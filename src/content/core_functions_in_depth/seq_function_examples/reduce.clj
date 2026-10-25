(ns content.core-functions-in-depth.seq-function-examples.reduce)


(defn somar-aportes
  [valores]
  (reduce + valores))

(somar-aportes [100 200 300])


;Você recebe uma sequência de aportes, que pode conter valores positivos, negativos e zero:
(def aportes [1000 -200 500 0 300 -100])


;Implemente a função analisar-aportes, que recebe essa sequência e retorna um mapa com:
;- :saldo-final — a soma de todos os valores.
;- :quantidade-positivos — quantos valores são maiores que zero.
;- :total-positivos — a soma apenas dos valores maiores que zero.

(defn valores-positivos
  [aporte]
  (loop [contador 0
         coll-positivos []]
    (if (< contador (count aporte))
      (if (pos? (nth aporte contador))
        (recur (inc contador) (conj coll-positivos (nth aporte contador)))
        (recur (inc contador) coll-positivos))
      coll-positivos)))

(defn valores-positivos-filter
  [aportes]
  (filter pos? aportes))


(defn analisar-aportes
  [aportes]
  (let [mapa-valores-positivos (valores-positivos-filter aportes)]
  {:saldo-final (reduce + aportes)
   :quantidade-positivos (count mapa-valores-positivos)
   :total-positivos (reduce + mapa-valores-positivos)}))
;
(analisar-aportes aportes)
;;Resultado esperado:
;{:saldo-final 1500
; :quantidade-positivos 3
; :total-positivos 1800}

(analisar-aportes [])
;;; Resultado esperado:
;{:saldo-final 0
; :quantidade-positivos 0
; :total-positivos 0}

; Um Outro Jeito
(defn analisar-aportes [aportes]
  (reduce
    (fn [resultado aporte]
      (let [novo-resultado
            (update resultado :saldo-final + aporte)]
        (if (pos? aporte)
          (-> novo-resultado
              (update :quantidade-positivos inc)
              (update :total-positivos + aporte))
          novo-resultado)))
    {:saldo-final 0
     :quantidade-positivos 0
     :total-positivos 0}
    aportes))









(def inicio-valores
  {:saldo-final          0
   :quantidade-positivos 0
   :total-positivos      0})


(defn funcao-devolve-certinho
  [resultado valor]
  (let [novo-resultado (update resultado :saldo-final + valor)]
    (if (pos? valor)
      (-> novo-resultado
        (update :quantidade-positivos inc)
        (update :total-positivos + valor))
      novo-resultado)))

(defn resolver
  [aportes]
  (reduce funcao-devolve-certinho inicio-valores aportes))



(resolver aportes)


























