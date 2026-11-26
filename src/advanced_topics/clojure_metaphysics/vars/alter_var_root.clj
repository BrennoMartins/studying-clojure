(ns advanced-topics.clojure-metaphysics.vars.alter-var-root)

;1. Relembrando o valor raiz
;Considere:
(def ^:dynamic *taxa* 0.05)
;O valor raiz dessa Var é 0.05.
;Podemos consultá-lo normalmente:
*taxa*
;;; 0.05

;E podemos criar um binding temporário:
(binding [*taxa* 0.15]
  *taxa*)
; 0.15

*taxa*
;;; 0.05

;O valor raiz não foi modificado.

;2. Conhecendo alter-var-root
;A função alter-var-root permite transformar o valor raiz de uma Var.
;Sua estrutura é:
;(alter-var-root #'nome-da-var funcao)

;Observe que usamos #' para passar a própria Var, e não apenas seu valor.

;Exemplo:
(def taxa 0.05)
(alter-var-root #'taxa + 0.10)
;taxa
;;; 0.15

;O que aconteceu?
;1. #'taxa referencia a Var.
;2. + 0.10 é a função aplicada ao valor raiz atual.
;3. O novo valor raiz passa a ser 0.15.
;Também podemos usar funções como inc, dec ou funções próprias.
(alter-var-root #'taxa inc)
;taxa
;;; 1.15

;3. Diferença entre binding e alter-var-root
;Característica	/ binding	/ alter-var-root
;Altera o valor raiz	/ Não	/ Sim
;É temporário	/ Sim	/ Não
;Escopo	/ Contexto dinâmico	/ Var
;Afeta outros contextos	/ Não diretamente	/ Sim, que consultem o novo valor raiz
;Um detalhe importante: se você executar alter-var-root dentro de um binding, ele altera o valor raiz,
; mas o binding temporário continua prevalecendo naquele contexto até terminar.

;4. Aplicação prática
;Imagine uma aplicação que permite atualizar sua taxa padrão de desconto.
(def ^:dynamic *desconto* 0.10)

;Podemos criar:
(defn atualizar-desconto [novo-desconto]
  (alter-var-root #'*desconto* (constantly novo-desconto)))

;A função constantly retorna uma função que sempre devolve o valor informado.

;Teste:
;(atualizar-desconto 0.20)

;*desconto*
;;; 0.20
;Diferentemente de binding, a alteração permanece depois da chamada.
;Atenção: alter-var-root modifica um valor global compartilhado.
;Em aplicações concorrentes, isso exige cuidado, pois outras execuções podem passar a observar o novo valor.

;5. Exercício prático
;Vamos praticar sem repetir exatamente o exemplo.

;Temos uma configuração de limite de compras:


;Implemente:
;1. atualizar-limite: recebe um novo limite e altera o valor raiz.
;2. aumentar-limite: recebe um percentual e aumenta o valor raiz de acordo com esse percentual.
;Teste os seguintes resultados:
(def ^:dynamic *limite-compra* 1000)

(defn atualizar-limite
  [novo-valor]
  (alter-var-root #'*limite-compra* (constantly novo-valor)))

(defn aumentar-limite
  [percentual]
  (let [novo-valor (+ *limite-compra* (* *limite-compra* percentual))]
    (alter-var-root #'*limite-compra* (constantly novo-valor))))

(atualizar-limite 2000)
;*limite-compra*
;;; 2000

(aumentar-limite 0.10)
;*limite-compra*
;;; 2200
