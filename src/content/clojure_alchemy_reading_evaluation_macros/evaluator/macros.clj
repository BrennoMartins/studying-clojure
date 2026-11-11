(ns content.clojure-alchemy-reading-evaluation-macros.evaluator.macros)

;1. O que é uma macro?
;Uma macro é uma função especial que recebe código sem avaliá-lo, transforma esse código e devolve outra expressão para o Clojure avaliar.
;Essa é a grande diferença em relação a uma função comum.

;Vamos comparar.
;Função comum
(defn dobrar [valor]
  (* valor 2))

(dobrar (+ 10 5))

;O que acontece?
;1. O Clojure avalia (+ 10 5).
;2. Obtém 15.
;3. Passa 15 como argumento para dobrar.
;4. A função calcula (* 15 2).
;Resultado: 30.

;Macro
;Uma macro recebe a expressão original, não o resultado dela.
;Veja:
(defmacro minha-macro [expressao]
  (list 'quote expressao))

;Agora:
(minha-macro (+ 10 5))
;O resultado será:
;(+ 10 5)

;Por quê?
;1. O Reader transforma (+ 10 5) em uma lista.
;2. A macro recebe essa lista sem executá-la.
;3. A macro monta uma nova expressão usando quote.
;4. O Evaluator avalia a expressão produzida.
;5. Como ela contém quote, o código é retornado como dado, sem ser executado.
;Observe que a macro não retornou 15. Ela retornou a própria expressão.

;2. Macro não é simplesmente uma função
;Veja a diferença:
;Característica	/ Função	/ Macro
;Recebe	/ Argumentos avaliados	/ Formas de código
;Executa quando	/ É invocada	/ Durante a expansão de código
;Retorna	/ Um valor	/ Uma forma de código
;Permite controlar a avaliação dos argumentos?	/ Não diretamente	/ Sim

;É importante entender que a macro não é executada em um momento mágico completamente separado do restante do programa. Ela é expandida antes de o código produzido ser avaliado.

;3. Como visualizar a expansão de uma macro?
;Clojure possui uma função muito útil para isso:
;macroexpand
;Ela mostra a expansão de uma macro.

;Por exemplo:
(macroexpand '(when true
                (println "Olá")
                (println "Mundo")))

;O resultado será equivalente a:
;(if true
;  (do
;    (println "Olá")
;    (println "Mundo")))

;Isso revela algo interessante: when é uma macro que transforma uma construção mais conveniente em outra expressão que o Clojure já sabe avaliar.

;Você também pode usar macroexpand-1, que expande apenas uma camada da macro, em vez de continuar expandindo recursivamente.

;4. Por que macros são tão poderosas?
;Imagine que você queira criar uma construção chamada quando-positivo:
;(quando-positivo saldo
;                 (println "Saldo positivo!")
;                 (println saldo))
;
;A ideia é executar as expressões somente quando saldo for positivo.
;Com uma função comum, os argumentos seriam avaliados antes da chamada. Isso impediria que a função controlasse quando executar os println.
;
;Com uma macro, podemos transformar essa construção em um if:
;(defmacro quando-positivo [valor & corpo]
;  `(if (pos? ~valor)
;     (do ~@corpo)))
;
;Aqui aparecem três elementos novos:
;- Crase (`): cria uma estrutura de código usando syntax quote.
;          - Til (~): insere uma expressão dentro dessa estrutura.
;                  - Til arroba (~@): insere os elementos de uma sequência individualmente.
;                                 Vamos aprofundar esses três elementos na próxima parte, porque eles são fundamentais para escrever macros corretamente.
;
;                                 A ideia principal para guardar: funções trabalham com valores; macros trabalham com código. I
; Isso permite criar novas construções de linguagem, e não apenas novas operações.

;1. Syntax quote: a crase ( ` )
;Você já conhece o quote, que impede a avaliação de uma expressão:
'(+ 10 20)

;O resultado é uma lista contendo os símbolos +, 10 e 20, sem executar a soma.
;O syntax quote também cria código como dado, mas tem algumas vantagens:
;- Resolve símbolos de funções e special forms para seus namespaces.
;- Permite inserir expressões dinamicamente com ~.
;- Permite inserir sequências de expressões com ~@.
;Exemplo:
`(+ 10 20)

;O resultado é uma estrutura de código que representa a soma. O + será qualificado com seu namespace, normalmente clojure.core/+.
;A diferença é que o syntax quote prepara uma estrutura de código mais segura e previsível para a construção de macros.


;2. Unquote: o til ( ~ )
;Imagine que queremos criar uma expressão que multiplique um valor por 2.
(def valor 10)
`(* valor 2)

;Aqui, valor é tratado como símbolo. Não será substituído pelo conteúdo da Var.
;Agora veja:
`(* ~valor 2)

;Resultado conceitual:
;(* 10 2)

;O ~ indica que aquela parte deve ser avaliada durante a construção da estrutura e seu resultado inserido nela.
;Isso permite montar código dinamicamente.

;3. Unquote-splicing: o til arroba ( ~@ )
;Esse recurso é usado quando queremos inserir vários elementos de uma sequência dentro de outra estrutura.
;Considere:
(def expressoes
['(println "Olá")
'(println "Mundo")])

;Com unquote:
`(do ~expressoes)
;A sequência inteira é inserida como um único elemento.

;Com unquote-splicing:
`(do ~@expressoes)
;Os elementos são inseridos individualmente.
;O resultado conceitual é:
;(do
;(println "Olá")
;(println "Mundo"))
;É exatamente o que precisamos quando uma macro recebe uma quantidade variável de expressões.

;4. Aplicando os três conceitos na macro
;Vamos voltar à nossa macro:
(defmacro quando-positivo [valor & corpo]
`(if (pos? ~valor)
(do ~@corpo)))

;Vamos analisar:
;Trecho	Responsabilidade
;`	Constrói uma expressão de código
;~valor	Insere o valor recebido pela macro
;~@corpo	Insere todas as expressões recebidas individualmente
;
;Exemplo de utilização:
(def saldo 100)
;
(quando-positivo saldo
(println "Saldo positivo!")
(println saldo))

;A expansão será equivalente a:
;(if (pos? saldo)
;(do
;(println "Saldo positivo!")
;(println saldo)))

;O if só executará o bloco quando a condição for verdadeira.


;5. Um detalhe importante: gensym
;Existe um problema que pode acontecer ao criar macros: colisão de nomes.
;Imagine uma macro que cria uma variável local chamada resultado.
;Se o código que utiliza a macro também tiver uma variável com esse nome, podemos ter conflitos indesejados.

;Clojure oferece gensym para gerar símbolos únicos:
(gensym "resultado")
;Resultado possível:
;resultado1234
;O número é gerado automaticamente.

;Dentro de syntax quote, também podemos usar resultado# para gerar um símbolo único automaticamente:
;`(let [resultado# 10]
;(+ resultado# 5))

;Isso é muito útil para evitar colisões de nomes em macros.

;Consolidando
;Recurso	Significado
;'	Preserva uma expressão como dado
;`	Cria código com resolução de namespaces
;~	Insere uma expressão avaliada
;~@	Insere vários elementos individualmente
;gensym	Cria símbolos únicos

;Uma observação: macros são poderosas, mas não devem substituir funções comuns sem necessidade. Se uma função resolve o problema, geralmente ela é a escolha mais simples.
