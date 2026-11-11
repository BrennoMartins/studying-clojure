(ns content.clojure-alchemy-reading-evaluation-macros.overview-clojure-evaluation-model)

;1. A ideia central: código também é dado
;Existe uma característica muito importante em Clojure:
;O código Clojure é representado por estruturas de dados.

;Isso significa que uma expressão como:
;(+ 10 20)

;pode ser entendida como uma lista contendo três elementos:
;+
;10
;20

;O primeiro elemento representa a operação, e os demais são seus argumentos.
;Essa característica é uma das bases do poder das macros, que estudaremos mais adiante.

;2. O caminho entre escrever e executar código
;Quando você escreve uma expressão no Clojure, existem duas etapas fundamentais:
;Código-fonte
;(+ 10 20)
;
;Reader
;Lê e transforma o texto em estruturas de dados
;Estrutura de dados
;Lista: +, 10, 20

;Evaluator
;Avalia a expressão e determina o que executar
;Resultado
;30

;Essas duas etapas são fundamentais:
;Reader: transforma o texto escrito em estruturas de dados Clojure.
;Evaluator: avalia essas estruturas e produz resultados.

;Uma observação: esse é um modelo conceitual simplificado. O ambiente de execução possui outras etapas internas, mas essa divisão é suficiente para começarmos.

;3. Vamos comparar com Java
;Considere:
;int resultado = 10 + 20;
;O compilador Java analisa o código, constrói uma representação interna e gera bytecode.

;No Clojure, o processo começa com o Reader, que transforma o texto em estruturas de dados, e depois o avaliador trabalha com essas estruturas.
;A grande diferença que queremos explorar é que, em Clojure, podemos manipular representações do próprio código como dados.
;Isso abre possibilidades que vamos explorar com macros.


;4. Primeiro experimento no REPL
;Quero que você execute estas expressões no seu REPL:

;Experimento A
(+ 10 20)

;Experimento B
'( + 10 20)

;Experimento C
(list + 10 20)

;Observe que os três exemplos parecem semelhantes, mas têm comportamentos diferentes.
;Uma pequena dica: o apóstrofo (') tem um papel muito importante no Clojure. Ele impede que uma expressão seja avaliada normalmente.
;Podemos comparar:
;Código	Comportamento	Resultado
;(+ 10 20)	Avalia a expressão	30
;'(+ 10 20)	Preserva a expressão como dado	Lista (+ 10 20)



;Um detalhe importante: eval
;O eval recebe uma estrutura de dados que representa código e solicita ao Clojure que a avalie.
;Isso significa que podemos construir código dinamicamente e depois avaliá-lo.
;É uma ideia fundamental para entender metaprogramação e macros.
(eval '(+ 10 20))

(def expressao '(+ 10 20))


(eval expressao)
;
;Conceitos que estudamos:
;Conceito	Aprendizado
;Evaluation	Avaliação de expressões
;Reader	Transforma texto em estruturas de dados
;Evaluator	Avalia as estruturas
;Quote (')	Preserva expressões como dados
;list	Cria listas avaliando seus argumentos
;eval	Avalia uma estrutura que representa código