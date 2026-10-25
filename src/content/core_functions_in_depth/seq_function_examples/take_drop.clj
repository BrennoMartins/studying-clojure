(ns content.core-functions-in-depth.seq-function-examples.take-drop)

;Considere esta lista de tarefas do LifeOS:
(def tarefas
  ["Estudar Clojure"
   "Treinar"
   "Ler"
   "Revisar investimentos"
   "Descansar"])

;1. primeiras-tarefas: recebe uma coleção e uma quantidade n, retornando as primeiras n tarefas.
;2. tarefas-restantes: recebe uma coleção e uma quantidade n, retornando as tarefas que sobram depois de ignorar as primeiras n.
(defn primeiras-tarefas
  [tarefas quantidade-listar]
  (take quantidade-listar tarefas))

(defn tarefas-restantes
  [tarefas quantidade-remover]
  (drop quantidade-remover tarefas))

(primeiras-tarefas tarefas 2)
;; => ("Estudar Clojure" "Treinar")

(tarefas-restantes tarefas 2)
;; => ("Ler" "Revisar investimentos" "Descansar")


;take-while e drop-while
(def tempos [10 20 30 5 40 50])

(defn habitos-ate-limite
  [tempos]
  (take-while #(< % 35)  tempos))

(defn habitos-apos-limite
  [tempos]
  (drop-while #(< % 35)  tempos))

;1. habitos-ate-limite: recebe os tempos e retorna os valores enquanto forem menores que 35, parando no primeiro que não atender à condição.
;2. habitos-apos-limite: ignora os valores enquanto forem menores que 35 e retorna o restante a partir do primeiro que não atender à condição.
;Resultado esperado:
(habitos-ate-limite tempos)
;;; => (10 20 30 5)
;
(habitos-apos-limite tempos)
;;; => (40 50)

;filter -> percorre a coleção e seleciona todos os elementos que atendem à condição.
;take-while -> pega elementos do início até a primeira falha da condição.
;drop-while -> ignora elementos do início até a primeira falha e retorna o restante.