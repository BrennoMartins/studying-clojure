(ns advanced-topics.mastering-concurrent-processes-core-async.thread)

;4.1 O que é thread?
;O async/thread executa um bloco de código em uma thread dedicada àquela tarefa.
;A principal diferença em relação ao go é que o bloco de thread pode realizar operações bloqueantes sem ocupar as threads compartilhadas pelo mecanismo de go.

;Exemplo:
;(require '[clojure.core.async :as async])

;(async/thread
;  (Thread/sleep 2000)
;  "Processamento concluído")

;O que acontece?
;1. Uma tarefa é iniciada em outra thread.
;2. A execução aguarda dois segundos.
;3. O resultado "Processamento concluído" é produzido.
;4. Esse resultado é disponibilizado por um canal.
;Um detalhe importante: thread não retorna diretamente o resultado da expressão. Ele retorna um canal que receberá esse resultado.

;4.2 Como recuperar o resultado?
;Podemos utilizar <!! para receber o resultado:
;(def resultado
;  (async/thread
;    (Thread/sleep 2000)
;    "Processamento concluído"))

;(async/<!! resultado)
;;; "Processamento concluído"
;O <!! aguarda o resultado, bloqueando a thread que executa essa leitura.
;O canal retornado por thread é fechado depois que o resultado é disponibilizado.

;4.4 Comparando go e thread
;Esta é a distinção mais importante desta seção:
;Característica	/ go	/ thread
;Execução	/ Pool compartilhado de threads	/ Thread dedicada à tarefa
;Operações de canal	<!, >!	/ <!!, >!!
;Pode usar Thread/sleep?	/ Não recomendado	/ Sim
;Pode executar operações bloqueantes? /	Deve evitar	/ Sim
;Retorno	/ Processo assíncrono	/ Canal com resultado
;Observe que go não cria uma thread dedicada para cada processo. Já thread utiliza uma thread para executar o bloco de código.

;Exemplo comparativo
;Com go:
;(def canal (async/chan))
;
;(async/go
;  (async/>! canal "Resultado do go"))
;
;(async/<!! canal)
;
;
;Com thread:
;(def resultado
;  (async/thread
;    (Thread/sleep 1000)
;    "Resultado do thread"))
;
;(async/<!! resultado)
;No primeiro exemplo, utilizamos operações de parking. No segundo, podemos utilizar operações bloqueantes convencionais.

;4.5 Quando utilizar cada um?
;Imagine uma aplicação que precisa executar duas atividades:
;- Processar dados em memória.
;- Ler um arquivo grande do disco.
;Para processamento assíncrono cooperativo, go pode ser adequado.
;Para operações bloqueantes, como leitura tradicional de arquivos ou chamadas a bibliotecas que bloqueiam a thread, thread é uma alternativa mais apropriada.
;Uma observação: thread não significa necessariamente que você deve criar uma thread para cada pequena operação.
;Criar threads também tem custos, e devemos escolher o mecanismo conforme o trabalho executado.

;Resumo do tópico
;- async/thread executa código em uma thread dedicada.
;- Retorna um canal com o resultado da execução.
;- Podemos recuperar o resultado utilizando <!!.
;- Permite operações bloqueantes, como Thread/sleep.
;- É uma alternativa ao go quando precisamos executar código bloqueante.