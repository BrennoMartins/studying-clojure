(ns content.clojure-alchemy-reading-evaluation-macros.reader.var)

;Em Clojure, uma Var é uma referência nomeada que associa um símbolo a um valor dentro de um namespace.
;Ela funciona como uma espécie de referência dinâmica para um valor. Vamos por partes, porque essa distinção é importante.

;1. Criando uma Var com def
;Quando fazemos:
;(def preco 100)
;O Clojure cria uma Var chamada preco no namespace atual e associa o valor 100 a ela.

;Podemos visualizar assim:
;Símbolo  = preco
;É resolvido para uma Var

;Var  = #'user/preco
;Valor associado: 100


;2. Símbolo, Var e valor são coisas diferentes
;Considere:
(def preco 100)

;Agora observe:
preco
;;; 100

'preco
;;; preco
;
#'preco
;;; #'user/preco

;Cada expressão representa algo diferente:
;Expressão	/ O que retorna
;preco	/ O valor associado à Var
;'preco	/ O símbolo preco
;#'preco /	A própria Var

;O #' é uma Reader Macro que equivale a (var preco).

;3. Uma Var pode receber outro valor
;Imagine:
;(def preco 100)
;
;(defn calcular-desconto []
;  (* preco 0.9))
;
;
;Agora:
;(calcular-desconto)
;;; 90.0
;
;
;Redefinimos:
;(def preco 200)
;
;
;E executamos novamente:
;(calcular-desconto)
;;; 180.0
;
;
;A função consulta a Var preco quando é executada.
;
;Isso acontece porque a Var funciona como uma referência para o valor associado, que pode ser redefinido.
;4. Comparação com Java
;Uma analogia aproximada seria:
;static int preco = 100;
;
;
;Ambos permitem acessar um valor por meio de um nome e alterar o valor associado.
;
;Mas não são equivalentes: Vars possuem características próprias, como metadados, resolução por namespace e bindings dinâmicos.
;5. Um detalhe que veremos mais adiante
;Clojure possui uma função chamada var-get:
;(def preco 100)
;
;(var-get #'preco)
;;; 100
;
;
;Ela recebe uma Var e retorna o valor associado a ela.


(def saldo 500)

(def referencia #'saldo)

(- saldo (var-get referencia))