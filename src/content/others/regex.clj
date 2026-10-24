(ns content.others.regex)

(def mensagem "Aporte realizado: R$ 2750 em outubro")

(re-find #"\d+" mensagem)
(re-find #"\d+" "Aporte realizado: R$ 80 em outubro")

;Por exemplo, \d representa um dígito de 0 a 9, e + significa “um ou mais” do elemento anterior.

(re-find #"\d+" "123ABC")
;; => "123"

(re-matches #"\d+" "123ABC")
;; => nil

(re-matches #"\d+" "123")
;; => "123"
;re-find procura um padrão em qualquer parte da string.
;re-matches exige que a string inteira corresponda ao padrão.

(re-matches #"\d{6}" "202611")
(re-matcher #"\d{6}" "123456")
;; => "2026"

(re-matches #"\d{4}" "123")
;; => nil

(re-matches #"\d{4}" "12345")
;; => nil

(re-matcher #"\d{6}" "123456")
;{n} quantidade que tem que corresponder


(defn codigo-valido?
  [codigo]
  (if (= (re-matches #"\d{6}" codigo) codigo)
    true
    false))

(codigo-valido? "123456")
;; => true

(codigo-valido? "12345")
;; => false

(codigo-valido? "1234567")
;; => false

(codigo-valido? "12A456")
;; => false

;Vamos aprender um recurso novo de regex: grupos de captura, usando parênteses ()
(def mensagem "Aporte de R$ 1250 realizado em outubro")

;Podemos criar um padrão para identificar o valor:
(re-find #"R\$ (\d+)" mensagem)
;;; => ["R$ 1250" "1250"]

;Observe o resultado: o primeiro elemento é a correspondência completa, e o segundo é o conteúdo capturado pelo grupo (\d+).

(def transacao "Compra de R$ 89 realizada no mercado")
;Escreva uma expressão com re-find que encontre a transação monetária e capture apenas o valor numérico usando um grupo.
(re-find #"R\$ (\d+)" transacao)