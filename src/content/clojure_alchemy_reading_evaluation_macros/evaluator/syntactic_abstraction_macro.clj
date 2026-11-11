(ns content.clojure-alchemy-reading-evaluation-macros.evaluator.syntactic-abstraction-macro)

;1. O problema: funções aninhadas
;Imagine que temos uma lista de produtos e queremos:
;1. Filtrar produtos ativos.
;2. Ordenar pelo preço.
;3. Pegar os três primeiros.
;4. Extrair seus nomes.
;Sem threading macros, poderíamos escrever:
;(map :nome
;     (take 3
;           (sort-by :preco
;                    (filter :ativo produtos))))
;
;
;Funciona, mas precisamos ler de dentro para fora:
;- Primeiro filter.
;- Depois sort-by.
;- Depois take.
;- Finalmente map.
;Conforme a quantidade de operações aumenta, esse formato pode ficar difícil de acompanhar.

;2. A solução: ->>
;O ->> permite escrever as operações na ordem em que queremos executá-las:
;(->> produtos
;     (filter :ativo)
;     (sort-by :preco)
;     (take 3)
;     (map :nome))

;O resultado é o mesmo.
;O ->> pega o resultado da expressão anterior e o insere como último argumento da próxima expressão.

;Conceitualmente, ele transforma nosso código em:
;(map :nome
;     (take 3
;           (sort-by :preco
;                    (filter :ativo produtos))))
;
;Isso é uma macro: ela transforma uma forma de código em outra antes da avaliação.

;3. E o ->?
;Existe outra macro semelhante: ->, chamada thread-first.
;A diferença é a posição em que o resultado anterior é inserido.
;- ->: insere como primeiro argumento.
;- ->>: insere como último argumento.
;Veja:
;(-> 10
;    (+ 5)
;    (* 2))
;
;
;Expansão conceitual:
;(* (+ 10 5) 2)

;Resultado: 30.
;Agora:
;(->> [1 2 3]
;     (map inc)
;     (reduce +))

;Expansão conceitual:
;(reduce + (map inc [1 2 3]))
;Resultado: 9.

;4. Outros threading macros
;Além de -> e ->>, existem outros recursos relacionados:
;Macro	Comportamento
;->	Insere como primeiro argumento
;->>	Insere como último argumento
;some->	Como ->, mas interrompe se encontrar nil
;some->>	Como ->>, mas interrompe se encontrar nil
;cond->	Executa etapas conforme condições
;cond->>	Mesmo conceito, inserindo como último argumento
;
;Um exemplo com some->:
;(-> pessoa
;    :endereco
;    :cidade
;    clojure.string/upper-case)
;
;Se pessoa ou algum acesso intermediário resultar em nil, o encadeamento é interrompido e retorna nil, em vez de tentar executar a próxima operação.
;Já cond-> permite aplicar transformações condicionalmente:
;(cond-> {:nome "Arroz" :preco 25}
;        true (assoc :ativo true)
;        false (assoc :desconto 10))
;Resultado:
;{:nome "Arroz" :preco 25 :ativo true}

;5. O que significa abstração sintática?
;É a capacidade de criar uma forma de escrever código que representa uma operação mais complexa.
;O ->> é um exemplo: em vez de escrever funções profundamente aninhadas, escrevemos uma sequência de transformações.
;Macros permitem criar essas abstrações, fazendo com que o código expresse melhor a intenção do programador.
;É uma das características que tornam Clojure tão extensível: a linguagem não se limita às construções sintáticas que vieram originalmente com ela.



;Etapa 1 — Exercícios de threading macros
;Vamos trabalhar com situações próximas do que você já conhece: produtos, estoque, vendas e controle financeiro.
;Antes: uma referência rápida
;Macro	Comportamento
;some->	Encadeia inserindo o resultado como primeiro argumento e interrompe em nil.
;some->>	Mesmo comportamento, mas insere como último argumento.
;cond->	Executa transformações como primeiro argumento, conforme condições.
;cond->>	Mesmo comportamento, inserindo como último argumento.
;
;Exercício 1 — some-> (acesso seguro)
;Temos os seguintes dados:
(def cliente
  {:nome "Carlos"
   :endereco {:cidade "Santo André"
              :estado "SP"}})

;Objetivo: crie uma expressão com some-> que:
;1. Acesse o endereço do cliente.
;2. Acesse a cidade.
;3. Transforme a cidade em letras maiúsculas.
;Resultado esperado:
;"SANTO ANDRÉ"
(some-> cliente
        :endereco
        :cidade
        (clojure.string/upper-case))

