(ns content.functions.defining
  (:require [clojure.repl :refer [doc]]))

;1 -(defn calcular-saldo-restante
;  [objetivo saldo-atual]
;  (- objetivo saldo-atual))
;2 -(defn calcular-percentual-meta
;  "Receber o objetivo e o saldo atual e retornar o percentual alcançado."
;  [objetivo saldo-atual]
;  (* 100.0 (/ saldo-atual objetivo )))
;Consulte a documentação da função no REPL usando doc. -> Como faz isso?
;3 - apenas a primeira, erro de arity, arity eh a quantidade de parametros que a funcao espera receber, qualquer coisa diferente que for passado teremos erro de arity
;4 -(defn calcular-valor-posicao
;  [quantidade preco-medio]
;  (* quantidade preco-medio))
;5 - Colocaria o proposito da funcao e colocaria valores de parametros semanticos
;6 -(defn resumir-meta
;  [meta-viagem-resumo]
;  (let [msg-meta "Meta: "
;        nome-meta (:nome meta-viagem-resumo)
;        msg-pipe " | "
;        msg-falta "Falta: "
;        valor-falta (calcular-saldo-restante (:objetivo meta-viagem-resumo) (:saldo-atual meta-viagem-resumo))]
;    (str msg-meta nome-meta msg-pipe msg-falta valor-falta)))
;7 - (calcular-total-aportes 1000 500 300); tres parametros; erro de arity;  aqui temos 3 parametros e na outra 2


;Exercício 1 — Função de cálculo financeiro
;Crie uma função chamada calcular-saldo-restante que receba dois parâmetros:
;- objetivo: valor total da meta.
;- saldo-atual: valor que já foi acumulado.
;A função deve retornar quanto falta para alcançar a meta.
(defn calcular-saldo-restante
  [objetivo saldo-atual]
  (- objetivo saldo-atual))
;Exemplo de uso esperado:
(calcular-saldo-restante 10000 3500)

;Exercício 2 — Função com docstring
;Crie uma função chamada calcular-percentual-meta.
;Ela deve receber o objetivo e o saldo atual e retornar o percentual alcançado.

(defn calcular-percentual-meta
  "Receber o objetivo e o saldo atual e retornar o percentual alcançado."
  [objetivo saldo-atual]
  (* 100.0 (/ saldo-atual objetivo )))

(doc calcular-percentual-meta)

(calcular-percentual-meta 10000 4000)

;Exercício 4 — Função para investimento
;Crie uma função chamada calcular-valor-posicao que receba:
;- quantidade
;- preco-medio
;Ela deve retornar o valor total da posição.
(defn calcular-valor-posicao
  [quantidade preco-medio]
  (* quantidade preco-medio))

(calcular-valor-posicao 10 250)

;Exercício 6 — Resumo de meta
(def meta-viagem
  {:nome "Viagem de férias"
   :objetivo 12000
   :saldo-atual 4500})

;Crie uma função chamada resumir-meta que receba o mapa da meta como argumento e retorne uma string neste formato:
;Meta: Viagem de férias | Falta: 7500

(defn resumir-meta
  [meta-viagem-resumo]
  (let [msg-meta "Meta: "
        nome-meta (:nome meta-viagem-resumo)
        msg-pipe " | "
        msg-falta "Falta: "
        valor-falta (calcular-saldo-restante (:objetivo meta-viagem-resumo) (:saldo-atual meta-viagem-resumo))]
    (str msg-meta nome-meta msg-pipe msg-falta valor-falta)))

(resumir-meta meta-viagem)

;Exercício 7 — Função com diferentes quantidades de argumentos
;Crie uma função chamada calcular-total-aportes que receba três parâmetros: aporte-1, aporte-2 e aporte-3.
;Ela deve retornar a soma dos três valores.
(defn calcular-total-aportes
  [aporte-1 aporte-2 aporte-3]
  (+ aporte-1 aporte-2 aporte-3))

(calcular-total-aportes 1000 500 300)


;Exercício 8 — Total de aportes variáveis
;Crie uma função chamada calcular-total-aportes que aceite um ou mais valores de aporte, usando &.
;Ela deverá produzir os seguintes resultados:
(defn calcular-total-aportes
  [primento-valor & outros-valores]
  (+ primento-valor (apply + outros-valores)))

(calcular-total-aportes 1000)
(calcular-total-aportes 1000 500)
(calcular-total-aportes 1000 500 300 200)


;Exercício 9 — Função de cálculo financeiro com overload
;Crie uma função chamada calcular-valor-posicao com duas implementações:
;- Dois argumentos: quantidade e preço unitário; retorna o valor total.
;- Três argumentos: quantidade, preço unitário e taxa; retorna o valor total acrescido da taxa.
;Resultados esperados:

(defn calcular-valor-posicao
  ([quantidade preco-unitario]
  (* quantidade preco-unitario))
  ([quantidade preco-unitario taxa]
   (let [valor-posicao (calcular-valor-posicao quantidade preco-unitario)]
     (+ (* valor-posicao taxa) valor-posicao))))

(calcular-valor-posicao 10 250)
;; => 2500

(calcular-valor-posicao 10 250 0.1)
;; => 2750.0


