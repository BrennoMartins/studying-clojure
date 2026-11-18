(ns advanced-topics.concurrent-and-parallel-programming.clojure-implementation-JVM-threads.the-three-goblins.mutual-exclusion)

;Imagine que temos um estoque compartilhado:
(def estoque (atom 10))


;Duas Threads tentam vender o último produto disponível.
;Uma sequência possível:
;1. Thread A consulta o estoque: 1 unidade.
;2. Thread B consulta o estoque: 1 unidade.
;3. Thread A decide vender.
;4. Thread B também decide vender.
;5. As duas executam a venda.
;O problema é que ambas tomaram a decisão com base no mesmo estado anterior.

;Isso é conhecido como race condition (condição de corrida).

;1. O que é exclusão mútua?
;Exclusão mútua significa permitir que apenas uma Thread por vez execute determinada região crítica.
;Imagine uma sala com uma única chave:
;- Thread A pega a chave e entra.
;- Thread B precisa aguardar.
;- Thread A termina e libera a chave.
;- Thread B entra.
;Região crítica protegida por lock
;Thread A - Executando
;Consulta e atualiza o estoque

;Thread B - Aguardando
;Espera a liberação do lock

;2. Como fazer isso no Clojure?
;O Clojure oferece a macro locking, que utiliza o mecanismo de monitor da JVM.

;Exemplo:
(def estoque (atom 10))
(def estoque-lock (Object.))

(defn vender-produto []
  (locking estoque-lock
    (if (pos? @estoque)
      (do
        (swap! estoque dec)
        :venda-realizada)
      :estoque-indisponivel)))

;Vamos entender:
;- Object. cria um objeto Java que será usado como monitor.
;- locking adquire o lock associado ao objeto.
;- O código dentro do locking só é executado por uma Thread por vez para aquele mesmo monitor.
;- Quando a execução termina, o lock é liberado automaticamente, inclusive se ocorrer uma exceção.
;Atenção: todas as Threads precisam utilizar o mesmo objeto de lock. Se cada Thread criar seu próprio objeto, não haverá proteção compartilhada.

;4. Lock versus STM (dosync)
;Agora temos dois mecanismos diferentes que resolvem problemas de concorrência.
;Característica	/ locking	/ dosync
;Mecanismo	/ Exclusão mútua	/ Memória transacional
;Bloqueia outras Threads no mesmo lock	/ Sim	/ Não funciona como um lock convencional
;Pode repetir o corpo	/ Não por conflito STM	/ Sim
;Trabalha com	/ Objetos monitor	/ Refs
;Efeitos colaterais dentro do bloco	/ Possíveis, com cuidado	/ Devem ser evitados
;
;Uma diferença importante: locking protege uma região de código, mas não torna automaticamente todas as operações da aplicação seguras. As Threads precisam respeitar o mesmo protocolo de lock.

;5. Quando utilizar?
;Imagine o Aroma Sabor OS, com várias operações acontecendo simultaneamente no caixa.
;Você poderia utilizar exclusão mútua para proteger uma operação que precisa ser executada exclusivamente por uma Thread.

;Mas, antes de escolher a ferramenta, vale considerar:
;- O estado é compartilhado entre várias operações?
;- Precisamos alterar várias referências atomicamente?
;- Queremos apenas proteger uma região crítica?
;- Podemos resolver o problema usando uma referência atômica ou uma transação STM?
;Não devemos usar locks automaticamente para qualquer problema de concorrência.