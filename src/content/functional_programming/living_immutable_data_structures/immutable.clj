(ns content.functional-programming.living-immutable-data-structures.immutable)

;Por que isso é Imutabilidade é importante?
;Imagine que durante o processamento de uma venda você precise:
;- Consultar o estoque original.
;- Calcular o estoque após a venda.
;- Gerar um relatório com os valores anteriores.
;- Comparar os valores antes e depois.
;Com dados imutáveis, você mantém as versões anteriores disponíveis sem precisar fazer cópias defensivas manualmente.
;
;Isso ajuda a reduzir efeitos colaterais e facilita testes.


;4. Exercício prático — Atualização de estoque
;Vamos trabalhar com um exemplo próximo do nosso projeto de mercadinho.
;
;Considere:
(def produtos
  [{:nome "Arroz" :estoque 10 :preco 25.0}
   {:nome "Feijão" :estoque 5 :preco 8.0}
   {:nome "Óleo" :estoque 8 :preco 7.5}
   {:nome "Açúcar" :estoque 12 :preco 4.5}])


;Precisamos implementar três funções.
;Exercício 1 — Atualizar estoque

(defn atualizando-estoque
  [produto-atualizar quantidade produto]
  (if (= produto-atualizar (:nome produto))
    (assoc produto :estoque quantidade)
    produto))





(defn atualizar-estoque
  [lista-produtos produto-atualizar quantidade]
  (->> lista-produtos
       (map (fn [produto]
              (atualizando-estoque produto-atualizar quantidade produto)))))

(defn buscar-estoque
  [lista-produtos produto-buscar]
  (->> lista-produtos
       (filter #(= produto-buscar (:nome %)))
       (map :estoque)
       (reduce + 0)))

(defn atualizar-estoque-usando-partial
  [lista-produtos produto-atualizar quantidade]
  (map
    (partial atualizando-estoque produto-atualizar quantidade)
    lista-produtos))

(atualizar-estoque produtos "Arroz" 15)
(atualizar-estoque-usando-partial produtos "Arroz" 15)
;Resultado esperado: uma nova coleção em que o arroz tenha estoque 15. Os outros produtos permanecem iguais.

;Exercício 2 — Aplicar uma venda
(defn baixando-estoque-venda
  [quantidade produto]
  (update produto :estoque - quantidade))

(defn atualizando-estoque-venda
  [produto-atualizar quantidade produto]
  (if (= produto-atualizar (:nome produto))
    (baixando-estoque-venda quantidade produto)
    produto))

(defn aplicar-venda
  [lista-produtos
   produto-comprado
   quantidade]
    (map (partial atualizando-estoque-venda produto-comprado quantidade) lista-produtos))

(aplicar-venda produtos "Feijão" 2)
;Resultado esperado: o estoque do feijão passa de 5 para 3.

;Exercício 3 — Produtos com estoque baixo
(defn produtos-estoque-baixo
  [produtos
   quantidade]
  (->> produtos
       (filter #(<= (:estoque %) quantidade ))))

(produtos-estoque-baixo produtos 8)
;
;
;Resultado esperado: retornar os produtos com estoque menor ou igual a 8.
;Regras
;- Não altere a coleção original.
;- Não utilize atom, ref ou volatile!.
;- Utilize funções puras.
;- Você pode utilizar map, filter, assoc e update.