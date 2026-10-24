(ns content.functions.calling)

;Em uma expressão aninhada, a chamada interna precisa produzir seu resultado para que a chamada externa possa utilizá-lo.
;Conceito-chave: pense nas chamadas aninhadas como uma árvore de expressões.
; Primeiro resolvemos as chamadas internas necessárias; depois, usamos os resultados na chamada externa.
(+ 10 20 30)
(+ 10 (* 2 5))
(- 100 (/ 40 2))
(* (+ 2 3) (- 10 4))
(- 20 5)
(- 5 20)
(/ 20 4)
(/ 4 20)

(def meta
  {:objetivo 15000
   :saldo 6000})

(- (:objetivo meta) (:saldo meta))
(* 100.0 (/ (:saldo meta) (:objetivo meta)))
(+ (:saldo meta) 2500)

(def investimento
  {:quantidade 10
   :preco-medio 250})

;1. Calcular o valor total da posição
(* (:quantidade investimento) (:preco-medio investimento))
;2. Calcular o valor da posição após adicionar mais 5 unidades, mantendo o preço médio.
(* (:quantidade (assoc investimento :quantidade 15)) (:preco-medio investimento))
;3. Calcular quanto a posição vale após um aumento de R$ 20 no preço por unidade.
(* (:quantidade (assoc investimento :preco-medio 270)) (:preco-medio investimento))

(* (:quantidade investimento)
   (:preco-medio
     (assoc investimento :preco-medio 270)))

(def meta
  {:objetivo 20000
   :saldo 7500})

(* 100.0
   (/ (+ (:saldo meta) 2500)
      (:objetivo meta)))


;Responda:
;1. Qual chamada é avaliada primeiro?

;2. Qual é o valor passado como numerador para /?
;3. Qual é o resultado final?
;4. O que esse resultado representa no contexto da meta?