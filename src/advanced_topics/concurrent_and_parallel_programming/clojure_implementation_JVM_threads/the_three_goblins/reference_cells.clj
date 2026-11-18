(ns advanced-topics.concurrent-and-parallel-programming.clojure-implementation-JVM-threads.the-three-goblins.reference-cells)

;Agora entramos em uma das partes mais importantes do capítulo 9: The Three Goblins — Reference Cells, Mutual Exclusion, and Dwarven Berserkers.

;Aqui o Clojure começa a mostrar como podemos trabalhar com estado compartilhado sem perder o controle quando várias Threads estão executando simultaneamente.
;Vamos estudar cada mecanismo separadamente.

;1. Reference Cells — Refs
;Primeiro, precisamos entender um problema.

;Imagine que temos uma carteira de investimentos com dois saldos:
(def conta-a (atom 1000))
(def conta-b (atom 500))

;Queremos transferir R$ 200 da conta A para a conta B.

;Uma implementação ingênua seria:
(swap! conta-a - 200)
(swap! conta-b + 200)

;O resultado esperado seria:
;@conta-a ; 800
;@conta-b ; 700
;Mas existe um problema: essas duas operações são independentes.
;Imagine que outra Thread consulte os saldos exatamente entre a retirada e o depósito. Ela poderá observar:
;- Conta A: R$ 800.
;- Conta B: R$ 500.
;Temos temporariamente R$ 1.300, quando o total deveria ser R$ 1.500.
;Isso é um problema de consistência.

;2. O que são Refs?
;Refs são referências para valores que podem mudar ao longo do tempo.
;A diferença importante é que elas permitem realizar alterações coordenadas usando Software Transactional Memory (STM).
;Pense em uma transação bancária:
;- Ou a retirada e o depósito acontecem juntos.
;- Ou nenhuma das duas alterações acontece.
;Não queremos uma situação intermediária visível para outras transações.

;Em Clojure, criamos uma Ref usando ref:
(def conta-a (ref 1000))
(def conta-b (ref 500))

;Para consultar o valor:
;@conta-a
;@conta-b
;Observe que a sintaxe de consulta é semelhante à de um Atom. Mas o mecanismo por trás é diferente.

;3. Alterando Refs com dosync
;Para modificar uma Ref, precisamos utilizar uma transação:
(dosync
  (alter conta-a - 200)
  (alter conta-b + 200))

;Vamos entender as funções:
;Função	/ Responsabilidade
;ref	/ Cria uma referência transacional
;dosync	/ Executa uma transação STM
;alter	/ Calcula e agenda uma alteração da Ref dentro da transação
;deref ou @	Consulta o valor atual

;O dosync garante que as alterações sejam confirmadas como uma unidade.

;Se houver conflito entre transações concorrentes, o Clojure pode tentar executar a transação novamente.

;Transferência bancária com STM
;Estado inicial
;Conta A: R$ 1.000
;Conta B: R$ 500

;Transação dosync
;Alterações coordenadas
;- Retirar R$ 200 da conta A.
;- Adicionar R$ 200 à conta B.

;Estado confirmado
;Conta A: R$ 800
;Conta B: R$ 700
;Total: R$ 1.500

;4. Comparando com Java
;Uma analogia seria uma operação transacional protegida por mecanismos de sincronização ou gerenciada por um banco de dados.

;Mas existe uma diferença importante:
;- Em Java, normalmente você precisa organizar explicitamente a sincronização com synchronized, locks ou outras ferramentas.
;- Em Clojure, o STM gerencia conflitos entre transações e pode repetir operações automaticamente.
;Isso não significa que dosync seja equivalente a uma transação de banco de dados: ele coordena o estado das Refs na memória da aplicação.

;5. Uma regra muito importante
;O corpo de um dosync pode ser executado mais de uma vez em caso de conflito.
;Portanto, evite efeitos colaterais dentro de uma transação.

;Por exemplo, não faça isso:
;(dosync
;  (println "Transferência realizada!")
;  (alter conta-a - 200)
;  (alter conta-b + 200))

;A mensagem pode ser impressa mais de uma vez caso a transação seja repetida.
;Essa é uma conexão direta com o que estudamos no capítulo 5: funções puras e isolamento de efeitos colaterais.

;Resumindo até aqui
;- atom: alteração independente de uma referência, com atualização atômica.
;- ref: alteração coordenada de referências usando STM.
;- dosync: delimita a transação.
;- alter: calcula a alteração dentro da transação.