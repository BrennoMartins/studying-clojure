(ns content.data-structure.strings
  (:require [clojure.string :as str]))


(str "Olá" " mundo")
(str "Clojure " "é " "legal")
(str "Saldo: " 3750)
;"Olá mundo" "Clojure é legal" "Saldo: 3750"

;Ela disse: "Olá!"
(str "Ela disse: \"Olá!\"")

;Primeira linha e Segunda linha, cada uma em uma linha.
(str "Primeira linha \nSegunda linha")
;
(str "C:\\Users\\Brenno")

;Um conceito importante
;Expressão	Significado
;\"	Aspa dupla como parte do texto
;\n	Quebra de linha
;\\	Barra invertida como parte do texto

;Exercício 3 — Concatenação e espaços
;Qual é a diferença entre os resultados destas expressões?
(str "Vida" "OS")
(str "Vida" " " "OS")
;O espacamento, o primeiro fica tudo junto VidaOS o segundo separa Vida OS

;Agora escreva uma expressão usando str para produzir:
;Meta: R$ 10000
;Saldo: R$ 3750
(str "Meta: R$ " 10000)
(str "Saldo: R$ " 3750)


;Exercício 4 — Tipos e tamanho
;Experimente:
(type "LifeOS")
(count "LifeOS")
(empty? "")
(empty? " ")
; String, 6, true, false
; a diferenca eh que o espaco eh um caracter e nao eh vazio

(clojure.string/upper-case "lifeos")
(clojure.string/lower-case "CLOJURE")
(clojure.string/capitalize "clojure")


(str/trim "   LifeOS   ")
(str/triml "   LifeOS   Teste   ")
(str/trimr "   LifeOS   ")

;trim executa em todos os espacos, do triml somente da esquerda e o r da diretira


(str/includes? "Clojure for the Brave and True" "Brave")
(str/includes? "Clojure for the Brave and True" "brave")
(str/starts-with? "LifeOS" "Life")
(str/ends-with? "LifeOS" "OS")
;;true false true true
;; seria para ver se existe a palavra Brave dentro do frase , e considerar como case sentive
;; starts-with para ve se comeca com os caracteres e se o ends-with se termina


;Exercício 8 — Aplicação no LifeOS
;Imagine que você recebeu o nome de um projeto digitado por um usuário:
(def nome-projeto "   controle financeiro   ")
;Sem criar uma aplicação completa, escreva uma expressão que remova os espaços das extremidades e outra que transforme o nome em letras maiúsculas.
;O objetivo é combinar o que você aprendeu, não apenas chamar funções isoladas.
(str/upper-case (str/trim nome-projeto))


(def nome-projeto "   controle financeiro   ")
(def categoria "investimentos")
;;Projeto: CONTROLE FINANCEIRO | Categoria: investimentos

(str "Projeto: " (str/upper-case (str/trim nome-projeto)) "| Categoria: " categoria)


(str/join ", " [:clojure :estudos :produtividade])
