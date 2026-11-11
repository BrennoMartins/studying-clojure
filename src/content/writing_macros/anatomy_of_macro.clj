(ns content.writing-macros.anatomy-of-macro)

;Uma macro possui uma estrutura parecida com uma função:
;(defmacro nome-da-macro [argumentos]
;  ...)

;Mas existe uma diferença fundamental: os argumentos recebidos são formas de código, não valores já avaliados.
;Vamos construir um exemplo simples.

;2.1 Criando nossa primeira macro
;Imagine que queremos criar uma macro chamada dobrar-expressao.
(defmacro dobrar-expressao [expressao]
  (list '* expressao 2))

;Utilização:
(dobrar-expressao (+ 10 5))

;Resultado:
;30

;Vamos entender o que aconteceu.
;Passo 1 — O Reader
;O Reader transforma a chamada em uma estrutura de dados:
;(dobrar-expressao (+ 10 5))

;O argumento (+ 10 5) é uma lista. Ele ainda não foi executado.

;Passo 2 — Expansão da macro
;A macro recebe essa lista como argumento:
;expressao = (+ 10 5)
;E executa:
;(list '* expressao 2)
;O resultado da expansão é:
;(* (+ 10 5) 2)

;Passo 3 — Avaliação
;Agora o Evaluator recebe a expressão produzida pela macro e a executa:
;(* (+ 10 5) 2)
;Resultado: 30.


;2.2 Inspecionando a expansão
;Podemos utilizar macroexpand-1 para enxergar o código gerado:
(macroexpand-1
  '(dobrar-expressao (+ 10 5)))
;Resultado:
;(* (+ 10 5) 2)

;Isso é muito útil durante o desenvolvimento de macros, pois permite verificar se estamos gerando a estrutura de código esperada.

;2.3 Macro recebe código, mas também pode receber valores
;Observe esta macro:
(defmacro mostrar-expressao [expressao]
  (println expressao)
  expressao)

;Ao executar:
(mostrar-expressao (+ 10 5))

;A macro recebe a lista (+ 10 5), imprime essa estrutura e devolve a mesma expressão.
;O Evaluator então executa a expressão e retorna 15.

;Repare na diferença:
;- O println dentro da macro acontece durante a expansão.
;- O + acontece durante a avaliação do código produzido.
;Essa separação é muito importante.

;2.4 O ciclo completo
;Código original  -> (dobrar-expressao (+ 10 5))
;Reader Estrutura de dados -> Lista representando a chamada
;Expansão da macro ->Código gerado ->(* (+ 10 5) 2)
;Evaluator -> Resultado 30

;2.5 Uma observação importante
;Uma macro não precisa necessariamente retornar uma lista.
;Ela precisa retornar uma forma de código válida para o contexto em que será avaliada.
;Por exemplo, pode retornar um símbolo, um número ou uma lista, dependendo do que queremos produzir.
;Isso ficará ainda mais claro quando estudarmos a construção de listas para avaliação.
;O que quero que você leve desta parte: uma macro recebe formas de código, executa sua própria lógica durante a expansão e devolve uma nova forma que será avaliada pelo Clojure.