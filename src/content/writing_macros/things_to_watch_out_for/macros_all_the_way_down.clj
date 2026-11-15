(ns content.writing-macros.things-to-watch-out-for.macros-all-the-way-down)

;1. Macros podem utilizar outras macros
;Imagine que temos uma macro:
(defmacro quando-positivo [valor & corpo]
  `(if (pos? ~valor)
     (do ~@corpo)))
;Podemos utilizá-la assim:
(quando-positivo 10
                 (println "Valor positivo!")
                 (println "Continuando..."))

;Mas perceba que nossa macro utiliza:
;- if, uma special form.
;- do, uma special form.
;- pos?, uma função.

;Podemos construir macros que utilizam outras macros também. Isso permite criar camadas de abstração.

;2. Um exemplo: when
;Você já utilizou bastante when durante nossos exercícios:
(when (> 10 5)
  (println "Maior")
  (println "Executando"))

;when é uma macro do próprio Clojure.
;Podemos inspecionar sua expansão:
(macroexpand-1
  '(when (> 10 5)
     (println "Maior")
     (println "Executando")))

;A expansão é equivalente a:
;(if (> 10 5)
;  (do
;    (println "Maior")
;    (println "Executando")))
;
;Observe que when oferece uma sintaxe mais conveniente para uma operação que poderia ser escrita com if e do.

;3. A linguagem construída sobre a própria linguagem
;Essa é a ideia central do tópico.
;Clojure oferece mecanismos fundamentais, como:
;- Funções.
;- Special forms.
;- Estruturas de dados.
;- Macros.
;Com esses mecanismos, a própria linguagem pode oferecer construções mais expressivas.

;Por exemplo:
;Construção	/ Tipo
;if	/ Special form
;let / Special form
;when / Macro
;cond / Macro
;->	/ Macro
;->> / Macro

;Você já utilizou praticamente todas essas construções durante nossos estudos.
;A grande vantagem é que não precisamos implementar cada abstração diretamente no núcleo da linguagem.
; Podemos construir novas formas de expressar operações utilizando os recursos existentes.

;4. Por que isso é importante?
;Imagine que você esteja desenvolvendo uma biblioteca financeira.

;Poderia criar uma macro para expressar regras condicionais:
;(quando-venda-valida venda
;                     (calcular-total venda)
;                     (registrar-venda! venda))

;Essa sintaxe poderia esconder uma estrutura mais complexa de validação e execução.
;A macro não cria uma nova capacidade fundamental para o computador. Ela cria uma maneira mais expressiva de representar uma operação.
;É isso que significa a ideia de Macros All the Way Down: as abstrações da linguagem podem ser construídas a partir de outras abstrações, inclusive macros.

;Fechamento da seção
;Com isso, terminamos os tópicos de Things to Watch Out For:
;1. Variable Capture: evitar conflitos entre variáveis introduzidas pela macro e variáveis do código consumidor.
;2. Double Evaluation: evitar executar expressões recebidas mais de uma vez.
;3. Macros All the Way Down: entender como macros podem construir outras abstrações da linguagem.