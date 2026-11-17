(ns advanced-topics.concurrent-and-parallel-programming.clojure-implementation-JVM-threads.the-three-goblins.agent)

;3. Agents — Atualizações assíncronas de estado
;Imagine que temos um sistema de vendas e queremos manter um contador de vendas processadas.
(def vendas-processadas (agent 0))

;A função agent cria um agente cujo estado inicial é 0.
;A diferença em relação ao Atom é que não atualizamos diretamente o valor. Enviamos uma ação para o agente executar.

;1. send e send-off
;Para solicitar uma atualização, usamos send:
(send vendas-processadas inc)

;O que acontece?
;1. Enviamos a função inc ao agente.
;2. O agente recebe essa função.
;3. A função é aplicada ao estado atual.
;4. O novo estado é armazenado.
;Para consultar o estado:
@vendas-processadas

;Como a execução é assíncrona, pode ser que a atualização ainda não tenha terminado quando consultarmos o valor.
;Para aguardar as ações pendentes:
(await vendas-processadas)
;Depois disso, podemos consultar novamente.

;2. Exemplo prático
;Vamos criar um agente que contabiliza vendas:
(def vendas-processadas (agent 0))

(send vendas-processadas inc)
(send vendas-processadas inc)
(send vendas-processadas inc)

(await vendas-processadas)
;
@vendas-processadas
;;; 3

;Observe que enviamos três ações sem executar diretamente as atualizações.
;O agente processa as ações em ordem e mantém seu próprio estado.

;3. send versus send-off
;O Clojure oferece duas funções para enviar ações aos agentes:
;Função /	Quando utilizar
;send	/ Ações curtas, geralmente não bloqueantes
;send-off /	Ações que podem bloquear ou demorar, como operações de I/O
;
;Exemplo:
(send vendas-processadas inc)

;Para uma ação que realiza uma operação potencialmente bloqueante:
(send-off vendas-processadas
          (fn [total]
            (Thread/sleep 2000)
            (inc total)))
;A distinção está relacionada aos pools de Threads utilizados para executar as ações.
;- send = utiliza um pool apropriado para tarefas rápidas.
;- send-off = utiliza um pool preparado para tarefas potencialmente bloqueantes.

;4. Uma característica importante: funções puras
;As funções enviadas ao agente recebem o estado atual e retornam o novo estado.

;Por exemplo:
(defn adicionar-venda [total quantidade]
  (+ total quantidade))
;
;Podemos enviar essa função:
(send vendas-processadas adicionar-venda 5)
;O agente executará a função usando o estado atual e o argumento 5.
;O estado anterior não é alterado diretamente pela função. Ela calcula e retorna um novo valor.
;Isso conecta Agents com o que estudamos no capítulo 5: imutabilidade e funções puras.

;5. Comparando Atom, Ref e Agent
;Essa comparação é essencial para entender o modelo de concorrência do Clojure.
;Característica	/ Atom / Ref	/ Agent
;Atualização	/ Síncrona	/ Transacional	/ Assíncrona
;Coordenação entre referências	/ Não	/ Sim	/ Não diretamente
;Atualização por função	/ swap!	/ alter	/ send ou send-off
;Pode repetir a função por conflito	/ Sim, em swap! /	Sim, em transações /	Não por conflito STM
;Principal uso	/ Estado independente	/ Estado coordenado	/ Estado atualizado assincronamente

;Uma observação: tanto Atom quanto Agent podem receber funções que sejam executadas novamente em determinadas situações de falha ou reenvio.
;Por isso, mantenha as funções de atualização sem efeitos colaterais.

;6. Um detalhe: erros em Agents
;Se uma função enviada ao agente lançar uma exceção, o agente entra em estado de erro e deixa de processar novas ações até que seja reiniciado.
;Podemos consultar o estado de erro com:
(agent-error vendas-processadas)

;E reiniciar:
;(restart-agent vendas-processadas 0)
;O segundo argumento define o novo estado inicial.

;Consolidando os três mecanismos
;Atom - Atualização independente
;Uma referência é atualizada atomicamente.

;Ref - Atualização coordenada
;Várias referências são modificadas dentro de uma transação.

;Agent - Atualização assíncrona
;Enviamos funções para um agente atualizar seu próprio estado.

;Uma distinção importante: Atom, Ref e Agent são mecanismos para gerenciar estado compartilhado. Eles não são simplesmente três maneiras diferentes de criar Threads.