(ns content.writing-macros.using-syntax-quoting-in-macro)

;Vamos avançar para Using Syntax Quoting in a Macro. Agora vamos juntar o que aprendemos e construir macros de uma maneira mais idiomática.
;A ideia é substituir aquela construção manual com list e quote pelo syntax quote.

;1. Relembrando nossa macro
;No tópico anterior, criamos:
(def taxa 0.10)

(defmacro calcular-com-desconto [expressao]
  `(* ~expressao (- 1 ~taxa)))

;Utilização:
(calcular-com-desconto (+ 100 50))
;Resultado: 135.0.

;Vamos entender melhor por que essa implementação funciona.
;O que acontece durante a expansão?
;A macro recebe:
;(+ 100 50)
;Esse código fica disponível no parâmetro expressao.
;Depois, o syntax quote constrói:
;`(* ~expressao (- 1 ~taxa))

;Durante essa construção:
;- * é preservado como símbolo, com namespace qualificado.
;- ~expressao insere a expressão recebida pela macro.
;- ~taxa avalia a Var taxa e insere seu valor.
;A expansão fica conceitualmente assim:
;(clojure.core/*
;  (+ 100 50)
;  (clojure.core/- 1 0.10))
;Depois, o Evaluator executa essa expressão.

;2. Comparando com list e quote
;Podemos implementar a mesma macro sem syntax quote:
(defmacro calcular-com-desconto [expressao]
  (list '*
        expressao
        (list '- 1 taxa)))

;Também funciona.
;Mas perceba como precisamos construir manualmente cada lista e preservar cada símbolo com quote.

;Com syntax quote:
(defmacro calcular-com-desconto [expressao]
  `(* ~expressao (- 1 ~taxa)))
;O código fica muito mais próximo da expressão que queremos gerar.
;Essa é uma das grandes vantagens do syntax quote.

;3. Um detalhe importante: o momento da avaliação
;Considere:
(def taxa 0.10)

(defmacro calcular-com-desconto [expressao]
  `(* ~expressao (- 1 ~taxa)))

;Agora redefina:
(def taxa 0.20)

;E execute:
(calcular-com-desconto 100)
;O resultado será 80.0.

;Por quê?
;Porque a macro é expandida quando a expressão é compilada/avaliada, e o valor atual de taxa é inserido na expressão gerada.

;Isso é diferente de inserir o símbolo taxa sem unquote:
(defmacro calcular-com-desconto [expressao]
  `(* ~expressao (- 1 taxa)))

;Nesse caso, a expressão gerada mantém a referência a taxa, que será resolvida quando o código gerado for avaliado.
;Essa diferença é importante quando você quer decidir se a macro deve capturar um valor durante sua expansão ou deixar uma referência para avaliação posterior.

;4. Uma aplicação prática
;Imagine uma macro para calcular o preço com acréscimo:
(defmacro aplicar-acrescimo [valor percentual]
  `(* ~valor (+ 1 ~percentual)))

;Utilização:
(aplicar-acrescimo 100 0.15)
;Expansão conceitual:
(* 100 (+ 1 0.15))
;Resultado: 115.0.
;A macro recebe as duas expressões sem avaliá-las e as insere na estrutura gerada.

;O que consolidamos?
;- Syntax quote permite construir código de maneira mais legível.
;- Unquote (~) permite inserir expressões avaliadas durante a expansão.
;            - Podemos misturar código preservado com valores dinâmicos.
;            - O momento em que uma expressão é avaliada altera o comportamento da macro.
;            Próximo tópico: Refactoring a Macro and Unquote Splicing (~@).