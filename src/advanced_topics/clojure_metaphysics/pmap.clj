(ns advanced-topics.clojure-metaphysics.pmap)

;1. O que é pmap?
;Você já conhece map:
(map inc [1 2 3 4 5])
;;; (2 3 4 5 6)

;O map aplica uma função a cada elemento de uma coleção, normalmente de maneira sequencial.
;O pmap faz algo semelhante, mas permite que os elementos sejam processados em paralelo, utilizando múltiplas threads.
(pmap inc [1 2 3 4 5])
;;; (2 3 4 5 6)

;A diferença não está no resultado, mas na maneira como o processamento acontece.
;Comparação conceitual

;map — processamento sequencial
;1
;2
;3
;4
;Um processamento após o outro

;pmap — processamento paralelo
;1
;
;
;2
;
;
;3
;
;
;4
;
;
;Várias tarefas podem ser processadas simultaneamente.


;2. Exemplo prático
;Imagine que temos uma lista de produtos e queremos calcular o preço com desconto.
(def produtos
  [{:nome "Arroz" :preco 25}
   {:nome "Feijão" :preco 10}
   {:nome "Óleo" :preco 8}
   {:nome "Açúcar" :preco 5}])

;Podemos criar uma função:
(defn calcular-preco [produto]
  (update produto :preco * 0.9))

;Usando map:
(map calcular-preco produtos)

;Usando pmap:
(pmap calcular-preco produtos)
;O resultado será equivalente nos dois casos.
;A diferença é que pmap permite executar os cálculos em paralelo.

;3. Por que funções puras são importantes?
;Imagine que nossa função faça isso:
(def total (atom 0))
;
(defn calcular-total [valor]
  (swap! total + valor))

;Agora imagine executar:
(pmap calcular-total [10 20 30 40])

;Temos um estado compartilhado entre várias execuções.
;Embora swap! proteja a atualização do Atom, introduzimos coordenação entre as threads.
;Isso pode reduzir os benefícios do paralelismo e dificultar o raciocínio sobre o programa.

;Uma abordagem melhor é fazer cada processamento retornar seu próprio resultado:
(pmap #(* % 2) [10 20 30 40])
;;; (20 40 60 80)
;Depois, se necessário, podemos combinar os resultados com reduce.
;A ideia central: processamento paralelo fica mais simples quando cada tarefa recebe dados, calcula um resultado e não modifica estados compartilhados.

;4. Quando usar pmap?
;Situação	/ Recomendação
;Cálculos pesados e independentes	pmap pode ajudar
;Transformações simples	Geralmente map
;Operações dependentes umas das outras	map ou outra abordagem sequencial
;Atualizações em estado compartilhado	Avaliar cuidadosamente
;Chamadas HTTP ou I/O	Avaliar outras ferramentas de concorrência

;Um detalhe importante: pmap não garante que tudo ficará mais rápido. Existe um custo de gerenciamento das threads e, para tarefas pequenas, esse custo pode superar o benefício.
;Além disso, pmap produz uma sequência lazy: os resultados são calculados conforme são consumidos, embora possa antecipar o processamento de elementos.

;5. Exercício prático
;Vamos trabalhar com um cenário de processamento de vendas.

;Considere:
(def vendas
  [100 250 80 400 150 320 90 500])

;Implemente:
;Exercício 1 — Processamento
;Crie uma função calcular-comissao que receba uma venda e retorne 10% do seu valor.
(defn calcular-comissao
  [valor-venda]
  (* valor-venda 0.10))

;Exemplo:
(calcular-comissao 100)
;;; 10.0

;Exercício 2 — Paralelismo
;Utilize pmap para calcular as comissões de todas as vendas.

(pmap calcular-comissao vendas)
;Resultado esperado:
;(10.0 25.0 8.0 40.0 15.0 32.0 9.0 50.0)

;Exercício 3 — Consolidação
;Utilize reduce para somar todas as comissões calculadas.

(reduce + (pmap calcular-comissao vendas))
;Resultado esperado:
;189.0