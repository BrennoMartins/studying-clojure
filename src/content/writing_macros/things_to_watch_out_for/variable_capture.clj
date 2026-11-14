(ns content.writing-macros.things-to-watch-out-for.variable-capture)

;Imagine que criamos uma macro que recebe uma expressão e executa essa expressão dentro de um let.
;(defmacro minha-macro [expressao]
;  `(let [resultado 10]
;     ~expressao))

;Parece simples, certo?

;Vamos utilizar:
;(let [resultado 100]
;  (minha-macro resultado))

;Qual seria o resultado esperado?
;Talvez você espere 100, porque a variável externa possui esse valor.

;Mas o resultado será 10.
;Por que isso acontece?
;Vamos observar a expansão da macro:
;(let [resultado 100]
;  (let [resultado 10]
;    resultado))
;
;
;A variável resultado criada pela macro interfere na resolução do símbolo resultado da expressão recebida.
;
;Isso é chamado de Variable Capture.
;
;A macro introduziu um nome que acabou capturando uma referência que deveria apontar para outro binding.
;Como resolver?
;Usando gensym.
;(defmacro minha-macro [expressao]
;  (let [nome-unico (gensym "resultado")]
;    `(let [~nome-unico 10]
;       ~expressao)))
;
;
;Agora o código gerado utiliza um símbolo único, por exemplo:
;(let [resultado1234 10]
;  resultado)
;
;
;O símbolo resultado continua apontando para a variável externa.
;
;Resultado:
;100
;
;
;Uma alternativa mais simples: auto-gensym
;O syntax quote possui um recurso que gera símbolos únicos automaticamente:
;(defmacro minha-macro [expressao]
;  `(let [resultado# 10]
;     ~expressao))
;
;
;O # depois do nome faz o Clojure gerar um símbolo único.
;
;É uma solução bastante conveniente para evitar captura acidental de variáveis introduzidas pela macro.
;
;4.2 Double Evaluation — Avaliação duplicada
;Outro problema comum é avaliar uma expressão mais de uma vez.
;
;Imagine esta macro:
;(defmacro verificar-valor [expressao]
;  `(if (> ~expressao 0)
;     ~expressao
;     0))
;
;
;Utilização:
;(verificar-valor (do
;                   (println "Calculando...")
;                   10))
;
;
;O resultado é 10, mas quantas vezes a expressão foi executada?
;
;Vamos olhar a expansão:
;(if (> (do
;         (println "Calculando...")
;         10)
;       0)
;  (do
;    (println "Calculando...")
;    10)
;  0)
;
;
;Observe que a expressão aparece duas vezes.
;
;Consequentemente, "Calculando..." será impresso duas vezes.
;
;Isso pode ser um problema sério quando a expressão contém efeitos colaterais, como:
;- Registrar uma venda.
;- Atualizar estoque.
;- Fazer uma chamada HTTP.
;- Gravar informações no banco de dados.
;Como resolver?
;A solução é avaliar a expressão uma única vez e guardar o resultado em uma variável local.
;(defmacro verificar-valor [expressao]
;  `(let [valor# ~expressao]
;     (if (> valor# 0)
;       valor#
;       0)))
;
;
;Agora a expansão será equivalente a:
;(let [valor1234
;      (do
;        (println "Calculando...")
;        10)]
;  (if (> valor1234 0)
;    valor1234
;    0))
;
;
;A expressão é executada somente uma vez.
;
;4.3 Comparando os dois problemas
;Problema	O que acontece	Solução
;Variable Capture	Um símbolo introduzido pela macro interfere em outro binding	gensym ou nome#
;Double Evaluation	Uma expressão recebida é executada várias vezes	Avaliar uma vez e guardar em let
;
;4.4 Conexão com Java
;Esses problemas podem parecer específicos de Clojure, mas as ideias são familiares.
;- Variable Capture lembra problemas de escopo e sombreamento de variáveis.
;- Double Evaluation lembra executar repetidamente uma operação que deveria produzir um único resultado.
;A diferença é que macros trabalham diretamente com código, então precisamos tomar cuidado com os nomes e com quantas vezes cada expressão será executada.
;
;Um princípio importante para escrever macros:
;
;Sempre que criar uma macro, pergunte:
;1. Estou introduzindo algum nome que pode conflitar com o código de quem vai utilizá-la?
;2. Estou avaliando alguma expressão recebida mais de uma vez?
;Essas duas verificações evitam muitos bugs difíceis de identificar.
;
;Na próxima parte veremos Macros All the Way Down, para entender como macros podem ser compostas e como a própria linguagem utiliza macros para construir suas abstrações.