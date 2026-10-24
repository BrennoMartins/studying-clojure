(ns content.data-structure.keywords)

(type :status)
(type "status")

(= :status :status)
(= :status :nome)
(= :status "status")

(def tarefa
  {:nome "Estudar Clojure"
   :status :em-andamento
   :prioridade :alta})

(:nome tarefa)
(:status tarefa)
(:prioridade tarefa)
(:inexistente tarefa)

(def tarefa-2
  {:nome "Estudar Clojure"
   :status "em andamento"
   :prioridade "alta"})

(:status tarefa)
(:status tarefa-2)


;Existem algumas vantagens de usar keyword em maps:
;1. Intenção clara: :status representa um identificador, enquanto "status" parece texto.
;2. Consistência: você pode reutilizar a mesma keyword em vários mapas.
;3. Uso idiomático: keywords podem ser usadas diretamente como funções para consultar mapas, como (:nome tarefa).

;Mas por que uma keyword funciona como função?
;Em Clojure, uma keyword pode ser chamada passando um mapa como argumento. Ela procura a si mesma como chave e retorna o valor correspondente.
(:nome tarefa)

(keyword? :status)
(keyword? "status")
(string? :status)
(string? "status")

(def tarefa-3
  {:nome "Estudar Clojure"
   :status :em-andamento})

(:prioridade tarefa-3)
(:prioridade tarefa-3 :normal)
(get tarefa-3 :prioridade :normal)

(def status-atual :em-andamento)
(= status-atual :em-andamento)
(= status-atual :concluido)

;Exercício 8 — Validar o estado
;Escreva uma expressão com if que retorne:
;- "Tarefa concluída" quando :status for :concluido.
;- "Tarefa pendente" caso contrário.
(if (= (:status tarefa) :concluido) "Tarefa concluída" "Tarefa pendente")


;Exercício 9 — Atualizar o estado
;Crie uma nova versão de tarefa com o status :concluido, usando assoc.
;Depois, mostre como verificar se o mapa original continua com :em-andamento.
(assoc tarefa :status :concluido)
(:status tarefa)


;Exercício 10 — Categorias consistentes
;Imagine que o LifeOS aceite apenas estas prioridades:
;1. Por que faz sentido usar keywords nesse conjunto em vez de strings?
;2. Escreva uma expressão usando contains? para verificar se :alta está entre as prioridades permitidas.
; Pois tem os valores ja definidos e é mais idiomatico
(contains? #{:baixa :media :alta} :alta)
