(ns content.writing_macros.macros-are-essential)

;Vamos começar com uma pergunta fundamental:
;Por que Clojure precisa de macros se já temos funções?

;Imagine uma função para registrar uma operação:
(defn registrar-operacao [operacao]
  (println "Iniciando...")
  (operacao)
  (println "Finalizado!"))

;Podemos utilizá-la assim:
(registrar-operacao
  (fn []
    (println "Processando venda...")))

;Funciona, mas precisamos envolver a operação em uma função anônima (fn).
;Agora imagine que pudéssemos escrever:
;(registrar-operacao
;  (println "Processando venda..."))

;Parece mais simples, certo?
;Mas existe um problema: uma função comum não consegue impedir que seus argumentos sejam avaliados antes de ser chamada.
;Nesse exemplo, o println seria executado antes de registrar-operacao começar.
;Uma macro consegue fazer diferente: ela recebe a expressão como código, sem executá-la, e decide onde e como ela será executada.
;Isso permite criar construções que se comportam como parte da própria linguagem.

;O ponto principal
;Funções permitem abstrair comportamentos.
;Macros permitem abstrair a própria sintaxe e controlar a avaliação do código.
;Essa é a razão pela qual macros são tão importantes em Clojure.