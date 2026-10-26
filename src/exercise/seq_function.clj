(ns exercise.seq-function)

;Exercício 1 — Análise de hábitos (Médio)
;Ela deverá retornar uma coleção contendo somente os hábitos que atingiram suas metas, ordenados pelo maior tempo dedicado.
;Cada elemento do resultado deve conter apenas:
;- :nome
;- :minutos
;- :meta
;- :excedente — quantos minutos ultrapassaram a meta.
;Exemplo da estrutura esperada:
;({:nome "Clojure"
;  :minutos 45
;  :meta 30
;  :excedente 15}
; ...)
;A ordem e a quantidade de elementos devem corresponder aos dados de entrada.

(def habitos
  [{:nome "Leitura" :minutos 30 :meta 20}
   {:nome "Academia" :minutos 50 :meta 60}
   {:nome "Clojure" :minutos 45 :meta 30}
   {:nome "Meditação" :minutos 10 :meta 10}
   {:nome "Caminhada" :minutos 25 :meta 30}
   {:nome "Inglês" :minutos 40 :meta 20}])


(defn habito-bateu-meta?
  [habito]
  (>= (:minutos habito) (:meta habito)))

(defn adicionar-excedente
  [habito]
  (assoc habito :excedente (- (:minutos habito) (:meta habito))))

(defn resultado
  [habitos-default]
  {:nome (:nome habitos-default)
   :minutos (:minutos habitos-default)
   :meta (:meta habitos-default)
   :excedente (:excedente habitos-default)})

(defn analisar-habitos
  [habitos]
  (->> habitos
       (filter habito-bateu-meta?)
       (sort-by :minutos >)
       (map adicionar-excedente)
       (map resultado)))

;Maneiras de melhorar o map depois de outro map
;(map (fn [habito]
;       (resultado (adicionar-excedente habito))))

;(map #(resultado (adicionar-excedente %)))
(analisar-habitos habitos)
;



;Imagine que você está desenvolvendo seu sistema de controle financeiro e precisa analisar uma coleção de aportes.
(def aportes [1500 -200 800 2500 -100 1200 400 3000])

(def resultado  {:saldo-final 0
                 :quantidade-positivos 0
                 :total-positivos 0
                 :maior-aporte 0
                 :aportes-acima-de-1000 []})


(defn verificar-maior-aporte
  [aportes]
  (loop [contador 0
         ultimo-valor-maior 0]
    (if (< contador (count aportes))
      (if (< ultimo-valor-maior (nth aportes contador))
             (recur (inc contador) (nth aportes contador))
             (recur (inc contador) ultimo-valor-maior))
      ultimo-valor-maior)))

(defn analisar-aportes
  [aportes]
  (let [mapa-resultado resultado]
    (-> mapa-resultado
        (assoc :saldo-final (reduce + aportes))
        (assoc :quantidade-positivos (count (filter pos? aportes)))
        (assoc :total-positivos (reduce + (filter pos? aportes)))
        (assoc :maior-aporte (verificar-maior-aporte aportes))
        (assoc :aportes-acima-de-1000 (filter #(< 1000 %) aportes))
        )))

(analisar-aportes aportes)

;;;;;Solucao mais idiomatica
(defn acumular-aporte
  [resultado aporte]
  (let [resultado-com-saldo
        (update resultado :saldo-final + aporte)

        resultado-com-positivos
        (if (pos? aporte)
          (-> resultado-com-saldo
              (update :quantidade-positivos inc)
              (update :total-positivos + aporte))
          resultado-com-saldo)

        maior-atual (:maior-aporte resultado-com-positivos)]
    (if (or (nil? maior-atual)
            (> aporte maior-atual))
      (assoc resultado-com-positivos :maior-aporte aporte)
      resultado-com-positivos)))

(defn analisar-aportes
  [aportes]
  (let [valores-iniciais
        {:saldo-final 0
         :quantidade-positivos 0
         :total-positivos 0
         :maior-aporte nil}

        resultado
        (reduce acumular-aporte valores-iniciais aportes)]
    (assoc resultado
      :aportes-acima-de-1000
      (filter #(< 1000 %) aportes))))

(def aportes [1500 -200 800 2500 -100 1200 400 3000])

(analisar-aportes aportes)




;🥋 Exercício 3 — Priorização inteligente de tarefas
;Dificuldade: Média/Difícil
;
;Considere a seguinte coleção:
;(def tarefas
;  [{:nome "Estudar Clojure" :prioridade 1 :minutos 60}
;   {:nome "Responder e-mails" :prioridade 3 :minutos 20}
;   {:nome "Treinar" :prioridade 2 :minutos 50}
;   {:nome "Revisar código" :prioridade 1 :minutos 40}
;   {:nome "Ler documentação" :prioridade 2 :minutos 30}
;   {:nome "Organizar mesa" :prioridade 3 :minutos 15}])
;
;🎯 Objetivo
;Implemente a função:
;(selecionar-tarefas tarefas 45)
;
;O segundo argumento representa o tempo máximo disponível em minutos.
;
;A função deve retornar somente as tarefas que atendam simultaneamente aos seguintes critérios:
;1. A prioridade deve ser 1 ou 2.
;2. A duração deve ser menor ou igual ao tempo disponível.
;3. As tarefas devem ser ordenadas pela prioridade, da mais alta para a mais baixa — neste caso, prioridade 1 vem antes da 2.
;4. Em caso de empate na prioridade, as tarefas mais curtas devem aparecer primeiro.
;Resultado esperado
;({:nome "Ler documentação" :prioridade 2 :minutos 30}
; {:nome "Revisar código" :prioridade 1 :minutos 40})
;
;Correção: esse exemplo de resultado não está ordenado corretamente de acordo com os critérios! O resultado correto é:
;({:nome "Revisar código" :prioridade 1 :minutos 40}
; {:nome "Ler documentação" :prioridade 2 :minutos 30})
;
;A tarefa "Estudar Clojure" fica de fora porque exige 60 minutos, acima do limite de 45. "Treinar" também fica de fora porque exige 50 minutos. As tarefas de prioridade 3 são excluídas.
;📋 Requisitos
;- Use filter para selecionar as tarefas.
;- Use sort-by para ordenar o resultado.
;- Combine condições com and e or.
;- Você pode usar fn ou #() para criar funções anônimas.
;- Não use loop, recur ou funções que ainda não estudamos.
;Desafio extra
;Crie uma segunda função que receba o resultado de selecionar-tarefas e retorne somente os nomes das tarefas, preservando a ordem.
;
;Por exemplo:
;("Revisar código" "Ler documentação")