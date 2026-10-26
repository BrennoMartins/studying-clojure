(ns content.core-functions-in-depth.seq-function-examples.sort-sort-by)


;A função sort ordena os elementos de uma coleção em ordem crescente.
(sort [30 10 20 5])
;; => (5 10 20 30)

;Por padrão, a ordenação é crescente. Para ordenar em ordem decrescente, podemos fornecer uma função de comparação:
(sort > [30 10 20 5])
;; => (30 20 10 5)
;
;E se você tiver uma coleção de mapas e quiser ordenar por uma propriedade específica?
;É aí que entra o sort-by.

(def tarefas
  [{:nome "Estudar Clojure" :prioridade 2}
   {:nome "Treinar" :prioridade 1}
   {:nome "Ler" :prioridade 3}
   {:nome "Estudar Datomic" :prioridade 1}])

(sort-by :prioridade tarefas)
(sort-by :nome tarefas)


;Considere:
(def tarefas
  [{:nome "Estudar Clojure" :prioridade 2 :minutos 60}
   {:nome "Treinar" :prioridade 1 :minutos 45}
   {:nome "Ler" :prioridade 3 :minutos 20}
   {:nome "Estudar Datomic" :prioridade 1 :minutos 90}])

;
;1. ordenar-por-prioridade

(defn ordenar-por-prioridade
  [tarefas]
  (sort-by :prioridade tarefas))
(ordenar-por-prioridade tarefas)

;2. ordenar-por-duracao
(defn ordenar-por-duracao
  [tarefas]
  (sort-by :minutos > tarefas))

(ordenar-por-duracao tarefas)

(defn ordenar
  ([tarefas campo]
   (sort-by campo tarefas))
  ([tarefas campo funcao]
   (sort-by campo funcao tarefas)))

(ordenar tarefas :minutos >)
(ordenar tarefas :prioridade)
