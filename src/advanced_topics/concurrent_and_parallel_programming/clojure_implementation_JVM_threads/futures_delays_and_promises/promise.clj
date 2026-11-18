(ns advanced-topics.concurrent-and-parallel-programming.clojure-implementation-JVM-threads.futures-delays-and-promises.promise)

;O promise permite que uma parte do programa disponibilize um resultado no futuro para outra parte que está aguardando por ele.
;A principal diferença em relação ao future é quem controla a execução e a entrega do resultado:
;- future: você fornece uma tarefa que será executada assincronamente.
;- promise: você cria um recipiente vazio para um resultado, que será entregue posteriormente por quem tiver acesso a ele.

;1. Criando uma Promise
;Usamos a função promise:
(def resultado (promise))
;Nesse momento, ela ainda não possui um valor.

;Podemos verificar:
(realized? resultado)
;;; false

;2. Entregando um valor
;Para preencher a promise, usamos deliver:
(deliver resultado 42)

;Agora podemos recuperar o valor:
@resultado
;;; 42
;E verificar novamente:
(realized? resultado)
;; true
;Importante: uma promise só pode receber seu primeiro valor. Chamadas posteriores a deliver não substituem o resultado original.

;3. Esperando por um resultado
;Imagine que uma parte do sistema precisa consultar um serviço externo, mas não sabe quando a resposta chegará.

;Podemos criar uma promise:
(def resposta (promise))

;Uma thread pode aguardar:
(future
  (println "Aguardando resposta...")
  (println "Resultado:" @resposta))

;Enquanto isso, outra parte do programa pode entregar o resultado:
(deliver resposta "Consulta concluída")
;Assim que o valor for entregue, a thread que estava aguardando continua sua execução.

;4. Exemplo prático: processamento de pagamento
;Imagine que temos um processamento de pagamento e queremos separar quem executa a operação de quem aguarda o resultado.
;(def pagamento (promise))
;
;(future
;  (Thread/sleep 3000)
;  (deliver pagamento
;           {:status :aprovado
;            :valor 150.00}))
;Enquanto o processamento acontece, podemos continua/r executando outras operações.
;Quando precisarmos do resultado:
;@pagamento

;Resultado:
;{:status :aprovado
; :valor 150.00}

;5. Future vs. Delay vs. Promise
;Característica	/ future	/ delay	/ promise
;Executa uma expressão	/ Sim	/ Sim	/ Não
;Inicia automaticamente	/ Sim / Não	/ Não
;Execução assíncrona	  / Sim	/ Não	/ Não
;Armazena resultado	    / Sim	/ Sim	/ Sim
;Quem fornece o resultado?	/ A tarefa	/ A expressão	/ Quem chama deliver

;Uma observação: uma promise não executa uma tarefa por conta própria.
; No exemplo, usamos um future para realizar o processamento e deliver para comunicar o resultado.

;Quando usar cada um?
;- Future: executar uma tarefa em segundo plano.
;- Delay: calcular algo somente quando for necessário.
;- Promise: permitir que uma parte do sistema entregue um resultado posteriormente para outra parte que está aguardando.
