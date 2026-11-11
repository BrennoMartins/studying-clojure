(ns content.clojure-alchemy-reading-evaluation-macros.evaluator.self-evaluating-forms)

;O que significa Self-Evaluating?
;São expressões cujo próprio valor é o resultado da avaliação.
;Em outras palavras, o Clojure não precisa procurar uma função ou resolver um identificador para determinar seu valor.

;Veja:
;42
;Resultado: 42.
;"Olá mundo"
;Resultado: "Olá mundo".
;:preco
;Resultado: :preco.

;Esses valores são chamados de self-evaluating forms.

;2. Quais estruturas são Self-Evaluating?
;Alguns exemplos:
;Expressão	Resultado
;10	10
;10.5	10.5
;"Clojure"	"Clojure"
;:nome	:nome
;true	true
;false	false
;nil	nil
;[1 2 3]	[1 2 3]
;{:nome "Ana"}	{:nome "Ana"}

;Uma observação: vetores e mapas são estruturas autoavaliáveis, mas seus elementos são avaliados durante a construção da estrutura.
;Por exemplo:
(def idade 35)
[idade 10]
;Resultado:
;[35 10]
;O vetor é produzido, mas o símbolo idade é resolvido durante sua construção.


;3. Comparando com Symbols
;Agora observe:
(def idade 35)
idade
;Resultado: 35.

;Mas idade não é uma expressão autoavaliável.
;Por quê?
;Porque o Evaluator precisa resolver o símbolo idade no namespace atual e encontrar sua Var.

;Compare:
;Número
;35
;O próprio valor é o resultado.
;
;Símbolo
;idade
;O Evaluator procura a definição correspondente.
;35

;Essa diferença é muito importante para entender a próxima parte.

;4. Um detalhe interessante: listas
;Considere:
'(1 2 3)
;Isso retorna uma lista como dado, por causa do quote.

;Mas se você escrever:
;(1 2 3)
;Execution error (ClassCastException) at content.clojure-alchemy-reading-evaluation-macros.evaluator.self-evaluating-forms/eval2871 (self_evaluating_forms.clj:67).
;class java.lang.Long cannot be cast to class clojure.lang.IFn (java.lang.Long is in module java.base of loader 'bootstrap'; clojure.lang.IFn is in unnamed module of loader 'app')

;O Clojure tentará interpretar o primeiro elemento como uma função e executar uma chamada.
;Como 1 não é uma função invocável, ocorrerá um erro.
;Isso acontece porque listas não são automaticamente autoavaliáveis:
; normalmente, o Evaluator interpreta uma lista como uma chamada de função ou outra construção especial.

;5. Vamos consolidar
;Temos três categorias que já conseguimos diferenciar:
;Categoria	/ Exemplo	/ Comportamento
;Self-evaluating	/ 100	/ Retorna o próprio valor
;Symbol	/ idade	/ Resolve o identificador
;List	/ (+ 10 20)	/ Interpreta a expressão

;E uma lista protegida por quote:
;'(+ 10 20)
;É preservada como dado.