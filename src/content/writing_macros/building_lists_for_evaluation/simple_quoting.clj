(ns content.writing-macros.building-lists-for-evaluation.simple-quoting)

;1. Relembrando o quote
;Quando escrevemos:
(+ 10 20)

;O Clojure avalia a expressão e retorna 30.
;Mas, quando usamos:
;'(+ 10 20)
;O Clojure preserva a lista como dado:
;(+ 10 20)
;Ou seja, não executa a soma.

;Podemos confirmar:
(type '(+ 10 20))
;;; clojure.lang.PersistentList

;2. Construindo código com list e quote
;Imagine que queremos construir dinamicamente esta expressão:
(+ 10 20)

;Podemos fazer:
(list '+ 10 20)
;Resultado:
;(+ 10 20)

;Agora temos uma estrutura de dados que representa código.
;Podemos armazená-la:
(def expressao (list '+ 10 20))

;E executá-la posteriormente:
(eval expressao)
;;; 30

;Observe a separação:
;- list constrói a estrutura.
;- quote preserva o símbolo +.
;- eval executa a estrutura.

;3. Um exemplo mais interessante
;Imagine que queremos construir uma expressão de multiplicação usando valores dinâmicos:
(def operador '*)
(def valor-a 10)
(def valor-b 5)

;Se fizermos:
(list operador valor-a valor-b)

;O resultado será uma lista contendo:
;- A função de multiplicação, porque operador foi avaliado.
;- O número 10.
;- O número 5.

;Isso não é o mesmo que uma lista contendo o símbolo *.
;Para construir uma expressão que tenha o símbolo como primeiro elemento, precisamos preservá-lo:
(list '* valor-a valor-b)

;Resultado conceitual:
;(* 10 5)

;E:
(eval (list '* valor-a valor-b))
;;; 50
;Atenção: operador e '* são coisas diferentes. O primeiro é uma Var cujo valor é a função de multiplicação. O segundo é um símbolo que representa o nome da função.

;4. Uma limitação do quote simples
;Considere:
(def nome "Arroz")
'(+ nome 10)
;O resultado será a lista:
;(+ nome 10)

;O símbolo nome não é substituído pelo valor "Arroz". ;
;O quote preserva a expressão inteira como dado. Ele não oferece uma maneira direta de inserir partes avaliadas dentro dela.
;É justamente essa limitação que nos levará ao próximo conceito: Syntax Quoting.
;Com ele, poderemos construir expressões preservando o código, mas também inserindo valores dinamicamente.

;Para consolidar
;Antes de avançar, guarde esta diferença:
;Recurso	/ O que faz
;list	/ Constrói uma lista avaliando seus argumentos
;'	/ Preserva uma expressão sem avaliá-la
;eval / 	Avalia uma estrutura de dados como código
