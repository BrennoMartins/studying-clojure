(ns content.functions.function-body)

;Regra para guardar: em Clojure, o valor retornado implicitamente por uma função é o resultado da última expressão do corpo.

;Agora imagine que queremos calcular o valor total de uma posição de investimento e retornar uma mensagem com esse valor.
;Complete esta função:
(defn resumir-posicao [quantidade preco]
  (let [valor-total (* quantidade preco)]
    (str "Valor total: "valor-total)))

(resumir-posicao 10 25)
;; => "Valor total: 250"

;Agora imagine que queremos calcular o total de uma posição e verificar se ela vale pelo menos R$ 1.000.
;Complete a função abaixo para que ela retorne "Posição relevante" quando o valor for maior ou igual a 1000, e "Posição pequena" caso contrário.
(defn classificar-posicao [quantidade preco]
  (let [valor-total (* quantidade preco)]
    (if (>= valor-total 1000)
    "Posição relevante"
    "Posição pequena")))


(classificar-posicao 50 25)
;; => "Posição relevante"

(classificar-posicao 10 25)
;; => "Posição pequena"


;All Functions Are Created Equals
;A ideia central deste tópico é que, em Clojure, as funções são valores.
;Isso significa que você pode atribuí-las a nomes, passá-las como argumentos para outras funções e retorná-las de outras funções.
(defn dobrar [n]
  (* n 2))

(def operacao dobrar)
(operacao 5)
;Quando você define operacao com dobrar, está associando esse nome à mesma função. Assim, chamar (operacao 5) é equivalente a chamar (dobrar 5)


;Imagine que você queira criar uma função que receba um número e uma operação, aplicando a operação ao número.
;Você já conhece as funções dobrar e inc (que incrementa um número em 1).
(defn aplicar-operacao [operacao numero]
  (operacao numero))

;Os resultados esperados são:
(aplicar-operacao dobrar 5)
;; => 10

(aplicar-operacao inc 5)
;; => 6


(defn somar [a b]
  (+ a b))
;Queremos adaptar aplicar-operacao para receber uma operação e dois números:
(defn aplicar-operacao
  ([operacao numero]
  (operacao numero))
  ([operacao primeiro-numero segundo-numero]
   (operacao primeiro-numero segundo-numero)))

(aplicar-operacao somar 10 20)
;; => 30

(defn escolher-operacao [tipo]
  (if (= tipo :dobrar)
    dobrar
    inc))

(escolher-operacao :dobrar)
;retorna a funcao dobrar
((escolher-operacao :dobrar) 5)
;retorna 10