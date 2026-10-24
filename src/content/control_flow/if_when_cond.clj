(ns free-exercice.control-flow.if-when-cond)


(defn testing
  [comprar saldo valor]
  (if (and comprar (<= valor saldo) :nao true)
    "  ai"
    "Sem saldo"
    ))


  (testing true 20 20)

;;
;; OR  -> returns either the first truthy value or the last value
;; AND -> returns the first falsey value or, if no values are falsey, the last truthy value


;; Meta Economia
(defn meta-economia
  [saldo]
  (cond
    (zero? saldo) :nao-iniciado
    (neg? saldo) :saldo-negativo
    (>= saldo 1000) :meta-atingida
    (< saldo 1000)  :em-progresso
    :else :nao-iniciado))

(meta-economia 0)
(meta-economia -15)
(meta-economia 110)
(meta-economia 1300)

(if nil :sim :nao)
(if 0 :sim :nao)
(if false :sim :nao)
(if [] :sim :nao)
(if :ok :sim :nao)
;;nao, sim, nao, sim, sim


;zero
(zero? 0)
;;positivo
(pos? 1)
;;negativo
(neg? -1)
;;maior ou igual a 100.
(>= 10 100)



;;Usando if, classifique uma idade:
;; menor que 18 → :menor
;; 18 ou mais → :maior
;;Teste com 15, 18 e 30.
(defn idade [idade] (if (>= idade 18) :maior :menor))

(idade 15)
(idade 18)
(idade 30)



;Executando uma ação condicional
;Usando when, escreva uma expressão que imprima "Meta atingida!" quando o saldo for maior ou igual a 1.000.
;Se a condição não for verdadeira, nada deve ser impresso.
;Teste com 500 e 1500. Observe também o valor retornado pela expressão.

(defn meta-when [saldo] (when (>= saldo 1000) "Meta atingida!"))

(meta-when 500)
(meta-when 1500)


;Usando cond, classifique a temperatura:
;Temperatura	Resultado
;Menor que 10	:frio
;De 10 até 24	:agradavel
;De 25 até 34	:quente
;35 ou mais	:muito-quente
;Teste os limites: 9, 10, 24, 25, 34 e 35.

(defn temp
  [temperatura]
  (cond
    (< temperatura 10) :frio
    (and (>= temperatura 10) (<= temperatura 24)) :agradavel
    (and (>= temperatura 25) (<= temperatura 34)) :quente
    :else :muito-quente))

(temp 9)
(temp 10)
(temp 24)
(temp 25)
(temp 34)
(temp 35)


;Validador de estoque do LifeOS
;Um produto recebe um número que representa seu estoque. Classifique-o com cond:
;- estoque negativo → :estoque-invalido
;- estoque igual a zero → :sem-estoque
;- estoque entre 1 e 5, inclusive → :estoque-baixo
;- estoque acima de 5 → :estoque-ok
;Pense na ordem das condições antes de escrever.

(defn estoque
  [])

;Acesso ao sistema
;Considere duas informações: logado? e admin?, ambas booleanas.
;Use if e, se precisar, and para produzir:
;- usuário não logado → :acesso-negado
;- usuário logado e administrador → :acesso-total
;- usuário logado e não administrador → :acesso-limitado
;Teste as quatro combinações possíveis de true e false.


;Meta de economia
;Vamos revisitar nossa primeira feature.
;Reimplemente classificar-meta com cond, atendendo a estes casos:
;- saldo negativo → :saldo-negativo
;- saldo zero → :nao-iniciada
;- saldo positivo abaixo de 1.000 → :em-progresso
;- saldo de 1.000 ou mais → :meta-atingida
;Regra adicional: não use and nessa implementação. Procure aproveitar o fluxo do cond.


;Predicados e valores inválidos
;Queremos classificar um valor recebido por uma futura API do LifeOS:
;- número negativo → :invalido
;- zero → :zerado
;- número positivo → :valido
;- qualquer valor que não seja número → :tipo-invalido
;Implemente a regra usando cond e predicados apropriados.
;Atenção: pense no que acontece se você tentar aplicar neg? a uma string, por exemplo "100".
;Dica: pesquise no REPL a função number? e pense em como ela pode proteger as demais condições.