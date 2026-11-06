(ns content.organizing-your-project.storing-objects-with-def)


;1. Diferença entre def e defn
;def associa um nome a um valor:
(def taxa-juros 0.10)
(def nome "Cecília")
(def produtos [])

;Esse valor pode ser um número, string, vetor, mapa ou até uma função.

;defn é uma forma conveniente de definir uma função:
(defn calcular-juros [capital]
  (* capital taxa-juros))
;
;Inclusive, conceitualmente, defn é uma macro que cria uma Var associada a uma função.

;2. O que acontece quando redefinimos?
;Imagine:
(def taxa-juros 0.10)
;
(defn calcular-juros [capital]
  (* capital taxa-juros))

;Executamos:
(calcular-juros 1000)
;;; 100.0
;

;Agora redefinimos:
(def taxa-juros 0.15)

;E executamos novamente:
(calcular-juros 1000)
;;; 150.0

;A função continua a mesma, mas consulta o valor atual da Var taxa-juros.

;3. O conceito de Var
;Pense na Var como uma referência nomeada dentro de um namespace, associada a um valor.
;Namespace atual
;taxa-juros
;0.15
;Valor atualmente associado à Var
;A função calcular-juros consulta essa Var quando é executada.
;Isso é importante porque, no próximo tópico, vamos aprender a trabalhar com vários namespaces e entender como essas referências são organizadas.
