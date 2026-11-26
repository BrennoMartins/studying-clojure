(ns advanced-topics.clojure-metaphysics.validator)

;1. O que é um Validator?
;Imagine nosso controle de estoque:
(def estoque (atom 20))

;Atualmente, podemos fazer:
(swap! estoque - 30)

;@estoque
;;; -10

;Isso pode representar um problema para nossa aplicação: não queremos permitir estoque negativo.
;Podemos resolver isso utilizando um Validator.

;2. Criando um Validator
;A função utilizada é set-validator!.

;Veja:
(set-validator! estoque
                (fn [novo-valor]
                  (>= novo-valor 0)))

;Vamos entender:
;- estoque: Atom que queremos proteger.
;- fn: função responsável pela validação.
;- novo-valor: valor que está sendo proposto para o Atom.
;- >= novo-valor 0: verifica se o valor é maior ou igual a zero.
;O Validator precisa retornar um valor verdadeiro para permitir a atualização.

;3. Testando o Validator
;Com o Validator configurado:
(swap! estoque - 5)

;@estoque
;;; 15

;A atualização é aceita porque o resultado é válido.
;Agora tente:
;(swap! estoque - 30)
;O resultado seria -15, mas o Validator impede a atualização e lança uma exceção.
;O estoque continua em 15.

;4. Validators também funcionam com reset!
;O Validator não protege apenas operações feitas com swap!.
;Ele também é aplicado quando utilizamos reset!.
(reset! estoque 50)
;;; Permitido
;
(reset! estoque -10)
;;; Exceção
;Isso acontece porque ambos tentam estabelecer um novo estado para o Atom.

;5. Watch vs. Validator
;Essa diferença é muito importante:
;Característica	/ Watch	/ Validator
;Finalidade	/ Observar alterações	/ Validar novos estados
;Quando atua?	/ Após a alteração	/ Antes de aceitar a alteração
;Pode impedir atualização?	/ Não	/ Sim
;Função principal	/ add-watch	/ set-validator!
;Recebe	Chave, referência, valor antigo e novo	Novo valor proposto
;Uma aplicação pode utilizar os dois mecanismos simultaneamente.

;Por exemplo, em um sistema de estoque:
;- Validator impede que o estoque fique negativo.
;- Watch registra que o estoque foi alterado.
;Atenção: Validators devem ser funções puras. Assim como funções utilizadas em swap!, não devemos colocar efeitos colaterais dentro deles.

;6. Exercícios práticos
;Vamos fazer dois exercícios para consolidar.
;Exercício 1 — Validação de estoque
;Crie um Atom:
(def estoque-exercicio (atom 50))

;Configure um Validator que permita apenas valores maiores ou iguais a zero.

(set-validator! estoque-exercicio
                (fn [valor]
                  (>= valor 0)))

(swap! estoque-exercicio + -50)
;Teste:
;- Adicionar 10 unidades.
;- Remover 20 unidades.
;- Tentar remover 50 unidades.
;Observe o que acontece com o estado após a última operação.

;Exercício 2 — Validação de saldo bancário
;Crie um Atom chamado saldo com valor inicial 1000.
(def saldo (atom 1000))

(set-validator! saldo
                (fn [valor]
                  (>= valor 0)))

(defn depositar [saldo valor]
  (+ saldo valor))

(defn sacar [saldo valor]
  (- saldo valor))

(defn consultar-saldo [saldo]
  saldo)

;; Depósito de R$ 500
(swap! saldo depositar 500)

;; Saque de R$ 300
(swap! saldo sacar 300)

;; Tentar sacar R$ 1.500
(swap! saldo sacar 1500)

;Configure um Validator que impeça saldo negativo.
;Depois:
;1. Deposite R$ 500 utilizando swap!.
;2. Faça um saque de R$ 300.
;3. Tente sacar R$ 1.500.
;4. Consulte o saldo final.
;O resultado esperado, caso a última operação seja rejeitada, é R$ 1.200.
