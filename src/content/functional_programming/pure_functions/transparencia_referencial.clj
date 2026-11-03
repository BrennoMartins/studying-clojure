(ns content.functional-programming.pure-functions.transparencia-referencial
  (:import (java.time LocalDateTime)))

;Transparência referencial significa que podemos substituir uma expressão pelo seu resultado sem alterar o comportamento do programa.
;Vamos utilizar um exemplo.
(defn somar
  [a b]
  (+ a b))
;
;Imagine esta expressão:
(* 2 (somar 10 20))

;Como sabemos que (somar 10 20) sempre retorna 30, podemos substituir a chamada pelo resultado:
;(* 2 30)

;Resultado:
;60

;O comportamento do programa não mudou. Isso é transparência referencial.

;Agora considere:
;(* 2 (LocalDateTime/now))
;
;Não podemos simplesmente substituir a chamada por um valor fixo e esperar que o programa continue apresentando o mesmo comportamento em todas as execuções.
;O resultado depende do momento em que a expressão é avaliada.

;Por que isso importa?
;Imagine que você tenha uma função responsável por calcular o patrimônio de um investidor:
(defn calcular-patrimonio
  [saldo investimentos]
  (+ saldo investimentos))
;
;Podemos confiar que:
(calcular-patrimonio 10000 5000)
;;; => 15000
;
;Sempre que essa expressão aparecer no programa, podemos substituí-la por 15000.

;Isso facilita:
;- Raciocinar sobre o código.
;- Testar funções isoladamente.
;- Reutilizar resultados.
;- Identificar erros.
;- Otimizar a execução.
;É uma das razões pelas quais a programação funcional valoriza tanto funções puras.


;7. Exercício de fixação
;Agora quero testar se você realmente entendeu a transparência referencial.
;
;Considere:
(defn calcular-total
  [preco quantidade]
  (* preco quantidade))
;
;E:
(defn calcular-desconto
  [total percentual]
  (- total (* total percentual)))
;
;Temos esta expressão:
(calcular-desconto
  (calcular-total 100 3)
  0.10)

;Perguntas:
;1. Qual é o resultado dessa expressão?
;2. Podemos substituir (calcular-total 100 3) pelo resultado numérico diretamente? Por quê?
;3. Podemos substituir toda a expressão por seu resultado numérico sem alterar o comportamento do programa?
;4. Imagine que calcular-desconto passasse a registrar cada desconto em um atom. Ainda poderíamos considerar essa função referencialmente transparente?

