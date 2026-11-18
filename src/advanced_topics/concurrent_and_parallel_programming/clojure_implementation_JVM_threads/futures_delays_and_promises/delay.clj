(ns advanced-topics.concurrent-and-parallel-programming.clojure-implementation-JVM-threads.futures-delays-and-promises.delay)

;2. Delays
;Um delay permite adiar a execução de uma expressão até que seu resultado seja solicitado pela primeira vez.
;Diferentemente de um future, ele não inicia uma tarefa em outra thread. A expressão só é executada quando alguém solicita seu valor.
;1. Criando um delay
;Usamos a função delay:
(def meu-delay
  (delay
    (println "Executando processamento...")
    (* 10 5)))

;Ao executar esse código, nada é impresso e a multiplicação ainda não foi realizada.
;Agora, solicitamos o resultado:
@meu-delay
;Saída:
;Executando processamento...
;50

;2. O resultado é armazenado
;Uma característica importante: depois que o delay é realizado, seu resultado fica armazenado.
;@meu-delay
;;; 50
;@meu-delay
;;; 50

;Na segunda chamada, a expressão não é executada novamente. O resultado anterior é reutilizado.

;Podemos verificar isso com realized?:
(realized? meu-delay)
;;; true
;Antes da primeira avaliação, retornaria false.

;3. Exemplo prático
;Imagine que temos uma aplicação financeira que calcula um relatório detalhado, mas nem sempre o usuário acessa essa informação.
(def relatorio-financeiro
  (delay
    (println "Gerando relatório...")
    {:receita 15000
     :despesas 9000
     :lucro 6000}))

;Enquanto ninguém solicitar o relatório, o processamento não acontece.
;Quando necessário:
@relatorio-financeiro

;Resultado:
;{:receita 15000
; :despesas 9000
; :lucro 6000}
;Se solicitar novamente, o resultado armazenado será reutilizado.

;4. Future vs. Delay
;Essa é a diferença mais importante:
;Característica	/ future	/ delay
;Execução	/ Assíncrona	/ Sob demanda
;Inicia em outra thread	/ Sim	/ Não
;Quando executa?	/ Ao ser criado	/ Na primeira solicitação
;Armazena resultado	/ Sim	/ Sim
;Executa novamente?	/ Não	/ Não

;Uma analogia:
;- Future: você pede para alguém começar uma tarefa agora e continua fazendo outras coisas.
;- Delay: você deixa uma tarefa preparada e só começa quando alguém solicitar.

;5. Um detalhe importante
;Assim como future, o delay pode bloquear quem solicita seu resultado enquanto a expressão ainda está sendo executada.
;(def processamento
;  (delay
;    (Thread/sleep 3000)
;    "Concluído"))
;
;@processamento
;A thread que executa @processamento aguardará o término dos três segundos.
;Resumo: Use future quando quiser iniciar um processamento assíncrono.
;        Use delay  quando quiser adiar um cálculo até que ele seja necessário e reutilizar o resultado posteriormente.
;O próximo tópico é Promises, que permitem separar quem produz um resultado de quem irá consumi-lo.