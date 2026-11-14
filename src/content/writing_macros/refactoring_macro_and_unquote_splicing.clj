(ns content.writing-macros.refactoring-macro-and-unquote-splicing)

;1. O problema: receber várias expressões
;Imagine que queremos criar uma macro para executar uma sequência de operações:
;(executar-operacoes
;(println "Iniciando venda")
;(println "Calculando total")
;(println "Finalizando venda"))
;
;Podemos definir a macro assim:
;(defmacro executar-operacoes [& operacoes]
;`(do ~operacoes))

;Mas existe um problema.
;O parâmetro & operacoes recebe todas as expressões restantes em uma sequência.

;Conceitualmente:
;operacoes =
;[(println "Iniciando venda")
;(println "Calculando total")
;(println "Finalizando venda")]

;Quando usamos:
;~operacoes

;Inserimos a sequência inteira como um único elemento.
;O resultado fica conceitualmente assim:
;(do
;[(println "Iniciando venda")
;(println "Calculando total")
;(println "Finalizando venda")])
;Isso não é o que queremos. O do deve receber cada expressão como um argumento separado.

;2. A solução: Unquote Splicing (~@)
;É exatamente para isso que existe o ~@.

;Veja a macro corrigida:
;(defmacro executar-operacoes [& operacoes]
;`(do ~@operacoes))

;Agora a expansão será:
;(do
;(println "Iniciando venda")
;(println "Calculando total")
;(println "Finalizando venda"))

;Cada expressão é inserida individualmente.
;Diferença entre ~ e ~@
;Imagine:
;(def expressoes
;['(println "A")
;'(println "B")])

;Com ~:
;`(do ~expressoes)

;A sequência inteira é inserida como um único elemento.
;Com ~@:
;`(do ~@expressoes)

;Os elementos são inseridos individualmente.
;Unquote (~)
;(do [expressao-1 expressao-2])

;Unquote-splicing (~@)
;(do expressao-1 expressao-2)

;3. Refatorando nossa macro
;Podemos melhorar a macro de operações:
(defmacro executar-operacoes [& operacoes]
`(do
(println "Iniciando operações...")
~@operacoes
(println "Operações finalizadas!")))

;Utilização:
(executar-operacoes
(println "Validando venda")
(println "Calculando total")
(println "Registrando venda"))

;Resultado:
;Iniciando operações...
;Validando venda
;Calculando total
;Registrando venda
;Operações finalizadas!

;Observe que conseguimos criar uma estrutura própria para executar um bloco de código.
;Isso é uma abstração sintática: criamos uma construção que não existia originalmente na linguagem.

;4. Um detalhe sobre &
;Você já conhece & de funções com argumentos variáveis:
;(defn somar [a & numeros]
;(+ a (apply + numeros)))

;Nas macros, funciona de maneira semelhante:
;(defmacro executar-operacoes [& operacoes]
;...)
;A diferença não está no &, mas no conteúdo recebido: a macro recebe formas de código sem avaliá-las.

;Consolidando o capítulo até aqui
;Recurso	Finalidade
;list	Construir listas
;'	Preservar código como dado
;`	Construir código com resolução de namespaces
;~	Inserir uma expressão avaliada
;~@	Inserir vários elementos individualmente
;&	Receber quantidade variável de expressões