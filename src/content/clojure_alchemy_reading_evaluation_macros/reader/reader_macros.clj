(ns content.clojure-alchemy-reading-evaluation-macros.reader.reader-macros)

;O que são Reader Macros?
;Reader Macros são atalhos sintáticos reconhecidos pelo Reader durante a leitura do código.
;Eles permitem escrever determinadas estruturas de maneira mais curta, sem precisar escrever sua representação completa.

;Por exemplo:
;'(+ 10 20)
;É equivalente a:
;(quote (+ 10 20))

;O Reader reconhece o apóstrofo e constrói a estrutura correspondente.
;Importante: Reader Macros não são a mesma coisa que macros Clojure.
; As primeiras atuam durante a leitura do texto; as segundas atuam posteriormente, durante a avaliação.

;2. Principais Reader Macros
;Vamos estudar as mais importantes.
;A. Quote — ' Preserva uma expressão como dado.
;'(+ 10 20)
;Equivalente a: (quote (+ 10 20))

;B. Var quote — #' Obtém a própria Var, em vez de seu valor.
;(def taxa 0.10)
;#'taxa
;Equivalente a: (var taxa)

;C. Deref — @ Acessa o valor contido em uma referência, como um Atom.
;(def contador (atom 10))
;@contador
;;; 10
;Equivalente a:
;(deref contador)

;D. Anonymous function — #() Cria uma função anônima.
;#(* % 2)
;Equivalente a:
;(fn [numero]
;  (* numero 2))

;E. Regex — #""
;Cria uma expressão regular. #"^[0-9]+$"
;É uma expressão regular que identifica strings formadas somente por números.

;3. Visualizando o funcionamento
;Texto escrito
;#(* % 2)
;Reader reconhece a sintaxe abreviada
;Estrutura equivalente
;(fn [p1__123#]
;  (* p1__123# 2))

;Nome ilustrativo de um parâmetro gerado internamente.
;O Reader transforma a abreviação em uma representação de função anônima.
;O nome interno do parâmetro é gerado automaticamente; você não precisa escrevê-lo.

;4. Um detalhe importante: Reader Macro versus Macro
;Essa diferença será fundamental mais adiante.
;Reader Macro	/ Macro Clojure
;Atua durante a leitura	/ Atua durante a avaliação
;Transforma sintaxe textual	/ Transforma estruturas de código
;Exemplo: '	/ Exemplo: when
;Exemplo: #()	/ Exemplo: ->

;Não confunda as duas coisas: #() não é uma macro Clojure, é uma abreviação reconhecida pelo Reader.