(ns content.data-structure.numbers
  (:import (java.math RoundingMode)))


(+ 10 5)
(- 10 5)
(* 10 5)
(/ 10 5)
(/ 10 3)
;;15;5;50;2;10/3
;; Para divisao que tem numeros sem decimal, ele devolve, porem para quem tem decimal devolve a divisao 10/3 para preservar o decimal (presicao)


;Long	Inteiro de 64 bits	Contadores, quantidades inteiras, IDs numéricos quando apropriado
;Double	Ponto flutuante binário de 64 bits	Cálculos científicos, simulações e aproximações numéricas
;BigDecimal	Decimal de precisão arbitrária	Valores monetários, taxas e cálculos que exigem precisão decimal controlada

(+ 2 (* 3 4))
(* (+ 2 3) 4)
(- 20 5 3)
(/ 100 2 5)
;; 14 20 12 10

(= 10 10)
(= 10 10.0)
(< 5 10)
(>= 10 10)
(not= 7 8)
;;true false true true true
;; not= é o != do java e podemos ter mais que um
(not= 1 1 2) ; => true
(not= 1 1 1) ; => false

(mod 10 3)
(mod 12 4)
(mod 7 2)




(type 10)
(type 10.0)
(type 10M)
;;Quando não tem decimal, devolve um Long , quando tem devolve um Double e qundo tem o M devolve um BigDecimal

(+ 0.1 0.2)
(+ 0.1M 0.2M)
; o primeiro somado ele coloca varias casas decimais, o segundo parece que arredonda

(/ 10 3)
(/ 10.0 3)
;(/ 10M 3M)
(with-precision 4 (/ 1000M 3M))
(.divide 10M 3M 4 RoundingMode/HALF_UP)
;;o primeiro da a divisao 10/3 o segundo ja da o resultado com os decimais 3.33... e o ultimo deu um ArithmeticException
;; O ArithmeticException acontece porque o resultado decimal de (10/3) tem infinitas casas decimais, e a divisão de BigDecimal do Clojure
;; exige um resultado decimal exato nesse caso.
;; Como não há um decimal finito que represente exatamente \(10/3\), a operação lança uma exceção em vez de arredondar silenciosamente.



;Calculadora de progresso financeiro
;Imagine que o LifeOS precisa calcular o progresso de uma meta de economia.
;Você recebe:
(def objetivo 10000)
(def saldo 3750) ;
(def dec-objetivo 10000.0)
(def dec-saldo 3750.0)
;Sem criar uma aplicação completa, escreva expressões no REPL para calcular:
;1. Quanto falta para atingir a meta.
(- objetivo saldo)
;2. Qual percentual da meta foi atingido.
(* 100.0 (/ (double saldo) objetivo))
;3. Se o saldo já atingiu ou ultrapassou a meta.
(>= saldo objetivo)
;4. Quanto falta se você acrescentar mais 1.500 ao saldo atual.
(- objetivo (+ saldo 1500))


(- dec-objetivo dec-saldo)
(* 100 (/ dec-saldo dec-objetivo))
(>= dec-saldo dec-objetivo)
(- dec-objetivo (+ dec-saldo 1500))
;Requisitos: use operações aritméticas e comparações. Para o percentual, pense cuidadosamente no tipo numérico usado na divisão.
;Bônus: experimente uma versão com números decimais e compare os resultados.

