(ns content.core-functions-in-depth.seq-function-examples.concat)

(concat [1 2 3] [4 5] [6 7])
;; => (1 2 3 4 5 6 7)

(def tarefas-pessoais
  ["Ler" "Treinar"])

(def tarefas-profissionais
  ["Estudar Clojure" "Revisar código"])

(defn unir-tarefas
  [tarefas adicionar-tarefas]
  (concat tarefas adicionar-tarefas))

(unir-tarefas tarefas-pessoais tarefas-profissionais)


;Diferentemente de funções como vec, o concat normalmente retorna uma sequência (seq), não um vetor.