(clojure.string/upper-case ((cliente :endereco) :cidade))

;Agora considere:
(def cliente-sem-endereco
  {:nome "Carlos"})

(some-> cliente-sem-endereco
        :endereco
        :cidade
        (clojure.string/upper-case))


;A mesma expressão deve retornar nil, sem lançar uma exceção ao tentar transformar a cidade em maiúsculas.
;caso eu nao tivesse o some->
;(when-let [cidade (-> cliente
;                      :endereco
;                      :cidade)]
;  (clojure.string/upper-case cidade))
;
;
;Se a cidade existir, transforma em maiúsculas. Se for nil, retorna nil.
;
;2. Usando apenas let e if
;(let [cidade (-> cliente
;                 :endereco
;                 :cidade)]
;  (if (some? cidade)
;    (clojure.string/upper-case cidade)
;    nil))

;Exercício 2 — some->> (pipeline seguro)
;Temos uma função que consulta um produto pelo código:
(defn buscar-produto [codigo]
  (get {101 {:nome "Arroz" :preco 25.0}
        102 {:nome "Feijão" :preco 8.0}}
       codigo))

;E outra função:
(defn calcular-desconto [produto]
  (* (:preco produto) 0.90))

;Objetivo: utilize some->> para:
;1. Buscar o produto pelo código 101.
;2. Extrair seu preço.
;3. Aplicar um desconto de 10%.
;Resultado esperado: 22.5.

(some->> 101
         (buscar-produto)
         (calcular-desconto))

(some->> 999
         (buscar-produto)
         (calcular-desconto))

;Depois, experimente com o código 999, que não existe. O resultado deve ser nil.
;Dica: observe a posição em que cada função recebe o argumento.

;Exercício 3 — cond-> (transformações condicionais)
;Temos:
(def produto
  {:nome "Notebook"
   :preco 3000
   :estoque 3})


;Queremos atualizar o produto conforme algumas regras:
;- Se o estoque for menor que 5, adicionar :estoque-baixo true.
;- Se o preço for maior que 2000, adicionar :produto-premium true.
;- Se o estoque for maior que 0, adicionar :disponivel true.
;Objetivo: implemente tudo usando apenas cond->.


;(cond-> valor-inicial
;        condicao-1 transformacao-1
;        condicao-2 transformacao-2
;        condicao-3 transformacao-3)
(cond-> produto
        (< (:estoque produto) 5)
        (assoc :estoque-baixo true)
        (< (:preco produto) 2000)
        (assoc :produto-premium true)
        (> (:estoque produto) 0)
        (assoc :disponivel true))


;Resultado esperado:
;{:nome "Notebook"
; :preco 3000
; :estoque 3
; :estoque-baixo true
; :produto-premium true
; :disponivel true}
;
;
;Atenção: as condições devem ser avaliadas com base no produto original recebido pelo cond->.

;Exercício 4 — cond->> (pipeline condicional)
;Temos uma coleção de vendas:
(def vendas
  [{:cliente "Ana" :total 150 :status :concluida}
   {:cliente "Carlos" :total 80 :status :pendente}
   {:cliente "Maria" :total 250 :status :concluida}
   {:cliente "João" :total 50 :status :cancelada}
   {:cliente "Pedro" :total 300 :status :concluida}])

;
;Queremos montar um relatório que possa receber três parâmetros:
(def incluir-pendentes? true)
(def valor-minimo 250)
(def ordenar-por-valor? true)

;Objetivo: crie uma expressão com cond->> que:
;1. Se incluir-pendentes? for falso, remova as vendas pendentes e canceladas, mantendo apenas as concluídas.
;2. Se valor-minimo for maior que zero, mantenha apenas vendas com total maior ou igual ao mínimo.
;3. Se ordenar-por-valor? for verdadeiro, ordene pelo total, do maior para o menor.
;Com os parâmetros apresentados, o resultado deve conter três vendas, ordenadas por valor decrescente: Pedro, Maria e Ana.

;Dica: lembre-se de que filter, remove e sort-by recebem a coleção como último argumento.

(cond->> vendas
         (not incluir-pendentes?)
         (remove #(contains? #{:pendente :cancelada} (:status %)))

         (> valor-minimo 0)
         (filter #(>= (:total %) valor-minimo))

         ordenar-por-valor?
         (sort-by :total > ))