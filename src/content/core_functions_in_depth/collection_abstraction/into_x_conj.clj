(ns content.core-functions-in-depth.collection-abstraction.into-x-conj)

;1. O que significa abstração de coleções?
;Até agora, você aprendeu a transformar coleções usando funções como map e filter.
;Por exemplo:
(map inc [1 2 3])
;;; (2 3 4)

;Mas observe que o resultado é uma sequência, e não necessariamente um vetor.
;E se você quiser transformar os elementos e obter um vetor como resultado?
;É aí que entra a função into.

; 2. Conhecendo into
;A função into permite inserir os elementos de uma coleção em outra coleção, preservando o tipo da coleção de destino.
;Exemplo 1 — Transformando uma sequência em vetor
(into [] (map inc [1 2 3]))
;Resultado:
;[2 3 4]

;Compare com:
(map inc [1 2 3])
;; (2 3 4)

;A diferença é que into reúne os elementos em um vetor vazio, produzindo um vetor como resultado.
;Exemplo 2 — Adicionando elementos a um vetor existente
(into [1 2] [3 4 5])
;Resultado:
;[1 2 3 4 5]

;O primeiro argumento é a coleção de destino; o segundo é a coleção cujos elementos serão adicionados.
;Exemplo 3 — Trabalhando com conjuntos
;(into #{1 2} [2 3 4])
;Resultado:
;#{1 2 3 4}
;Como o destino é um conjunto (set), os elementos duplicados não são mantidos.
;A ideia principal: into permite combinar coleções e direcionar o resultado para o tipo de coleção que você deseja.

;3. Entendendo melhor conj
;Você já estudou conj, mas agora vamos relacioná-la com into.
;A função conj adiciona um ou mais elementos a uma coleção.
;Vetores
;(conj [1 2] 3)
;;; [1 2 3]

;No vetor, os elementos são adicionados ao final.
;Listas
;(conj '(1 2) 3)
;;; (3 1 2)

;Na lista, os elementos são adicionados ao início.
;Conjuntos
;(conj #{1 2} 3)
;;; #{1 2 3}

;No conjunto, o elemento é adicionado, sem duplicar valores existentes.

;Portanto, o comportamento de conj depende do tipo de coleção.
;Diferença entre conj e into
;Compare:
;(conj [1 2] [3 4])
;;; [1 2 [3 4]]
;Aqui, o vetor [3 4] é adicionado como um único elemento.
;
;Já:
;(into [1 2] [3 4])
;;; [1 2 3 4]

;Nesse caso, os elementos da segunda coleção são incorporados individualmente à coleção de destino.
;Essa é uma diferença importante entre as duas funções.

;4. Exemplo prático no LifeOS
;Imagine que você tenha uma lista de atividades:
(def atividades
  [{:nome "Estudar Clojure" :minutos 60}
   {:nome "Academia" :minutos 50}
   {:nome "Ler livro" :minutos 30}])

;Você quer selecionar as atividades que duraram pelo menos 40 minutos e retornar um vetor.
;Uma forma de fazer isso é:
(into []
      (filter #(>= (:minutos %) 40) atividades))
;Resultado:
;[{:nome "Estudar Clojure" :minutos 60}
; {:nome "Academia" :minutos 50}]

;Observe a combinação:
;1. filter seleciona as atividades.
;2. into reúne o resultado em um vetor.
;Isso é especialmente útil quando você quer garantir que uma função retorne uma coleção de determinado tipo.

;5. Usando into com map
;Podemos combinar as duas funções para transformar os dados e retornar um vetor.
;Imagine que você queira obter apenas os nomes das atividades:
(into []
      (map :nome atividades))
;Resultado:
;["Estudar Clojure" "Academia" "Ler livro"]
;
;Uma alternativa mais idiomática é usar o threading macro ->>:
(->> atividades
     (map :nome)
     (into []))
;
;O resultado é o mesmo.
;Essa combinação é muito comum em código Clojure:
;- map transforma os elementos.
;- filter seleciona os elementos.
;- into reúne os resultados na coleção desejada.


;🧪 Exercícios para praticar
;Vamos fazer três exercícios curtos antes de avançar.
;Exercício 1 — Filtrar e retornar um vetor
;Considere:
(def tarefas
  [{:nome "Estudar" :minutos 60}
   {:nome "Treinar" :minutos 30}
   {:nome "Ler" :minutos 45}
   {:nome "Descansar" :minutos 20}])
;
;Crie uma expressão que selecione as tarefas com pelo menos 40 minutos e retorne um vetor.
(defn tarefas-mais-40
  [tarefas]
  (->> tarefas
       (filter #(<= 40 (:minutos %)))
       (into [])))

(tarefas-mais-40 tarefas)

;Exercício 2 — Transformar e retornar um vetor
;Utilizando a mesma coleção, crie uma expressão que retorne um vetor contendo somente os nomes das tarefas.
(defn nome-das-tarefas
  [tarefas]
  (->> tarefas
       (map :nome)
       (into [])))

(nome-das-tarefas tarefas)

;Utilize map e into.
;Exercício 3 — Combinar coleções
;Considere:
(def tarefas-concluidas
  ["Estudar" "Treinar"])

(def novas-tarefas
  ["Ler" "Meditar"])

;Crie uma expressão que combine as duas coleções e retorne um vetor com todas as tarefas, na ordem apresentada.
(defn juntar-tarefas [] (into tarefas-concluidas novas-tarefas))

(juntar-tarefas)