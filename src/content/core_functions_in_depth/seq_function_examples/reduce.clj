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

















;reduce — Acumulando valores
;
;A função reduce percorre uma coleção, aplicando uma função a cada elemento e atualizando um acumulador até produzir um resultado final.
;
;1. Sintaxe
;(reduce função acumulador-inicial coleção)
;
;A função passada para reduce recebe dois argumentos, nesta ordem:
;
;Acumulador: resultado acumulado até o momento.
;
;Elemento atual: elemento da coleção que está sendo processado.
;
;2. Exemplo
;
;(defn somar
;  [acumulador numero]
;  (+ acumulador numero))
;
;(reduce somar 0 [10 20 30])
;;; Resultado: 60
;
;O processamento acontece assim:
;
;Etapa
;
;Acumulador
;
;Elemento atual
;
;Novo acumulador
;
;Inicial
;
;0
;
;—
;
;0
;
;1
;
;0
;
;10
;
;10
;
;2
;
;10
;
;20
;
;30
;
;3
;
;30
;
;30
;
;60
;
;3. Usando reduce com mapas
;
;O acumulador também pode ser um mapa, permitindo atualizar várias informações durante o processamento.
;
;(defn acumular-produtividade
;  [resultado atividade]
;  (update resultado
;          :total-minutos
;          +
;          (:minutos atividade)))
;
;(reduce acumular-produtividade
;        {:total-minutos 0}
;        [{:nome "Estudar Clojure" :minutos 120}
;         {:nome "Academia" :minutos 60}])
;
;;; Resultado:
;;; {:total-minutos 180}
;
;Nesse exemplo:
;
;resultado é o mapa acumulador.
;
;atividade é o elemento atual da coleção.
;
;(:minutos atividade) extrai os minutos da atividade.
;
;update soma esses minutos ao total acumulado.
;
;A função retorna o novo mapa, que será usado na próxima iteração.
;
;4. Atenção à ordem dos argumentos
;
;A função utilizada pelo reduce deve receber os argumentos na ordem esperada:
;
;(fn [acumulador elemento-atual]
;  ...)
;
;Os nomes dos parâmetros são livres, mas suas posições precisam corresponder aos valores fornecidos pelo reduce.
;
;Se inverter os parâmetros, poderá tentar acessar uma chave inexistente ou realizar operações com valores incorretos, causando erros como NullPointerException.
;
;Regra para memorizar
;
;reduce recebe uma função, um acumulador inicial e uma coleção. A cada iteração, a função recebe o acumulador primeiro e o elemento atual depois, devendo retornar o novo acumulador.
;
;
;
;




