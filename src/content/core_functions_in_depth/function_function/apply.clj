(ns content.core-functions-in-depth.function-function.apply)

;1. apply — Aplicando uma função a uma coleção
;Você já conhece funções como:
(+ 10 20 30)
;;; => 60
;
;Aqui, passamos três argumentos para +.
;
;Agora, imagine que os números estejam armazenados em um vetor:
(def numeros [10 20 30])
;
;Talvez você tente fazer:
;(+ numeros)
;Isso não funciona como queremos, porque + espera argumentos numéricos, e estamos passando um vetor como argumento.


;É aqui que entra o apply.
;Como funciona?
;O apply recebe uma função e uma coleção de argumentos, aplicando a função aos elementos dessa coleção.
(apply + [10 20 30])
;; => 60
;
;É como se o Clojure executasse:
;(+ 10 20 30)
;
;Outro exemplo:
(apply max [15 42 8 31])
;;; => 42
;
;Nesse caso, é como executar:
(max 15 42 8 31)
;
;E se a função receber argumentos fixos e uma coleção?
;O apply também permite passar argumentos individuais antes da coleção final:
(apply str "Olá, " ["mundo" "!"])
;;; => "Olá, mundo!"
;
;O Clojure combina os argumentos e aplica a função como se tivéssemos escrito:
;(str "Olá, " "mundo" "!")
;
;Regra importante: no uso mais comum, o último argumento de apply é a coleção cujos elementos serão passados individualmente à função.
;🧠 Quando usar apply?
;Ele é útil quando você tem uma coleção de valores, mas precisa chamar uma função que recebe argumentos separados.
;Por exemplo, imagine que você registra os tempos de estudo de cada dia:
;(def minutos-estudo [30 45 60 25 90])
;
;Você pode calcular o total:
;(apply + minutos-estudo)
;;; => 250
;
;Ou descobrir o maior tempo:
;(apply max minutos-estudo)
;;; => 90
;
;Observe que, para somar os elementos de uma coleção, reduce também é uma opção. Já apply é especialmente útil quando queremos chamar uma função existente com os elementos de uma coleção como argumentos.

;🧪 Exercícios
;Tente resolver estes três exercícios antes de olhar qualquer solução completa.
;Exercício 1 — Somar valores
;Considere:
(def aportes [1500 800 1200 500])
;Crie uma função chamada somar-aportes que receba um vetor e utilize apply para calcular a soma dos valores.

(defn somar-aportes
  [aportes]
  (apply + aportes))
;Uso esperado:
(somar-aportes aportes)
;;; => 4000

;Exercício 2 — Encontrar o maior valor
;Considere:
(def tempos [25 60 45 90 30])

;Crie uma função chamada maior-tempo que utilize apply e retorne o maior valor da coleção.

(defn maior-tempo
  [tempo]
  (apply max tempo))

;Resultado esperado:
(maior-tempo tempos)
;;; => 90
;
;Exercício 3 — Combinar argumentos
;Considere:
(def palavras ["estudando" "Clojure" "hoje"])

;Crie uma função chamada montar-frase que receba um vetor de palavras e utilize apply com str para produzir:
(defn montar-frase
  [palavras]
  (apply str palavras))



(montar-frase palavras)
;;; => "estudandoClojurehoje"
;
;Desafio extra: como você modificaria a função para obter "estudando Clojure hoje", com espaços entre as palavras?
; Não precisa resolver esse desafio agora se quiser se concentrar nos três exercícios.
(defn montar-frase
  [palavras]
  (clojure.string/join " " palavras))

(montar-frase palavras)