(ns advanced-topics.concurrent-and-parallel-programming.clojure-implementation-JVM-threads.whats-a-thread)

;Essa seção é importante porque, antes de conhecer future, delay e promise, precisamos entender onde o Clojure executa suas operações.

;1. O que é uma Thread?
;Uma Thread é uma unidade de execução dentro de um processo.
;Quando você inicia uma aplicação Clojure na JVM, normalmente existe uma Thread principal (main thread) responsável por executar o código inicial.

;Por exemplo:
;(println "Início")
;(println "Processando...")
;(println "Fim")

;As três instruções são executadas pela mesma Thread, sequencialmente.
;Podemos visualizar assim:
;Main Thread
;1. Executa println "Início"
;2. Executa println "Processando..."
;3. Executa println "Fim"


;2. Uma aplicação pode ter várias Threads
;A JVM permite criar várias Threads dentro do mesmo processo.

;Imagine uma aplicação que precisa:
;- Consultar uma API de cotação.
;- Consultar outra API.
;- Processar um relatório.

;Podemos distribuir essas operações entre diferentes Threads.
;Um processo com três Threads

;Main Thread
;Controla a aplicação

;Thread 1
;Consulta cotação do dólar

;Thread 2
;Consulta cotação do euro
;Essas Threads podem progredir independentemente. Dependendo do ambiente e da disponibilidade de núcleos, podem inclusive executar simultaneamente.

;3. Criando uma Thread no Clojure
;Como Clojure roda na JVM, podemos utilizar diretamente as classes Java.

;Por exemplo:
(def minha-thread
  (Thread.
    (fn []
      (println "Executando em outra Thread"))))

;Aqui temos alguns elementos:
;- Thread.: cria uma instância da classe Java Thread.
;- fn: cria uma função que será executada.
;- minha-thread: guarda a referência para o objeto Thread.
;Mas atenção: criar uma Thread não significa iniciá-la.

;Para iniciar:
(.start minha-thread)
;O métodos .start solicita à JVM que inicie a execução em uma nova Thread.

;Para aguardar seu término:
(.join minha-thread)
;O join bloqueia a Thread que o chamou até que a Thread indicada termine
; .
;4. Comparando com Java
;Em Java, seria algo equivalente a:
;Thread minhaThread = new Thread(() -> {
;                                       System.out.println("Executando em outra Thread");
;                                       });
;minhaThread.start();
;minhaThread.join();

;A ideia é exatamente a mesma: Clojure não inventou um mecanismo separado de Threads. Ele utiliza a infraestrutura da JVM.

;5. Um detalhe importante: Thread não é tarefa
;Essa distinção será importante nas próximas seções.
;- Thread: recurso de execução.
;- Tarefa: trabalho que queremos executar.
;Uma Thread pode executar uma tarefa, várias tarefas sequencialmente ou permanecer aguardando alguma operação.

;Criar uma Thread para cada tarefa nem sempre é uma boa solução. Threads possuem custos de memória e gerenciamento.
;Por isso existem mecanismos como pools de Threads e abstrações de concorrência.