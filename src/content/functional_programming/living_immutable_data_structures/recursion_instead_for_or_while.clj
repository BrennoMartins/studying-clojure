(ns content.functional-programming.living-immutable-data-structures.recursion-instead-for-or-while)

;Você já conhece reduce, então não vamos simplesmente repetir isso.
; Quero mostrar como chegar a esse tipo de solução usando recursão e entender o que acontece por baixo dos panos.

;Em Java, provavelmente você faria:
;public int somar(List<Integer> numeros) {
;    int total = 0;
;
;    for (Integer numero : numeros) {
;        total += numero;
;    }
;
;    return total;
;}
;
;Observe o que acontece:
;1. Criamos uma variável total.
;2. Percorremos a coleção.
;3. Modificamos total a cada iteração.
;4. Retornamos o resultado.
;Essa é uma abordagem imperativa: descrevemos passo a passo como o resultado deve ser produzido.
;
;Em Clojure, podemos pensar de outra maneira.

;2. Entendendo a recursão
;Recursão acontece quando uma função chama a si mesma.
;
;Vamos começar com uma função que imprime os elementos de uma coleção:
(defn imprimir-numeros
  [numeros]
  (when (seq numeros)
    (println (first numeros))
    (imprimir-numeros (rest numeros))))
;
;Execute:
(imprimir-numeros [10 20 30 40 50])
;
;Resultado:
;10
;20
;30
;40
;50
;
;Vamos entender o funcionamento.
;Primeira chamada
;(imprimir-numeros [10 20 30 40 50])
;
;- first retorna 10.
;- rest retorna [20 30 40 50].
;- A função chama a si mesma com o restante da coleção.
;Quando termina?
;Quando não existem mais elementos:
;(seq [])
;;; => nil
;
;O when não executa mais o corpo da função.
;
;Esse é o nosso caso-base: a condição que determina quando a recursão deve parar.

;4. Vamos fazer algo mais interessante: somar números recursivamente
;Agora queremos substituir o reduce por uma implementação recursiva.
(defn somar
  [numeros]
  (if (empty? numeros)
    0
    (+ (first numeros)
       (somar (rest numeros)))))
;
;Execute:
;(somar [10 20 30])
;
;Resultado:
;60
;
;Vamos acompanhar as chamadas:
;(somar [10 20 30])
;10 + (somar [20 30])
;
;10 + 20 + (somar [30])
;
;10 + 20 + 30 + (somar [])
;
;10 + 20 + 30 + 0
;
;60
;
;Perceba que estamos construindo o resultado durante o retorno das chamadas recursivas.
;Essa é uma recursão tradicional, também chamada de recursão não terminal.


;5. Um problema: Stack Overflow
;Agora imagine que nossa coleção tenha 100.000 elementos.
;A função anterior pode gerar muitas chamadas recursivas pendentes.
;Cada chamada precisa aguardar o resultado da próxima para conseguir finalizar sua operação.
;Isso pode consumir muita memória da pilha de execução (call stack).

;Em Java, você provavelmente já conhece o erro:
;StackOverflowError
;Clojure oferece um recurso para resolver esse problema: recur.


(defn contar-elementos
  [numeros]
  (if (empty? numeros)
    0
    (+ 1
       (contar-elementos (rest numeros)))))

(contar-elementos [10 20 30 40 50])


(defn maior-numero
  [numeros]
  (let [maior 0]
    (if (empty? numeros)
      (if (< maior (first numeros))
        (do
          ;precisava atribuir o numero maior
          (maior-numero (rest numeros))
          )
        )
      maior
      )))

(defn maior-numero
  [numeros]
  (if (nil? (next numeros))
    (first numeros)
    (let [primeiro (first numeros)
          maior-restante (maior-numero (rest numeros))]
      (if (> primeiro maior-restante)
        primeiro
        maior-restante))))

(maior-numero [-10 10 50 30 90 20])



(defn contar-pares
  [numeros]
  (if (empty? numeros)
    0
    (if (even? (first numeros))
      (+ 1 (contar-pares (rest numeros)))
      (contar-pares (rest numeros)))))

(contar-pares [1 2 3 4 5 6])
;; => 3

