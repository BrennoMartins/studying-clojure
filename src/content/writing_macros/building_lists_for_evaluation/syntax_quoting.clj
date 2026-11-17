(ns content.writing-macros.building-lists-for-evaluation.syntax-quoting)

;Até aqui utilizamos o quote (') para preservar expressões como dados. O problema é que ele não facilita a combinação de código fixo com valores dinâmicos.

;O syntax quote resolve isso.
;1. O que é Syntax Quoting?
;É representado por uma crase (backtick):
`(+ 10 20)

;À primeira vista, parece igual ao quote:
'(+ 10 20)
;
;Ambos preservam a expressão sem executar a soma.
;Mas existe uma diferença importante: o syntax quote resolve automaticamente os namespaces dos símbolos.
;Por exemplo:
`(+ 10 20)
;Sua representação expandida será equivalente a:
;(clojure.core/+ 10 20)

;Isso evita ambiguidades quando construímos código dentro de macros.

;2. O problema que o Syntax Quote resolve
;Imagine que temos:
(def taxa 0.10)
(def valor 100)

;Queremos construir uma expressão equivalente a:
(* valor (- 1 taxa))

;Com quote simples:
'(* valor (- 1 taxa))

;Todos os elementos são preservados como código. Não conseguimos inserir diretamente o valor de uma variável nessa estrutura.
;Com syntax quote, temos o recurso chamado unquote.

;3. Unquote: o til (~)
;O ~ permite avaliar uma expressão e inserir seu resultado dentro de uma estrutura criada com syntax quote.
;Exemplo:
(def valor 100)
;
`(* ~valor 2)

;O resultado é uma estrutura equivalente a:
;(clojure.core/* 100 2)

;Observe o que aconteceu:
;- * continua sendo um símbolo que representa uma função.
;- ~valor foi avaliado e substituído por 100.
;- 2 continua sendo um valor numérico.
;Podemos executar:
;(eval `(* ~valor 2))
;;; 200

;4. Syntax quote também funciona com expressões
;O unquote não precisa inserir somente variáveis.
;Podemos inserir uma expressão inteira:
`(* ~(+ 10 5) 2)

;O resultado será uma estrutura equivalente a:
;(clojure.core/* 15 2)
;E, ao avaliar:
(eval `(* ~(+ 10 5) 2))
;;; 30

;Perceba a diferença:
;- A expressão dentro de ~ é avaliada durante a construção da estrutura.
;- O restante da expressão é preservado para avaliação posterior.


;5. Comparando os recursos
;Recurso	/ Comportamento
;'	/ Preserva a expressão como dado
;`	/ Preserva a expressão e resolve namespaces
;~	/ Avalia e insere uma expressão dentro do syntax quote
;~@	/ Avalia e insere vários elementos individualmente
;Ainda não vamos aprofundar o ~@, pois ele será trabalhado no próximo tópico.

;6. Aplicando em uma macro
;Vamos voltar ao exemplo de desconto:
(def taxa 0.10)

(defmacro calcular-com-desconto [expressao]
  `(* ~expressao (- 1 ~taxa)))

;Utilização:
(calcular-com-desconto (+ 100 50))

;A expansão será equivalente a:
;(clojure.core/*
;  (+ 100 50)
;  (- 1 0.10))

;Resultado:
;135.0

;Repare que temos dois momentos diferentes:
;1. taxa é avaliada durante a expansão da macro e seu valor é inserido no código.
;2. expressao é inserida como código e será avaliada posteriormente.
;Essa distinção é muito importante.

;Um detalhe para guardar
;O syntax quote não significa que tudo dentro dele será executado. Ele constrói uma estrutura de código.
;O ~ é que determina quais partes serão avaliadas durante essa construção.
;Próximo tópico: Using Syntax Quoting in a Macro. Vamos aprofundar essa combinação e entender como escrever macros mais limpas e previsíveis.