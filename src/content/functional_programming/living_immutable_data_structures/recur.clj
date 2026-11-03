(ns content.functional-programming.living-immutable-data-structures.recur)

;Imagine uma coleção com 100 mil elementos. Podemos acabar acumulando chamadas demais na pilha de execução.
;Isso pode provocar um StackOverflowError.

;2. O que é Tail Recursion?
;Uma recursão é chamada de Tail Recursive quando a chamada recursiva é a última operação executada pela função.
;Compare:
;Recursão tradicional
;(+ 1 (contar-elementos (rest numeros)))
;A chamada recursiva retorna e ainda precisamos somar 1.

;Recursão de cauda
;(recur (rest numeros) (inc total))

;A chamada é a última operação. Não existe trabalho pendente depois dela.


;5. Exercício prático
;Vamos começar com um exercício que você já resolveu, mas agora utilizando loop e recur.
;
;Exercício 1
;Contar elementos

(defn contar-elementos
  [numeros]
  (loop [contador 0
         numeros-restante numeros]
    (if (empty? numeros-restante)
      contador
      (recur (inc contador) (rest numeros-restante)))))

;Implemente novamente:
(contar-elementos [10 20 30 40 50])
;;; => 5


;Regras:
;- Utilize loop e recur.
;- Não utilize count.
;- Utilize um acumulador chamado total.
;- Não utilize recursão tradicional chamando a própria função.
;Quero que você tente escrever sozinho.
;
;Uma dica: o loop terá dois bindings, numeros e total, assim como no exemplo anterior. Mas não precisa copiar o exemplo inteiro: tente reconstruir a lógica.
;
;Depois revisamos seu código e avançamos para transformar maior-numero e contar-pares em versões com recur.


;3. Um detalhe importante sobre Clojure
;O recur precisa ser a última operação executada no caminho correspondente.
;Por exemplo:
;(recur (inc contador)
;       (rest numeros-restante))

;Isso é válido.
;Mas isto não seria:
;(+ 1 (recur ...))
;Porque ainda existiria uma operação pendente depois da recursão.


(defn maior-numero
  [numeros]
  (if (nil? (next numeros))
    (first numeros)
    (let [primeiro (first numeros)
          maior-restante (maior-numero (rest numeros))]
      (if (> primeiro maior-restante)
        primeiro
        maior-restante))))

(defn maior-numero
  [numeros]
  (loop [maior-numero (first numeros)
         restante-numero (rest numeros)]
    (if (empty? restante-numero)
      maior-numero
      (if(> (first restante-numero) maior-numero)
        (recur (first restante-numero) (rest restante-numero))
        (recur maior-numero (rest restante-numero))))))

(maior-numero [-10 -50 -30 -90 -20])

;
;4. Um conceito que quero reforçar
;Compare as duas implementações:
;Recursão tradicional:
;(+ 1 (contar-elementos (rest numeros)))

;Loop/recur:
;(recur (inc contador) (rest numeros))
;
;Na primeira, o resultado é construído quando as chamadas retornam.
;Na segunda, o resultado é construído durante o percurso, utilizando acumuladores.
;Essa diferença é fundamental para entender programação funcional.

(defn contar-pares
  [numeros]
  (loop [contador-pares 0
         numeros numeros]
    (if (empty? numeros)
      contador-pares
      (if (even? (first numeros))
        (recur (inc contador-pares) (rest numeros))
        (recur contador-pares (rest numeros) )))))
;
;
;
(contar-pares [1 2 3 4 5 6])
;;;; => 3

;;
(contar-pares [1 3 5 7])
;;;; => 0
;;
(contar-pares [2 4 6])