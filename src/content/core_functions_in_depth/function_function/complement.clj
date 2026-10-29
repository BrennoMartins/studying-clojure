(ns content.core-functions-in-depth.function-function.complement)

;O complement recebe uma função e retorna outra que inverte seu resultado booleano.
;Por exemplo, imagine que temos uma função que verifica se um número é positivo:
(defn positivo?
  [numero]
  (pos? numero))

;Podemos utilizá-la:
(positivo? 10)
;;; => true
;
(positivo? -5)
;;; => false
;
;Agora, usando complement:
(def nao-positivo?
  (complement positivo?))

(nao-positivo? 10)
;;; => false

(nao-positivo? -5)
;;; => true

;O complement criou uma função que retorna o oposto do resultado de positivo?.
;Um exemplo prático com filter
;Imagine que temos uma coleção de tarefas:
(def tarefas
  [{:nome "Estudar Clojure" :concluida true}
   {:nome "Treinar" :concluida false}
   {:nome "Ler livro" :concluida true}
   {:nome "Estudar inglês" :concluida false}])

;Podemos criar um predicado para tarefas concluídas:
(defn concluida?
  [tarefa]
  (:concluida tarefa))
;
;Para selecionar as tarefas concluídas:
(filter concluida? tarefas)

;E para selecionar as tarefas não concluídas, podemos aproveitar a função existente:
(filter (complement concluida?) tarefas)

;Assim, evitamos criar manualmente uma segunda função com a lógica inversa.
;⚠️ Uma observação importante
;O complement inverte a truthiness do resultado: se a função original retorna um valor verdadeiro, a nova retorna false; se retorna nil ou false, a nova retorna true.
;Portanto, não é necessário que a função original retorne explicitamente um booleano.
;🧪 Exercícios — complement
;Exercício 1 — Números pares
;Crie uma função chamada impar? utilizando complement e a função even?.
(def impar?
  (complement even?))

;Resultados esperados:
(impar? 3)
;;; => true

(impar? 8)
;;; => false
;
;Regra: não use defn para implementar a lógica de impar?; utilize complement.
;Exercício 2 — Tarefas pendentes
;Considere:
(def tarefas
  [{:nome "Estudar Clojure" :concluida true}
   {:nome "Treinar" :concluida false}
   {:nome "Ler livro" :concluida true}
   {:nome "Estudar inglês" :concluida false}])

;Crie uma função concluida? que receba uma tarefa e retorne o valor de :concluida.
(defn concluida?
  [tarefa]
  (:concluida tarefa))

(filter (complement concluida?) tarefas)
;Depois, utilize filter e complement para selecionar as tarefas pendentes.
;
;O resultado deve conter:
;[{:nome "Treinar" :concluida false}
; {:nome "Estudar inglês" :concluida false}]
;
;Exercício 3 — Composição de conceitos
;Considere:
(def numeros [1 2 3 4 5 6 7 8 9 10])

(def par)
;Utilize filter, complement e even? para retornar um vetor contendo somente os números ímpares.
(->> numeros
     (filter impar?)
     (into []))

;Resultado esperado:
;[1 3 5 7 9]
;
;Quando terminar, faremos a revisão e fecharemos essa seção do capítulo 4.
