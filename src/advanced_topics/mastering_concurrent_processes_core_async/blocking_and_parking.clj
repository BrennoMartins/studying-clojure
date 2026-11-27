(ns advanced-topics.mastering-concurrent-processes-core-async.blocking-and-parking)

;3.1 O que significa bloquear uma thread?
;Quando uma thread é bloqueada, ela fica parada aguardando alguma operação terminar.
;Por exemplo:
;(def canal (async/chan))
;(async/<!! canal)
;Se não houver nenhuma mensagem disponível, a thread que executou <!! ficará bloqueada até alguém enviar uma mensagem.
;Enquanto isso, aquela thread não consegue executar outras tarefas.

;3.2 O que significa estacionar um processo?
;O core.async introduz uma alternativa chamada parking.
;Em vez de bloquear a thread, o processo é suspenso temporariamente. A thread fica livre para executar outros processos.
;Veja:
;(def canal (async/chan))
;(async/go
;  (let [mensagem (async/<! canal)]
;    (println mensagem)))

;O que acontece?
;1. O processo começa a executar.
;2. Chega à operação <!.
;3. Como não existe mensagem disponível, o processo é estacionado.
;4. A thread fica livre para executar outros processos.
;5. Quando uma mensagem chega, o processo pode continuar de onde parou.

;Parking — processo estacionado
;Processo A
;Aguardando mensagem

;Thread disponível
;Pode executar outros processos

;Essa é uma das grandes vantagens do core.async.

;3.3 Blocking versus Parking
;Vamos comparar diretamente:
;Característica	/ Blocking	/ Parking
;Suspende a execução?	/ Sim	/ Sim
;Mantém a thread bloqueada?	/ Sim	/ Não
;Permite à thread executar outras tarefas?	/ Não, enquanto bloqueada /	Sim
;Operadores	>!!, <!! /	>!, <!
;Contexto típico	/ Código convencional	/ Blocos go

;A diferença fundamental não é simplesmente esperar ou não esperar. Ambos podem esperar.
;A diferença é o que acontece com a thread enquanto a operação aguarda.

;3.4 Entendendo os quatro operadores
;Agora podemos organizar os operadores que já conhecemos:
;Operador	Operação	Comportamento
;>!!	Enviar	Bloqueia a thread
;<!!	Receber	Bloqueia a thread
;>!	Enviar	Estaciona o processo
;<!	Receber	Estaciona o processo

;Exemplo de comunicação bloqueante:
;(def canal (async/chan))
;(async/>!! canal "Olá")
;(async/<!! canal)

;Atenção: se executar o envio primeiro, sem buffer e sem um consumidor concorrente, o envio ficará bloqueado.
;O exemplo é ilustrativo, não uma sequência que deve ser executada isoladamente no REPL.

;Exemplo de comunicação com estacionamento:
;(def canal (async/chan))

;(async/go
;  (async/>! canal "Olá"))

;(async/go
;  (let [mensagem (async/<! canal)]
;    (println mensagem)))
;Aqui, os processos podem aguardar a comunicação sem bloquear as threads do pool de go.

;3.5 Uma restrição muito importante
;Dentro de um bloco go, devemos utilizar os operadores de parking:
;(async/go
;  (async/<! canal))

;Evite fazer isto:
;(async/go
;  (async/<!! canal))

;O <!! bloqueia a thread que está executando o bloco.
;Como as threads são compartilhadas pelo mecanismo de go, isso pode prejudicar outros processos e até provocar situações de starvation.
;Da mesma forma, <! e >! devem ser usados dentro de contextos go apropriados, e não em funções convencionais.


;O que guardar deste tópico
;1. Blocking suspende a execução e mantém a thread bloqueada.
;2. Parking suspende o processo, mas libera a thread.
;3. >!! e <!! são operações bloqueantes.
;4. >! e <! são operações de parking.
;5. Dentro de go, prefira operações de parking.
;Blocking and Parking concluído