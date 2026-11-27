(ns advanced-topics.mastering-concurrent-processes-core-async.getting-started-with-processes
  (:require [clojure.core.async :as async]))

;1. Getting Started with Processes
;1.1 O problema da concorrência tradicional
;Imagine que temos uma aplicação que precisa executar três tarefas:
;1. Consultar o estoque.
;2. Consultar o preço de um produto.
;3. Calcular o preço final.
;Uma abordagem tradicional poderia executar essas operações em sequência:
;(defn calcular-preco-final []
;  (let [estoque (consultar-estoque)
;        preco (consultar-preco)]
;    (if (pos? estoque)
;      (* preco 0.9)
;      0)))
;Dependendo do tempo de resposta das consultas, podemos acabar esperando desnecessariamente.
;Nos capítulos anteriores, vimos como usar future, agent e threads para executar tarefas concorrentemente.
;O core.async oferece outra abordagem: em vez de organizar tudo em torno de threads e referências compartilhadas,
; podemos organizar o sistema em processos que trocam mensagens por canais.

;1.2 O que é um processo no core.async?
;Um processo é uma unidade de execução que realiza uma sequência de operações e pode se comunicar com outros processos.

;Imagine:
;Processo A - Consulta estoque
;Canal
;Transporta mensagens

;Processo B - Calcula resultado
;Os processos não precisam compartilhar diretamente o estado interno uns dos outros. Eles podem trocar informações por canais.

;1.3 Conhecendo os canais
;Um canal é criado com chan.
;(def canal (async/chan))
;;;
;;;;Agora temos um canal vazio.
;;;;Podemos enviar uma mensagem:
;(async/>!! canal "Olá, Clojure!")
;;
;;;E receber essa mensagem:
;(async/<!! canal)
;;;; "Olá, Clojure!"

;Observe os operadores:
;Operação	Significado
;chan Cria um canal
;>!!	Envia uma mensagem de forma bloqueante
;<!!	Recebe uma mensagem de forma bloqueante

;Por enquanto, estamos utilizando operações bloqueantes. Mais adiante, veremos a diferença entre bloquear e estacionar (parking).

;1.4 Criando um processo com go
;Agora vem uma parte importante.
;O go cria um processo assíncrono que pode utilizar operações de canais sem bloquear uma thread enquanto aguarda essas operações.

;Exemplo:
;(def canal (async/chan))

;(async/go
;  (async/>! canal "Mensagem enviada pelo processo"))

;(async/<!! canal)
;;; "Mensagem enviada pelo processo"

;Aqui temos três elementos:
;1. async/go: inicia o processo.
;2. async/>!: envia uma mensagem pelo canal dentro do processo.
;3. async/<!!: recebe a mensagem de forma bloqueante no código externo.
;Atenção à diferença entre os operadores:
;- >!! e <!!: operações bloqueantes, normalmente usadas fora de blocos go.
;- >! e <!:  operações de canais que estacionam o processo e são utilizadas dentro de blocos go.
;Não são operadores intercambiáveis.


;1.5 go não significa criar uma nova thread
;Essa distinção é fundamental.

;Quando utilizamos:
;(async/go
;  (println "Executando processo"))

;Não estamos necessariamente criando uma nova thread dedicada.
;O go utiliza um mecanismo de agendamento para executar processos em um conjunto de threads compartilhadas.
;Isso permite ter muitos processos concorrentes sem precisar criar uma thread para cada um.

;Pense assim:
;Recurso	/ Modelo
;Thread	/ Thread dedicada
;future	/ Tarefa assíncrona executada em uma thread gerenciada
;go	/ Processo cooperativo agendado pelo core.async