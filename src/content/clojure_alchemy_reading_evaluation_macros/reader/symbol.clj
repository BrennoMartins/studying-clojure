(ns content.clojure-alchemy-reading-evaluation-macros.reader.symbol)

;Em Clojure, a melhor definição é:
;Símbolo (Symbol) é uma estrutura de dados que representa um identificador ou nome que pode ser resolvido para uma Var, função, classe ou outro elemento dentro de um contexto.
;
;Vamos entender isso com exemplos.
;1. Exemplos de símbolos
;nome
;calcular-total
;+
;minha-funcao
;
;
;Todos são símbolos.
;
;Observe:
(type 'nome)
;;; clojure.lang.Symbol
;
;
;O apóstrofo impede que nome seja avaliado. Assim, conseguimos observar o símbolo em si.

;2. Símbolo não é o mesmo que valor
;Considere:
(def idade 35)

;Temos uma Var chamada idade, associada ao valor 35.

;Agora:
;idade
;;; 35

;O Clojure resolve o símbolo idade e encontra o valor associado à Var.

;Mas:
;'idade
;;; idade
;

;Aqui temos o símbolo como dado, sem resolução para seu valor.
;Visualizando a diferença
;Símbolo
;idade

;Quando avaliado, é resolvido no namespace
;Var associada
;35


;3. Comparação com outros tipos
;Tipo	/ Exemplo	/ Representa
;Symbol	/ 'idade	/ Identificador
;Keyword	/ :idade	/ Valor identificador, frequentemente usado como chave
;String	/ "idade"	/ Texto
;Number	/ 35 / Número

;Uma diferença importante: keywords normalmente representam valores que são utilizados diretamente; símbolos frequentemente são nomes que precisam ser resolvidos durante a avaliação.
;4. Por que isso é importante para macros?
;Porque macros trabalham justamente com estruturas que representam código.
;
;Imagine:
;'(+ idade 10)
;
;
;Essa lista contém três elementos:
;- +: símbolo que representa uma operação.
;- idade: símbolo que representa um identificador.
;- 10: número.
;A macro pode receber essa estrutura, inspecionar seus elementos e construir outra expressão.
;
;É por isso que entender símbolos é fundamental para compreender macros.