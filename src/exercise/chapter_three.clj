(ns exercise.chapter-three)

;Exercício 1 — Resumo financeiro
(def meta-financeira
  {:nome "Reserva de emergência"
   :objetivo 20000
   :saldo-atual 12500})

;Implemente resumir-meta para retornar:
;Reserva de emergência: 62.5% concluída

;Regras:
;- Calcule o percentual concluído.
;- Se o saldo for maior ou igual ao objetivo, retorne "Meta concluída!".
;- Use let para guardar os valores calculados.
;- Para formatar o percentual, você pode usar str; não precisa arredondar ainda.

(defn resumir-meta
  [meta-financeira]
  (let [porcentagem (* 100.0 (/ (:saldo-atual meta-financeira) (:objetivo meta-financeira)))
        porcentagem-ideal-reserva 62.5]
    (if (>= porcentagem porcentagem-ideal-reserva)
      (str (:nome meta-financeira) ": " porcentagem "% concluída")
      (str porcentagem "%"))))

(resumir-meta (assoc meta-financeira :objetivo 200000))
(resumir-meta meta-financeira)

;Exercício 2 — Validação de hábitos
;Fácil–intermediário
;Criar uma função que interprete o registro de um hábito.
;Dados:
{:nome "Leitura"
 :minutos 30
 :meta-diaria 20}

;Implemente avaliar-habito para retornar "Meta atingida" quando os minutos forem suficientes e "Meta pendente" caso contrário.
(defn avaliar-habito
  [{:keys [minutos meta-diaria]}]
  (if-let [minutos-registados (and minutos meta-diaria)]
    (if (and (>= minutos meta-diaria) (pos? minutos))
      "Meta atingida"
      "Meta pendente")
    "Registro incompleto"))

(defn avaliar-habito
  [{:keys [minutos meta-diaria]}]
  (if-some [minutos-registados minutos]
    (if (some? meta-diaria)
      (if (and (>= minutos-registados meta-diaria)
               (pos? minutos-registados))
        "Meta atingida"
        "Meta pendente")
      "Registro incompleto")
    "Registro incompleto"))


(avaliar-habito {:nome "Leitura"
                 :minutos 0
                 :meta-diaria 0})

(avaliar-habito {:nome "Leitura"
                 :minutos 10
                 :meta-diaria 0})

(avaliar-habito {:nome "Leitura"
                 :meta-diaria 0})

(avaliar-habito {:nome "Leitura"
                 :minutos 0})

;Exercício 3 — Seleção de tarefas prioritárias
(def tarefas
  [{:nome "Estudar Clojure" :prioridade 1}
   {:nome "Revisar orçamento" :prioridade 1}
   {:nome "Planejar férias" :prioridade 2}
   {:nome "Revisar investimentos" :prioridade 1}])

;Requisitos:
;- Retorne um vetor contendo somente os mapas cuja :prioridade seja igual a 1.
;- Preserve os mapas completos e a ordem original.
(defn add-tarefa-prioritaria
  [tarefas tarefa-adicionar]
  (if (=(:prioridade tarefa-adicionar) 1)
    (conj tarefas tarefa-adicionar)
    tarefas))

(defn prioridade-alta
  [tarefas]
   (loop [contador 0
          novo-vetor []]
     (if (< contador (count tarefas))
       (recur (inc contador) (add-tarefa-prioritaria novo-vetor (nth tarefas contador)))
       novo-vetor)))

(prioridade-alta tarefas)



;Exercício 4 — Análise de aportes
(def aportes [500 1200 -100 800 1500 0])


(defn contabilizar-positivos
  [valor-checar resultado]
  (if (pos? valor-checar)
    {:total-positivos      (+ (:total-positivos resultado) valor-checar)
     :quantidade-positivos (inc (:quantidade-positivos resultado))}
    resultado))

(defn analisar-aportes
  [aportes]
  (loop [contador 0
         resultado {:total-positivos 0
                    :quantidade-positivos 0}]
    (if (< contador (count aportes))
      (recur (inc contador) (contabilizar-positivos (nth aportes contador) resultado))
      resultado)))

(defn analisar-aportes-outra-versao
  [aportes]
  (loop [contador 0
         resultado {:total-positivos 0
                    :quantidade-positivos 0}]
    (if (< contador (count aportes))
      (let [aporte (nth aportes contador)]
        (if (pos? aporte)
          (recur
            (inc contador)
            (assoc resultado
              :total-positivos
              (+ (:total-positivos resultado) aporte)
              :quantidade-positivos
              (inc (:quantidade-positivos resultado))))
          (recur (inc contador) resultado)))
      resultado)))

(analisar-aportes aportes)
