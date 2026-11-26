(ns advanced-topics.clojure-metaphysics.refs)

;1. Por que precisamos de Refs?
;Até aqui, vimos os Atoms. Eles são ótimos quando precisamos atualizar um estado de maneira independente.
;Mas imagine uma transferência bancária:
;- Conta A tem R$ 1.000.
;- Conta B tem R$ 500.
;- Transferimos R$ 200 da conta A para a conta B.
;O resultado esperado é:
;- Conta A: R$ 800.
;- Conta B: R$ 700.
;O problema é que temos duas referências que precisam ser atualizadas como uma única operação.

;Se usássemos dois Atoms:
;(def conta-a (atom 1000))
;(def conta-b (atom 500))
;
;(swap! conta-a - 200)
;(swap! conta-b + 200)
;Cada swap! é atômico individualmente, mas a sequência inteira não é.
;Imagine que a primeira atualização aconteça e a aplicação falhe antes da segunda. Teremos retirado R$ 200 da conta A sem depositá-los na conta B.
;É justamente esse tipo de problema que os Refs ajudam a resolver.

;2. O que é uma Ref?
;Uma Ref é uma referência gerenciada pelo mecanismo STM (Software Transactional Memory) do Clojure.
;Ela permite coordenar alterações em múltiplos estados dentro de uma transação.
;Os principais elementos são:
;Elemento	/ Responsabilidade
;ref	/ Cria uma referência transacional
;dosync	/ Inicia uma transação
;alter	/ Calcula e agenda uma alteração dentro da transação
;@	/ Consulta o valor atual da referência

;3. Primeira transferência usando Refs
(def conta-a (ref 1000))
(def conta-b (ref 500))

(dosync
  (alter conta-a - 200)
  (alter conta-b + 200))

;Agora as duas alterações fazem parte da mesma transação.
;Se a transação não puder ser concluída por causa de um conflito, o STM pode executá-la novamente.
; Se ocorrer uma falha, nenhuma das alterações da transação será confirmada.

;Para consultar os valores:
;@conta-a
;;; 800
;@conta-b
;;; 700

;Uma regra importante: não coloque efeitos colaterais dentro de dosync, pois o STM pode repetir a execução da transação.

;4. Comparando Atom e Ref
;Característica	/ Atom	/ Ref
;Atualização	/ swap!	/ alter
;Transação	/ Não	/ dosync
;Múltiplas referências coordenadas	/ Não	/ Sim
;Mecanismo	/ Atualização atômica individual	/ STM

;5. Exercício prático
;Vamos começar com um cenário semelhante ao livro, mas usando contas bancárias.

;Temos:
(def conta-a (ref 2000))
(def conta-b (ref 1000))

(defn transferir
  [conta-origem conta-destino valor]
  (dosync
    (when (and (>= @conta-origem valor)
               (pos? valor))
      (do
        (alter conta-origem - valor)
        (alter conta-destino + valor)))))

(transferir conta-a conta-b 500000)

@conta-a
@conta-b

;Implemente a função transferir que:
;1. Recebe duas referências e um valor.
;2. Retira o valor da primeira conta.
;3. Adiciona o valor à segunda conta.
;4. Executa as duas alterações dentro de uma única transação usando dosync.
;Exemplo esperado:
;(transferir conta-a conta-b 500)
;
;@conta-a
;;; 1500
;
;@conta-b
;;; 1500