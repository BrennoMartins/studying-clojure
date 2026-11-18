(ns advanced-topics.concurrent-and-parallel-programming.clojure-implementation-JVM-threads.futures-delays-and-promises.future)

;Um future permite executar uma tarefa de forma assíncrona, em outra thread, enquanto o programa continua executando o restante do código.

;Em Clojure, usamos future:
(def resultado-dois
(future
(Thread/sleep 5000)
"Processamento concluído"))

;Quando esse código é executado:
;1. O Clojure inicia a tarefa em uma thread separada.
;2. O código principal não precisa esperar os três segundos.
;3. O resultado fica disponível quando a tarefa termina.
;Para obter o resultado, usamos deref (@):
@resultado-dois
;;; "Processamento concluído"
;Atenção: se a tarefa ainda estiver executando, o deref bloqueia a thread atual até que o resultado esteja disponível.

;2. Verificando se terminou
;Podemos verificar se um future já terminou usando realized?:
(realized? resultado-dois)
;;; true ou false

;Exemplo:
(def tarefa
(future
(Thread/sleep 5000)
42))
;
(realized? tarefa)
;;; provavelmente false
;
@tarefa
;;; aguarda e retorna 42
;
(realized? tarefa)
;;; true


;3. Definindo um tempo máximo de espera
;Nem sempre queremos bloquear indefinidamente. Podemos usar deref com timeout:
(deref tarefa 1000 :timeout)

;Nesse exemplo:
;- 1000: tempo máximo de espera em milissegundos.
;- :timeout: valor retornado caso a tarefa não termine nesse período.
;Se a tarefa terminar dentro do prazo, recebemos o resultado. Caso contrário, recebemos :timeout.
;Importante: o timeout não cancela a tarefa. Ela continua executando.

;4. Exemplo prático
;Imagine que estamos desenvolvendo um sistema financeiro e precisamos consultar três serviços externos:
(defn buscar-cotacao []
(Thread/sleep 2000)
{:moeda "USD" :valor 5.20})

(defn buscar-selic []
(Thread/sleep 3000)
{:selic 15.0})

(defn buscar-inflacao []
(Thread/sleep 1500)
{:inflacao 4.5})

;Executando sequencialmente:
(def cotacao (buscar-cotacao))
(def selic (buscar-selic))
(def inflacao (buscar-inflacao))

;O tempo total será aproximadamente 6,5 segundos.
;Agora, usando future:
(def cotacao (future (buscar-cotacao)))
(def selic (future (buscar-selic)))
(def inflacao (future (buscar-inflacao)))

;As três tarefas podem ser executadas concorrentemente. Depois, podemos obter os resultados:
{:cotacao @cotacao
:selic @selic
:inflacao @inflacao}
;O tempo total esperado fica próximo da tarefa mais demorada: aproximadamente 3 segundos, desconsiderando sobrecarga e escalonamento.

;A comparação não é perfeita: future em Clojure também gerencia a execução assíncrona, enquanto em Java normalmente separamos a criação da tarefa do executor que a executa.
;Ideia central: future é útil quando queremos iniciar um processamento sem bloquear imediatamente o fluxo principal e recuperar seu resultado posteriormente.