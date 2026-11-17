(ns advanced-topics.concurrent-and-parallel-programming.concurrency-and-parallelism-concepts.managing-multiple-tasks-vs.managing-multiple-tasks-vs-executing-tasks-simultaneously)

;Imagine uma aplicação financeira que precisa executar três operações:
;- Consultar a cotação do dólar em uma API.
;- Consultar a cotação do euro em outra API.
;- Calcular o rendimento de uma carteira.

;Uma implementação sequencial seria:
;(defn processar-relatorio []
;  (let [dolar (consultar-dolar)
;        euro (consultar-euro)
;        rendimento (calcular-rendimento)]
;    {:dolar dolar
;     :euro euro
;     :rendimento rendimento}))

;O fluxo é:
;Execução sequencial
;1  Consultar dólar
;2  Consultar euro
;3  Calcular rendimento
;Cada operação começa depois que a anterior termina.
;Agora imagine que a consulta do dólar demora 2 segundos, a do euro demora 3 segundos e o cálculo demora 1 segundo.
;O tempo total será aproximadamente 6 segundos, desconsiderando outros custos.
;Mas essas operações não dependem umas das outras. Poderíamos executá-las concorrentemente.

;A diferença fundamental:
;- Gerenciar múltiplas tarefas: organizar e controlar várias operações.
;- Executar tarefas simultaneamente: realizar mais de uma operação no mesmo instante, normalmente usando múltiplos núcleos de processamento.


;2. Blocking and Asynchronous Tasks
;Outro conceito importante é a diferença entre operações bloqueantes e assíncronas.

;Imagine:
;(def resultado (consultar-dolar))
;Se consultar-dolar demora 2 segundos, a execução fica aguardando o resultado.
;Isso é uma operação bloqueante do ponto de vista de quem está aguardando.

;Em uma abordagem assíncrona, podemos iniciar uma operação e continuar executando outras atividades enquanto aguardamos sua conclusão.

;Uma analogia:
;- Blocking: você faz um pedido no restaurante e fica parado no balcão esperando.
;- Asynchronous: você faz o pedido, recebe uma senha e vai sentar. Quando estiver pronto, recebe uma notificação.
;Um detalhe: assíncrono não significa necessariamente paralelo. Uma operação pode ser assíncrona sem executar simultaneamente em outro núcleo.

;3. Concurrent Programming vs. Parallel Programming
;Essa distinção é uma das mais importantes do capítulo.
;Concorrência -> Múltiplas tarefas progridem durante um mesmo intervalo, alternando sua execução ou aguardando recursos.
;Paralelismo  -> Múltiplas tarefas são executadas literalmente ao mesmo tempo, por exemplo, em núcleos diferentes.
;
;
;Uma forma de memorizar:
;Conceito /	Ideia
;Concorrência	/ Lidar com várias tarefas ao mesmo tempo
;Paralelismo	/ Executar várias tarefas simultaneamente
;Assíncrono	/ Não bloquear esperando a conclusão
;Bloqueante	/ Aguardar a conclusão antes de continuar

;Uma aplicação pode ser concorrente sem ser paralela. Também pode ser concorrente e paralela ao mesmo tempo.

;Conectando com Java
;Você provavelmente já encontrou essas ideias no Java:
;Java	Conceito
;Thread	/ Unidade de execução
;ExecutorService	/ Gerenciamento de tarefas
;CompletableFuture	/ Composição de operações assíncronas
;synchronized	/ Exclusão mútua
;ForkJoinPool	/ Execução paralela de tarefas

;No Clojure, veremos ferramentas próprias para trabalhar com concorrência, especialmente future, delay, promise e os mecanismos de referência de estado.
;Uma observação importante: concorrência não é simplesmente uma otimização de desempenho. Ela também é uma questão de organização e coordenação de tarefas, especialmente quando existem estados compartilhados.