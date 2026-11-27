(ns advanced-topics.mastering-concurrent-processes-core-async.buffering)

;Na seção anterior, conhecemos os canais (chan) e aprendemos que eles permitem a comunicação entre processos.
;Agora vamos entender um problema importante:
;O que acontece quando um processo envia mensagens mais rápido do que outro consegue recebê-las?
;É para isso que existem os buffers.

;2.1 Canal sem buffer
;Quando criamos um canal assim:
;(def canal (async/chan))
;Temos um canal sem buffer.
;Isso significa que o envio e o recebimento precisam se encontrar.

;Imagine dois processos:
;- Processo A tenta enviar uma mensagem.
;- Processo B tenta receber uma mensagem.
;O envio só é concluído quando existe um recebimento correspondente.
;(async/>!! canal "Mensagem")
;Se ninguém estiver recebendo, essa operação ficará bloqueada.
;Esse comportamento é conhecido como rendezvous: produtor e consumidor precisam se encontrar para realizar a transferência.

;2.2 Canal com buffer fixo
;Podemos criar um canal com capacidade para armazenar mensagens:
;(def canal (async/chan 3))
;Agora temos espaço para três mensagens.

;Podemos enviar:
;(async/>!! canal "Mensagem 1")
;(async/>!! canal "Mensagem 2")
;(async/>!! canal "Mensagem 3")

;Esses envios podem ser concluídos mesmo sem um consumidor naquele momento.
;Mas o que acontece se tentarmos enviar uma quarta mensagem?
;(async/>!! canal "Mensagem 4")
;Atenção
;O envio ficará bloqueado até que algum consumidor retire uma mensagem do buffer.

;Visualmente:
;Buffer com capacidade 3
;Msg 1
;Msg 2
;Msg 3
;Buffer cheio — a próxima mensagem precisa aguardar.

;2.3 Tipos de buffer
;O core.async oferece diferentes estratégias de armazenamento.
;A. Fixed Buffer
;Armazena mensagens até atingir sua capacidade.
;(def canal
;  (async/chan (async/buffer 3)))
;Quando estiver cheio, novos envios precisam aguardar.
;É útil quando queremos preservar todas as mensagens, respeitando o ritmo do consumidor.

;B. Dropping Buffer
;Descarta novas mensagens quando o buffer está cheio.
;(def canal
;  (async/chan (async/dropping-buffer 3)))
;Imagine que o buffer já contém:
;[10, 20, 30]
;Tentamos enviar 40.
;O resultado continua:
;[10, 20, 30]
;A mensagem nova é descartada.
;É útil quando não queremos bloquear o produtor e podemos tolerar perda de mensagens mais recentes.

;C. Sliding Buffer
;Quando o buffer está cheio, descarta a mensagem mais antiga para armazenar a nova.
;(def canal
;  (async/chan (async/sliding-buffer 3)))

;Imagine:
;[10, 20, 30]
;Enviamos 40.
;
;O resultado passa a ser:
;[20, 30, 40]
;É útil quando queremos preservar os dados mais recentes, como leituras de sensores ou atualizações de posição.

;2.4 Comparação dos buffers
;Tipo	/ Quando fica cheio
;Sem buffer	/ Produtor aguarda consumidor
;Fixed	/ Produtor aguarda espaço
;Dropping	/ Descarta a mensagem nova
;Sliding	/ Descarta a mensagem mais antiga

;2.5 Exemplo de aplicação: monitoramento de temperatura
;Imagine um sistema que recebe temperaturas continuamente.
;(def temperaturas
;  (async/chan (async/sliding-buffer 5)))
;Nesse cenário, talvez não seja interessante acumular centenas de leituras antigas.
;Se o consumidor estiver atrasado, preferimos manter as cinco leituras mais recentes.
;Por outro lado, em um sistema financeiro, descartar transações seria inaceitável. Um buffer fixo pode ser mais apropriado, acompanhado de uma estratégia de persistência e tratamento de falhas.
;Importante: buffers não tornam o processamento mais rápido. Eles ajudam a administrar diferenças de ritmo entre produtores e consumidores.

;O que você precisa guardar deste tópico
;- Canal sem buffer: envio e recebimento precisam se encontrar.
;- Fixed Buffer: armazena até atingir a capacidade e aplica backpressure.
;- Dropping Buffer: descarta mensagens novas quando está cheio.
;- Sliding Buffer: descarta mensagens antigas para manter as recentes.
;Buffering concluído