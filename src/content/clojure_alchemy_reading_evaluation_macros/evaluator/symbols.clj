(ns content.clojure-alchemy-reading-evaluation-macros.evaluator.symbols)

;1. Como o Evaluator resolve um símbolo?
;Lembra que o Reader transforma o texto idade em um símbolo? O Evaluator recebe esse símbolo e precisa descobrir a que ele se refere.

;Considere:
(def idade 35)

;Quando essa expressão é avaliada, o Clojure:
;1. Cria uma Var chamada idade no namespace atual.
;2. Associa o valor 35 a essa Var.
;Depois, quando você escreve:
idade

;O Reader transforma idade em um símbolo. O Evaluator procura esse símbolo no namespace atual, encontra a Var correspondente e retorna seu valor: 35.
;É como se o símbolo fosse o nome que permite localizar uma referência.

;2. Símbolo, valor e Var são coisas diferentes
;Veja a diferença:
(def idade 35)

idade
'idade
#'idade

;Expressão	Resultado	O que representa
;idade	35	O valor associado à Var
;'idade	idade	O símbolo, sem avaliá-lo
;#'idade	#'user/idade	A própria Var

;O detalhe importante é que #'idade não retorna o valor 35. Ele retorna a referência Var.
;Você pode acessar o valor dessa Var usando:
(var-get #'idade)
;;; 35

;3. E se o símbolo não existir?
;Se você escrever:
;altura
;E não existir uma Var chamada altura no namespace atual, o Evaluator não conseguirá resolver o símbolo e lançará um erro, normalmente Unable to resolve symbol.

;Isso é diferente de:
'altura
;Aqui não há erro, porque o símbolo está sendo tratado como dado. O Clojure não precisa encontrar uma Var chamada altura.

;A ideia central: avaliar um símbolo significa tentar resolver seu nome no contexto atual. Colocá-lo entre aspas impede essa resolução e preserva o símbolo como dado.
;Na próxima parte, vamos aprofundar como o Evaluator trata listas: chamadas de funções, argumentos e special forms.