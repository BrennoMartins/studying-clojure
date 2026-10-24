(ns content.functions.anonymous-functions
  (:require [clojure.string :as str]))

(defn dobrar [n]
  (* n 2))
(dobrar 5)

((fn [n]
  (* n 2)) 5)

(#(* % 2) 5)

(#(+ % 10) 5)

;Na forma abreviada, % representa o primeiro argumento e %2 representa o segundo.
(#(- % %2) 20 5)

((fn [a b]
   (- a b))
 20 5)

;Um detalhe importante sobre #()
;A forma abreviada é prática para funções pequenas, especialmente quando você passa uma operação simples para outra função.
;Por exemplo:
;(map #(* % 2) [1 2 3])
;;; => (2 4 6)
;
;Aqui, map aplica a função anônima a cada elemento da coleção.
;Uma observação: na forma #(), você não declara os parâmetros explicitamente.
;Eles são representados por %, %2 e assim por diante. Para funções mais complexas, fn costuma ser mais legível.

;Exercício F-12 — Quando usar cada forma?
;Imagine que você queira criar uma função anônima que recebe um projeto e retorna o nome dele em maiúsculas:
;{:nome "LifeOS"}
;
;Você poderia usar fn para deixar o parâmetro explícito e acessar o mapa.
;Sua missão: escreva essa função usando fn, recebendo o mapa como argumento e retornando o valor de :nome convertido para maiúsculas com clojure.string/upper-case.
;Teste com o mapa acima e verifique se retorna "LIFEOS".

((fn [mapa]
  (str/upper-case (:nome mapa)))
 {:nome "LifeOS"})