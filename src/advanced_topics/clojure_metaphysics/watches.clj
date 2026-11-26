(ns advanced-topics.clojure-metaphysics.watches)

;4.1. Watches
;Um Watch é um mecanismo que permite observar alterações em uma referência.
;Imagine nosso controle de estoque. Sempre que o estoque mudar, queremos registrar essa alteração no console.

;1. Criando um Atom
(def estoque (atom 20))

;2. Registrando um Watch
;Utilizamos add-watch:
(add-watch estoque :monitor-estoque
           (fn [chave referencia valor-antigo valor-novo]
             (println "Estoque atualizado!")
             (println "Valor anterior:" valor-antigo)
             (println "Novo valor:" valor-novo)))

;Vamos entender os quatro argumentos recebidos pela função:
;Argumento /	Significado
;chave	/ Identificador do Watch
;referencia	/ Atom que está sendo observado
;valor-antigo	/ Estado anterior à atualização
;valor-novo	/ Novo estado

;A chave :monitor-estoque identifica esse observador.

;3. Testando o Watch
;Agora vamos atualizar nosso estoque:
(swap! estoque + 10)

;Saída esperada:
;Estoque atualizado!
;Valor anterior: 20
;Novo valor: 30

;O interessante é que não precisamos chamar manualmente a função do Watch. Ela é acionada quando o Atom sofre uma alteração.

;4. Podemos ter vários Watches?
;Sim! Podemos registrar mais de um observador na mesma referência.

;Por exemplo:
(add-watch estoque :auditoria
           (fn [chave referencia antigo novo]
             (println "Auditoria:" antigo "->" novo)))

;Agora temos dois observadores:
;- :monitor-estoque
;- :auditoria
;Ambos serão notificados das atualizações.

;5. Removendo um Watch
;Para remover um observador, utilizamos remove-watch:
(remove-watch estoque :monitor-estoque)

;A partir daí, esse Watch não receberá novas notificações.

;4.2. Cuidados importantes com Watches
;Existem alguns detalhes que você precisa conhecer:
;1. Watches são executados depois que a alteração do estado acontece.
;2. A função de observação não faz parte da transação ou atualização atômica.
;3. Um Watch não impede que um estado inválido seja armazenado.
;4. Se a função do Watch lançar uma exceção, isso não desfaz a alteração já realizada.
;Isso nos leva ao próximo mecanismo: Validators.

;4.3. Exercício rápido
;Antes de avançarmos para Validators, quero que você implemente um exemplo simples.
;Crie um Atom:
(def temperatura (atom 25))

(add-watch temperatura :monitor-temperatura
           (fn [chave referencia valor-antigo valor-novo]
             (println "Temperatura alterada!")
             (println "Temperatura anterior:" valor-antigo)
             (println "Nova temperatura:" valor-novo)))

;Registre um Watch chamado :monitor-temperatura que imprima:
;- "Temperatura alterada!"
;- Temperatura anterior.
;- Nova temperatura.

;Depois execute:
(swap! temperatura + 5)
(swap! temperatura - 3)

;O objetivo é praticar o registro de um Watch e entender os quatro argumentos recebidos pela função.
