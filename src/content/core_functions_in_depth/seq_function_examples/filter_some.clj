(ns content.core-functions-in-depth.seq-function-examples.filter-some)

(def tarefas
  [{:nome "Estudar Clojure" :prioridade 1}
   {:nome "Ler" :prioridade 3}
   {:nome "Treinar" :prioridade 1}
   {:nome "Organizar finanças" :prioridade 2}])

;Implemente duas funções:
;1. tarefas-prioritarias: retorna todas as tarefas cuja prioridade seja igual a 1.
(defn valida-prioridade-1
  [tarefa]
  (let [prioridade (:prioridade tarefa)]
    (if (= prioridade 1)
      true
      false)))

(defn valida-prioridade-1-melhor
  [tarefa]
  (= (:prioridade tarefa) 1))

(defn tarefas-prioritarias
  [tarefas]
  (filter valida-prioridade-1 tarefas))

(tarefas-prioritarias tarefas)

;;tenho que lembrar que o filter ele pega um registro

;2. existe-tarefa-prioritaria?: retorna true se existir pelo menos uma tarefa com prioridade 1, ou false caso contrário.
(defn valida-prioridade-1
  [tarefa]
  (let [prioridade (:prioridade tarefa)]
    (if (= prioridade 1)
      true
      false)))

(defn existe-tarefa-prioritaria?
  [tarefas]
  (boolean (some valida-prioridade-1 tarefas)))

(existe-tarefa-prioritaria? tarefas)

;Como o filter funciona?
;O filter recebe dois argumentos:
;1. Uma função que avalia cada elemento.
;2. Uma coleção com os elementos que serão avaliados.
;No seu caso, ele passa cada mapa para valida-prioridade-1.
;
;Por exemplo, na primeira iteração, a função recebe:
;{:nome "Estudar Clojure" :prioridade 1}
;
;A expressão (:prioridade tarefa) retorna 1, e a função retorna true. Como o resultado é verdadeiro, a tarefa permanece na coleção.
;
;Quando recebe:
;{:nome "Ler" :prioridade 3}
;
;A função retorna false, e essa tarefa não aparece no resultado.
;
;Portanto, o filter não recebe apenas um registro: ele percorre a coleção e passa cada registro individualmente para a função de validação.


(existe-tarefa-prioritaria?
      [{:nome "Ler" :prioridade 3}
       {:nome "Treinar" :prioridade 2}])


(some :prioridade
      [{:nome "Ler" :prioridade 3}
       {:nome "Treinar" :prioridade 1}])
;; => 3

;Isso mostra que some não significa exatamente "verificar se algum elemento atende à condição". Ele procura o primeiro resultado verdadeiro produzido pela função.




;Exercício 2 — some
;Considere este vetor de investimentos:
(def investimentos
  [{:ativo "Tesouro Direto" :rentabilidade 0.10}
   {:ativo "CDB" :rentabilidade 0.12}
   {:ativo "LCI" :rentabilidade 0.09}
   {:ativo "Ações" :rentabilidade 0.15}])

;Enunciado: utilizando apenas some, verifique se existe algum investimento com rentabilidade superior a 0.13.
(filter (fn[valor] (= (:prioridade valor) 1)) tarefas)
(boolean (some (fn[valor] (> (:rentabilidade valor) 0.13))  investimentos))
;Quero que você observe o resultado retornado pelo some, sem usar boolean ou if.
;
;Faça os dois e me envie as expressões e os resultados que apareceram no REPL. Depois vamos analisar o que cada função retornou e por quê.