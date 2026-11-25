(ns advanced-topics.clojure-metaphysics.atoms)

;3. Atoms
;3.1. O que é um Atom?
;Um atom é uma referência que permite acompanhar um estado mutável de maneira controlada e realizar atualizações atômicas.
;Lembre-se: o valor armazenado continua sendo imutável. O que muda é o valor associado à referência.

;Imagine uma conta bancária:
(def conta
  (atom 1000))

;Criamos um Atom cujo estado inicial é 1000.
;Para consultar seu valor, usamos deref ou o atalho @:
@conta
;;; 1000
;O Atom representa a identidade da conta, enquanto 1000 é o valor atual associado a ela.

;3.2. Atualizando um Atom com swap!
;A principal função para atualizar um Atom é swap!.
;Sua estrutura é:
;(swap! referencia funcao argumentos...)

;Ela recebe:
;- A referência que queremos atualizar.
;- Uma função que calcula o novo valor.
;- Argumentos adicionais, se necessários.

;Exemplo:
(def conta (atom 1000))
;
(swap! conta + 200)
@conta
;;; 1200

;O que aconteceu?
;1. O Clojure leu o valor atual: 1000.
;2. Aplicou a função + com o argumento 200.
;3. Produziu um novo valor: 1200.
;4. Atualizou atomicamente o estado do Atom.
;Observe que não modificamos o número 1000. Produzimos outro valor e associamos esse valor ao Atom.

;Criando funções próprias
;Podemos utilizar funções que já conhecemos:
(defn depositar [saldo valor]
  (+ saldo valor))

(swap! conta depositar 500)

;Ou utilizar uma função anônima:
(swap! conta
       (fn [saldo]
         (+ saldo 300)))

;Isso é muito interessante porque podemos encapsular nossas regras de negócio em funções puras.


;3.3. Atualizando diretamente com reset!
;Existe outra maneira de atualizar um Atom: reset!.
(def conta (atom 1000))
(reset! conta 1500)
@conta
;;; 1500

;A diferença é importante:
;- swap!: calcula o novo valor utilizando o valor atual.
;- reset!: substitui diretamente o valor atual pelo valor informado.
;Veja a comparação:
;Operação	Exemplo	Resultado
;swap!
(swap! conta + 200)	1200
;reset!
(reset! conta 1500)	1500

;Quando utilizar?
;Use swap! quando o novo estado depender do estado atual.
;Use reset! quando você já conhece o valor completo que deseja armazenar.

;3.4. Por que Atom é seguro para concorrência?
;Imagine que duas threads tentem depositar dinheiro simultaneamente:
(def saldo (atom 1000))

(future
  (swap! saldo + 200))

(future
  (swap! saldo + 300))

;Se ambas as operações forem concluídas, o saldo será:
;@saldo
;;; 1500
;O swap! garante que as atualizações sejam atômicas.

;Isso significa que uma atualização não simplesmente sobrescreve a outra por causa de uma leitura e escrita concorrentes.
;Internamente, o Clojure pode tentar executar novamente a função de atualização caso detecte um conflito.
;Duas atualizações concorrentes
;Thread A
;+ R$ 200

;Thread B
;+ R$ 300

;Atom
;R$ 1.500
;Estado final após as duas atualizações

;Atenção: uma particularidade importante do swap!
;A função passada para swap! pode ser executada mais de uma vez.
;Por isso, ela deve ser pura, sem efeitos colaterais.

;Por exemplo, evite:
;(swap! saldo
;       (fn [valor]
;         (println "Atualizando saldo...")
;         (+ valor 100)))
;
;O println pode ser executado várias vezes, mesmo que a atualização seja efetivada apenas uma vez.
;Prefira separar o cálculo da atualização de qualquer efeito colateral.

;3.5. Atom versus Ref
;No capítulo anterior, estudamos os dois mecanismos. Vamos reforçar a diferença:
;Característica	/ Atom	/ Ref
;Atualização	/ Atômica	/ Transacional
;Escopo	/ Uma referência	/ Uma ou várias referências
;Função principal	/ swap!	/ alter
;Contexto	/ Atualizações independentes	/ Atualizações coordenadas
;Transação com dosyncm /	Não /	Sim

;Um exemplo:
;- Alterar o saldo de uma única conta: Atom pode ser adequado.
;- Transferir dinheiro entre duas contas, atualizando ambos os saldos de maneira coordenada: Ref pode ser mais adequado.

;3.6. Exercícios práticos
;Vamos fazer dois exercícios antes de avançar. Como de costume, tente resolver sozinho e me envie seu código para revisão.

;Exercício 1 — Controle de estoque
;Crie um Atom chamado estoque com valor inicial 20.
;Implemente duas funções:
;1. adicionar-estoque: recebe uma quantidade e aumenta o estoque.
;2. vender-produto: recebe uma quantidade e diminui o estoque.
;Por enquanto, não precisa validar estoque negativo.

(def estoque (atom 20))
(defn adicionar-estoque
  [saldo adicionar]
  (+ saldo adicionar))

(defn vender-produto
  [saldo adicionar]
  (- saldo adicionar))

(swap! estoque adicionar-estoque 10)

(swap! estoque vender-produto 10)



;Exercício 2 — Controle de saldo
;Crie um Atom chamado saldo com valor inicial 1000.
;Implemente:
;1. depositar: recebe um valor e atualiza o saldo.
;2. sacar: recebe um valor e atualiza o saldo.
;3. consultar-saldo: retorna o saldo atual.
;Neste exercício, tente utilizar swap! nas operações de depósito e saque.

(def saldo (atom 1000))

(defn depositar [saldo valor]
  (+ saldo valor))

(defn consultar-saldo
  [saldo]
  saldo)

(consultar-saldo @saldo)