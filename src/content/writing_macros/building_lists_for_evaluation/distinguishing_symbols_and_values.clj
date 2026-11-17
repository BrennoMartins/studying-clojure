(ns content.writing-macros.building-lists-for-evaluation.distinguishing-symbols-and-values)

;Lembra que estudamos o Reader e o Evaluator?
;Quando escrevemos:
;(+ 10 20)
;O Reader produz uma lista com três elementos:
;- O símbolo +.
;- O número 10.
;- O número 20.

;Mas existe uma diferença importante entre o símbolo + e o número 10.
;O símbolo + representa um identificador que, quando avaliado, será resolvido para uma função.
;O número 10 já é um valor. Ele não precisa ser resolvido.

;Vamos observar isso no REPL:
(type '+)
;;; clojure.lang.Symbol

(type 10)
;;; java.lang.Long
;
(type :preco)
;;; clojure.lang.Keyword

;Temos três tipos diferentes:
;Expressão	Tipo	Significado
;'+	Symbol	Identificador da função
;10	Long	Valor numérico
;:preco	Keyword	Valor utilizado, por exemplo, como chave

;3.2 O problema ao construir uma lista
;Imagine que queremos construir a expressão:
;(+ 10 20)

;Podemos tentar:
(list + 10 20)
;Mas existe um problema!
;O Clojure avalia os argumentos de list antes de chamar a função.
;Portanto:
;- + é avaliado e vira a função de adição.
;- 10 continua sendo 10.
;- 20 continua sendo 20.
;O resultado é uma lista contendo a própria função +, e não o símbolo +.

;Conceitualmente:
;(#<function clojure.core/+> 10 20)
;Isso não é a mesma estrutura que:
;(+ 10 20)
;Para construir código, precisamos preservar o símbolo.

;3.3 Usando quote
;É aqui que entra o recurso que estudamos no capítulo anterior:
(list '+ 10 20)

;Agora o apóstrofo impede que + seja avaliado.
;O resultado é:
;(+ 10 20)

;E podemos verificar:
(type (first (list '+ 10 20)))
;;; clojure.lang.Symbol
(type (first (list + 10 20)))
;Agora temos o símbolo correto.

;E se quisermos executar essa estrutura?
(eval (list '+ 10 20))
;;; 30

;Perceba o processo:
;1. list constrói uma estrutura de dados.
;2. quote preserva o símbolo +.
;3. eval interpreta a estrutura como código.
;4. O resultado é 30.


;3.4 Uma distinção que merece atenção
;Veja estas duas expressões:
;(list '+ 10 20)
;(list '+ 'valor 20)

;A primeira produz uma expressão com números.
;A segunda produz uma expressão que contém o símbolo valor.

;Se existir:
(def valor 100)

;Podemos avaliar a segunda estrutura:
(eval (list '+ 'valor 20))
;;; 120

;O símbolo valor é resolvido pelo Evaluator quando a expressão é executada.
;Isso é exatamente o que precisamos para construir macros: algumas partes devem ser símbolos que serão resolvidos depois, enquanto outras são valores que queremos inserir diretamente.

;Consolidando o conceito
;Guarde esta distinção:
;- Valor: algo que já existe, como 10, "Arroz" ou :preco.
;- Símbolo: um identificador que pode ser resolvido durante a avaliação, como +, valor ou calcular-total.
;- Quote: impede que uma expressão seja avaliada, preservando sua representação como dado.
;Esse entendimento será fundamental quando começarmos a combinar símbolos e valores dinamicamente.