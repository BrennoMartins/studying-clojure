(ns content.clojure-alchemy-reading-evaluation-macros.reader.reader)

;1. O que é o Reader?
;Até agora, vimos que existem duas etapas fundamentais:
;- Reader: transforma o texto do código em estruturas de dados Clojure.
;- Evaluator: avalia essas estruturas e produz resultados.

;Vamos olhar mais de perto para o Reader.
;Imagine que você escreve:
(+ 10 20)

;Para nós, isso parece uma chamada de função.
;Para o Reader, inicialmente, é uma sequência de caracteres que precisa ser interpretada.

;Ele reconhece:
;Elemento	Interpretação
;(	Início de uma lista
;          +	Símbolo
;          10	Número
;          20	Número
;          )	Fim da lista

;O resultado é uma estrutura de dados Clojure: uma lista contendo um símbolo e dois números.

;2. O Reader não executa funções
;Essa distinção é fundamental.

;Considere:
;(+ 10 20)
;O Reader não calcula a soma.

;Ele apenas produz a representação da expressão.
;Podemos observar isso utilizando read-string:
(read-string "(+ 10 20)")
;Resultado:
;(+ 10 20)

;O interessante é que read-string retorna uma estrutura de dados, não o resultado da soma.

;Podemos verificar o tipo:
(type (read-string "(+ 10 20)"))
;Resultado:
;clojure.lang.PersistentList

;E também:
(type (first (read-string "(+ 10 20)")))
;Resultado:
;clojure.lang.Symbol
;Perceba que conseguimos inspecionar o código como dados!

;3. O Reader reconhece diferentes estruturas
;Experimente:
(read-string "42")
(read-string "\"Olá\"")
(read-string ":nome")
(read-string "[1 2 3]")
(read-string "{:nome \"Ana\"}")
(read-string "(+ 10 20)")

;O Reader identifica o tipo de cada estrutura:
;Texto	Estrutura produzida
;42	Número
;"Olá"	String
;:nome	Keyword
;[1 2 3]	Vetor
;{:nome "Ana"}	Mapa
;(+ 10 20)	Lista

;Você já conhece praticamente todas essas estruturas dos capítulos anteriores.
;Agora estamos entendendo como elas surgem a partir do texto escrito.

;4. Reader Macros
;Aqui começa uma parte muito interessante.
;O Reader possui atalhos sintáticos chamados Reader Macros.
;Um exemplo que você já conhece é o apóstrofo:
'(+ 10 20)
;Ele é uma forma abreviada de escrever:
(quote (+ 10 20))

;O Reader reconhece o apóstrofo e produz uma estrutura equivalente à segunda expressão.
;O que o Reader faz
;'(+ 10 20)
;Reader reconhece o apóstrofo
;(quote (+ 10 20))

;A expressão resultante preserva a lista como dado.

;Outros exemplos de atalhos que encontraremos:
;Atalho	Forma expandida
;'x	(quote x)
;#'x	(var x)
(def valor 10)
(var valor)
;@x	(deref x)
;#(...)	Forma abreviada de função anônima

;Não precisamos dominar todos agora. Vamos estudá-los individualmente.

;5. Vamos experimentar no REPL
;Quero que você execute estas expressões:

;Experimento A
(read-string "'(+ 10 20)")

;Experimento B
(read-string "(+ 10 20)")

;Experimento C
(read-string "#(+ % 10)")

;Observe especialmente a diferença entre A e B.

;O Reader trabalha recursivamente
;Considere esta expressão:
;(+ 10 (* 5 2))

;Temos uma lista dentro de outra lista.
;O Reader identifica a estrutura completa:
;Estrutura externa
;Lista externa
;+
;10
;
;Lista interna
;*
;5
;2

;O resultado da leitura é uma lista cujo terceiro elemento também é uma lista.
;O Reader não calcula 5 * 2 nem a soma externa. Apenas reconhece e constrói a estrutura.

;2. Inspecionando a estrutura
;Podemos utilizar read-string e funções que você já conhece:
(def codigo (read-string "(+ 10 (* 5 2))"))

(first codigo)
;;; +
;
(second codigo)
;;; 10
;
(last codigo)
;;; (* 5 2)
;

;Observe que last retorna a lista interna, não o resultado de sua multiplicação.
;Também podemos inspecioná-la:
(type codigo)
;;; clojure.lang.PersistentList
;
(type (last codigo))
;;; clojure.lang.PersistentList

;Isso demonstra que o código foi transformado em estruturas de dados aninhadas.

;3. Reader não significa apenas listas
;O mesmo acontece com mapas, vetores e conjuntos.
;
;Por exemplo:
(read-string "{:nome \"Arroz\" :preco (+ 10 15)}")

;O Reader reconhece:
;- Um mapa.
;- Keywords como chaves.
;- Uma string como valor.
;- Uma lista como outro valor.
;A lista (+ 10 15) continua sendo uma estrutura de código, mas ainda não foi executada.

;4. Uma distinção importante: leitura e avaliação
;Considere:
(read-string "(+ 10 (* 5 2))")

;Resultado: estrutura de dados.
;Agora:
(eval (read-string "(+ 10 (* 5 2))"))
;Resultado: 20.
;Reader Texto → Lista aninhada

;Evaluator ;Lista aninhada → Resultado 20

;5. Fechamento da seção The Reader
;Até aqui, estudamos:
;Conceito	Aprendizado
;Reader	Transforma texto em estruturas de dados
;read-string	Lê uma string e retorna uma estrutura
;Symbols	Representam identificadores
;Quote (')	Preserva expressões como dados
;        Reader Macros	Abreviações reconhecidas durante a leitura
;        Estruturas aninhadas	Coleções podem conter outras coleções e listas de código
;
;        Uma ressalva: o Reader também pode ler múltiplas formas de código com funções específicas, mas read-string lê apenas uma forma. Não precisamos aprofundar isso agora.