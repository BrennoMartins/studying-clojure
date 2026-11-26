(ns advanced-topics.clojure-metaphysics.vars.per-thread-binding)

;1. Cada thread pode ter seu próprio binding
;Vamos reutilizar nosso exemplo de desconto:
(def ^:dynamic *desconto* 0.10)

;Imagine duas operações acontecendo simultaneamente:
;- Cliente A recebe 10% de desconto.
;- Cliente B recebe 30% de desconto.
;Podemos fazer isso:
(def cliente-a
  (future
    (binding [*desconto* 0.10]
      (* 100 (- 1 *desconto*)))))

(def cliente-b
  (future
    (binding [*desconto* 0.30]
      (* 100 (- 1 *desconto*)))))

;Consultando os resultados:
;@cliente-a
;;; 90.0

;@cliente-b
;;; 70.0
;Cada execução utiliza seu próprio binding dinâmico.

;2. O que está acontecendo?
;Quando o binding termina, o contexto temporário deixa de valer e o valor raiz continua sendo 10%.

;3. Uma particularidade importante do Clojure
;O Clojure possui um mecanismo chamado binding conveyance.
;Algumas operações concorrentes, como future, transmitem os bindings dinâmicos do contexto em que foram criadas.

;Por exemplo:
(binding [*desconto* 0.25]
  (let [resultado
        (future
          *desconto*)]
    @resultado))
;; 0.25
;O future recebe o binding dinâmico de 25%.
;Isso não significa que qualquer thread criada manualmente herdará esses valores.
;A transmissão depende do mecanismo utilizado para criar ou executar o trabalho.

;4. Exercício prático
;Vamos testar esse comportamento sem precisar criar threads manualmente.
;Considere:
(def ^:dynamic *taxa* 0.05)

;Implemente três situações:
;1. Crie um future que retorne o valor de *taxa*, sem utilizar binding.
@(future *taxa*)
;2. Crie um future dentro de um binding que altere temporariamente *taxa* para 0.15.
@(binding [*taxa* 0.15]
  (future *taxa*))
;3. Consulte *taxa* novamente fora do binding.
(println *taxa*)
;Resultados esperados:
;Situação	Resultado
;Future sem binding temporário	0.05
;Future criado dentro do binding	0.15
;Var fora do binding	0.05
;O objetivo é perceber a diferença entre o valor raiz e o binding transmitido para uma execução concorrente. Tente implementar e depois revisamos.