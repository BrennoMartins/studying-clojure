(ns content.functions.returning-functions)

(defn criar-multiplicador [fator]
  (fn [numero]
    (* numero fator)))

;1. O que você espera que seja retornado por (criar-multiplicador 3)?
;2. O que acontece quando executamos ((criar-multiplicador 3) 10)?
;3. Por que precisamos de dois pares de parênteses nessa segunda expressão?


;Agora crie uma função chamada criar-calculadora-de-meta que recebe um percentual e retorna uma função que calcula esse percentual de um valor.
;Por exemplo, a função retornada por (criar-calculadora-de-meta 0.1) deve calcular 10% de qualquer valor recebido.
(defn criar-calculadora-de-meta
  [perc]
  (fn [valor-total]
    (* valor-total perc)))

((criar-calculadora-de-meta 0.1) 5000)
;; => 500.0

;A função externa recebe 0.1 e retorna uma função que mantém esse percentual disponível para os cálculos seguintes.
;Isso demonstra um conceito importante: a função retornada continua tendo acesso ao valor perc recebido pela função externa,
; mesmo depois que a chamada externa terminou. Esse comportamento está relacionado a closures (fechamentos), que são uma parte importante da programação funcional.

