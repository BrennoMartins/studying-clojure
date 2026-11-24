(ns advanced-topics.clojure-metaphysics.clojure-metaphysics)

;2. Clojure Metaphysics
;2.1. Três conceitos fundamentais
;Antes de entrar nos Atoms, precisamos entender três conceitos.

;1. Valor (Value)
;É o dado propriamente dito.
(def saldo 1000)
;O número 1000 é um valor. Ele não muda depois de criado.

;Se precisarmos representar um saldo de 1200, teremos outro valor:
(def novo-saldo 1200)

;2. Identidade (Identity)
;É aquilo que reconhecemos como sendo a mesma entidade ao longo do tempo.
;Imagine uma conta bancária. Mesmo que seu saldo mude de 1000 para 1200, continuamos falando da mesma conta.


;3. Estado (State)
;É o valor associado a uma identidade em determinado momento.
;Podemos representar a evolução de uma conta assim:
;Identidade: Conta bancária
;1. Momento inicial
;R$ 1.000
;Estado inicial
;2. Após um depósito
;R$ 1.200
;Novo estado
;3. Após um saque
;R$ 900
;Estado posterior
;A identidade permanece; os valores associados a ela mudam.


;2.2. Como isso funciona em Clojure?
;Até agora trabalhamos diretamente com valores:
(def conta
  {:titular "Brenno"
   :saldo 1000})

;Podemos criar uma nova versão:
(def conta-atualizada
  (update conta :saldo + 200))

;Observe:
(:saldo conta)
;;; 1000

(:saldo conta-atualizada)
;;; 1200

;Temos dois valores distintos.
;Mas e se quisermos representar uma conta cuja identidade permanece a mesma enquanto seu saldo evolui?

;É aqui que entram as referências.
;Uma referência permite acompanhar uma identidade cujo estado pode ser atualizado, mantendo os valores associados a ela imutáveis.

;2.3. Referências e valores
;Imagine a seguinte estrutura conceitual:
;Referência: conta
;Valor atual
;R$ 1.200

;Após uma atualização, a mesma referência passa a apontar para outro valor imutável.
;Essa separação é fundamental porque podemos controlar como as atualizações acontecem.

;Por exemplo:
;- Uma atualização atômica com Atom.
;- Uma atualização transacional com Ref.
;- Uma associação dinâmica ou alteração de raiz com Var.
;Cada mecanismo possui regras próprias.

;2.4. Por que isso é importante para concorrência?
;Imagine duas operações simultâneas sobre uma conta:
;- Thread A deposita R$ 200.
;- Thread B deposita R$ 300.
;Se trabalhássemos diretamente com estado mutável compartilhado, precisaríamos coordenar cuidadosamente as operações para evitar perder atualizações.
;Com as referências do Clojure, podemos escolher mecanismos que controlam como os novos valores são associados à identidade.
;Isso não significa que toda operação seja automaticamente segura. Precisamos escolher o mecanismo adequado.
;É justamente o que estudaremos nas próximas seções.

;2.5. Comparação com Java
;Uma analogia útil:
;Conceito	Java	Clojure
;Valor imutável	BigDecimal, String	Números, strings, mapas etc.
;Identidade	Objeto acompanhado por uma referência	Identidade gerenciada por uma referência
;Estado	Campos atuais do objeto	Valor associado à identidade
;Atualização	Pode modificar campos	Associa um novo valor à referência

;Essa comparação é conceitual: uma referência do Clojure não é simplesmente equivalente a uma variável Java.

;2.6. O que você precisa dominar?
;Antes de avançar, guarde estas três ideias:
;1. Valor: dado imutável.
;2. Identidade: entidade que acompanhamos ao longo do tempo.
;3. Estado: valor associado à identidade em determinado momento.
;A grande mudança de pensamento é deixar de imaginar que estamos modificando um objeto
; e começar a pensar que estamos associando novos valores a uma identidade.