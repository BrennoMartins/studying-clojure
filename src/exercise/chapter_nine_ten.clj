(ns exercise.chapter-nine-ten)

;Parte 1 — Fundamentos
;Exercício 1 — Escolhendo a referência correta
;Para cada situação, escolha entre Atom, Ref, Agent e Var.
;
;A. Um contador de acessos precisa ser incrementado imediatamente por várias operações concorrentes.
;
;B. Uma transferência bancária precisa debitar uma conta e creditar outra de maneira transacional.
;
;C. Um sistema precisa processar tarefas de atualização de estatísticas de maneira assíncrona.
;
;D. Uma configuração global precisa ter um valor temporário durante a execução de determinado contexto.
;
;Explique brevemente por que escolheu cada uma.
;Exercício 2 — Identificando problemas
;Analise:
;(def estoque (atom 10))
;
;(defn vender []
;  (when (pos? @estoque)
;    (swap! estoque dec)))
;
;
;Responda:
;1. Existe algum problema de concorrência?
;2. Duas threads poderiam vender o último produto?
;3. Como você corrigiria a implementação?
;4. Por que simplesmente utilizar locking ou Ref não seria necessariamente a melhor solução?
;Parte 2 — Aplicação prática
;Exercício 3 — Transferência bancária com Ref
;Considere:
;(def conta-a (ref 5000))
;(def conta-b (ref 3000))
;
;
;Implemente:
;(defn transferir
;  [origem destino valor]
;  ...)
;
;
;Requisitos:
;- Utilizar dosync.
;- Não permitir valores negativos ou zero.
;- Não permitir saldo insuficiente.
;- Não permitir transferência para a mesma conta.
;- Garantir que nenhuma conta seja alterada quando a transferência for inválida.
;- Retornar um resultado que indique sucesso ou motivo da falha.
;Exercício 4 — alter ou commute?
;Considere as operações abaixo:
;
;A. Incrementar o número de visualizações de um artigo.
;
;B. Debitar uma conta bancária se houver saldo suficiente.
;
;C. Somar a quantidade de produtos vendidos em um relatório.
;
;D. Atualizar o saldo de duas contas em uma transferência.
;
;Para cada operação, escolha alter ou commute e justifique.
;
;Não considere apenas se a operação é matematicamente comutativa. Pense também nas regras de negócio e nas leituras realizadas.
;Exercício 5 — Agents e processamento assíncrono
;Um sistema recebe vendas e precisa acumular o total vendido.
;(def total-vendas (agent 0))
;
;
;Implemente:
;1. Uma função que registre uma venda usando send.
;2. Uma função que registre uma operação potencialmente bloqueante usando send-off.
;3. Uma forma de aguardar o processamento das operações.
;4. Uma forma de consultar o total atualizado.
;5. Explique o que acontece se uma ação de Agent lançar uma exceção.
;Parte 3 — Vars e execução concorrente
;Exercício 6 — Dynamic Binding
;Considere:
;(def ^:dynamic *comissao* 0.05)
;
;(defn calcular-comissao [venda]
;  (* venda *comissao*))
;
;
;Determine o resultado de cada expressão:
;(calcular-comissao 1000)
;
;(binding [*comissao* 0.10]
;  (calcular-comissao 1000))
;
;(calcular-comissao 1000)
;
;(binding [*comissao* 0.20]
;  @(future
;     (calcular-comissao 1000)))
;
;*comissao*
;
;
;Explique por que os valores são diferentes ou iguais.
;Exercício 7 — Alterando o valor raiz
;Considere:
;(def ^:dynamic *limite* 1000)
;
;
;Implemente:
;1. atualizar-limite: substitui o valor raiz.
;2. aumentar-limite: aumenta o valor raiz por um percentual.
;3. Uma execução com binding que altere temporariamente o limite para 500.
;4. Uma verificação que demonstre que o valor raiz permanece diferente do binding temporário.
;Explique a diferença entre binding e alter-var-root.
;Parte 4 — Integração e raciocínio
;Exercício 8 — Identificando efeitos colaterais
;Analise:
;(def total (atom 0))
;
;(defn processar-venda [venda]
;  (swap! total + (:valor venda))
;  (println "Venda processada")
;  venda)
;
;(pmap processar-venda
;      [{:valor 100}
;       {:valor 200}
;       {:valor 300}])
;
;
;Responda:
;1. A função é pura?
;2. Quais são os efeitos colaterais?
;3. Por que esse código pode ser problemático?
;4. Reescreva o processamento usando uma função pura.
;5. Como calcular o total depois do processamento?
;Exercício 9 — Processamento paralelo
;Temos uma coleção de produtos:
;(def produtos
;  [{:nome "Arroz" :preco 25}
;   {:nome "Feijão" :preco 10}
;   {:nome "Óleo" :preco 8}
;   {:nome "Açúcar" :preco 5}
;   {:nome "Café" :preco 18}])
;
;
;Implemente um processamento que:
;1. Calcule 10% de desconto em cada produto usando pmap.
;2. Retorne uma nova coleção sem modificar a original.
;3. Filtre os produtos cujo preço final seja superior a 10.
;4. Calcule o valor total dos produtos após o desconto.
;Utilize pmap, filter e reduce.
;Parte 5 — Desafio final
;Exercício 10 — Sistema de vendas concorrente
;Imagine um pequeno sistema de vendas com:
;(def estoque (ref 20))
;(def total-vendido (atom 0))
;(def vendas-processadas (agent []))
;
;
;Implemente uma solução que permita:
;1. Registrar uma venda de determinada quantidade de produtos.
;2. Impedir vendas quando não houver estoque suficiente.
;3. Atualizar o estoque de maneira transacional.
;4. Acumular o valor vendido no total.
;5. Registrar os dados da venda de maneira assíncrona utilizando Agent.
;6. Consultar o estoque, o total vendido e as vendas processadas.
;Regras:
;- Uma venda inválida não pode alterar nenhum estado.
;- O estoque não pode ficar negativo.
;- O valor total deve ser consistente.
;- O registro assíncrono deve acontecer somente após uma venda válida.
;Explique por que escolheu cada mecanismo de estado.