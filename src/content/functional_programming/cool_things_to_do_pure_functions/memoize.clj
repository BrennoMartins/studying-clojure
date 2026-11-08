(ns content.functional-programming.cool-things-to-do-pure-functions.memoize
  (:import (java.time LocalDateTime)))

;1. O problema: cálculos repetidos
;Imagine que temos uma função que calcula Fibonacci:
(defn fibonacci [n]
  (if (<= n 1)
    n
    (+ (fibonacci (- n 1))
       (fibonacci (- n 2)))))

;Se executarmos:
(fibonacci 5)
;;; => 5
;O resultado está correto.
;Mas existe um problema: a função calcula os mesmos valores várias vezes.

;Veja uma representação simplificada:
;Árvore de chamadas — Fibonacci(5)
;fib(5)
;fib(4)
;fib(3)
;fib(2)
;fib(3)
;fib(2)
;fib(1)

;fib(3) e fib(2) são calculados repetidamente.
;Quanto maior o número, mais cálculos repetidos acontecem.

;2. O que é memoization?
;Memoization é uma técnica que armazena o resultado de uma função para reutilizá-lo quando a mesma entrada aparecer novamente.

;Em Clojure, temos a função memoize.

;Veja:
;(def fibonacci-memo
;  (memoize fibonacci))

;Agora podemos executar:
;(fibonacci-memo 5)
;; => 5

;(fibonacci-memo 5)
;; => 5

;Na primeira execução, o resultado é calculado e armazenado.
;Na segunda, o resultado armazenado pode ser reutilizado, sem executar novamente o cálculo original.
;Como funciona o memoize?

;Entrada: 5
;Existe resultado armazenado para 5?
;Sim -> Retorna resultado armazenado
;Não -> Executa a função e armazena o resultado

;3. Uma característica importante
;O memoize não altera a função original. Ele retorna uma nova função que mantém um cache dos resultados.

;Por exemplo:
(defn calcular-desconto [preco percentual]
  (* preco (- 1 percentual)))
;
(def calcular-desconto-memo
  (memoize calcular-desconto))
;
;
;Agora:
(calcular-desconto-memo 100 0.10)
;;; => 90.0

;Se chamarmos novamente com os mesmos argumentos, o resultado pode ser recuperado do cache.
;Observe que o cache considera os argumentos da chamada. Portanto, (100 0.10) e (100 0.20) são entradas diferentes.

;4. Quando utilizar?
;O memoize pode ser útil quando:
;- Uma função realiza cálculos custosos.
;- As mesmas entradas aparecem repetidamente.
;- A função é pura ou, pelo menos, produz resultados estáveis para os mesmos argumentos.
;- O resultado pode ser mantido em memória sem problemas.
;Não é adequado para toda função.

;Por exemplo, imagine:
(defn horario-atual []
  (LocalDateTime/now))

;Não devemos aplicar memoize aqui esperando receber sempre o horário atual. Depois da primeira chamada, o resultado poderá ficar armazenado e ser reutilizado.
;Também existe um cuidado: o cache do memoize cresce conforme novas entradas são armazenadas. Para funções com muitas entradas diferentes, isso pode consumir bastante memória.

;5. Exercício prático
;Vamos começar com algo simples para observar o comportamento.

;Crie uma função chamada calcular-quadrado:
(defn calcular-quadrado [numero]
  (* numero numero))

;Depois crie uma versão utilizando memoize:
(def calcular-quadrado-memo
  (memoize calcular-quadrado))

;Teste:
(calcular-quadrado-memo 10)
;;; => 100
;
(calcular-quadrado-memo 10)
;;; => 100
;
(calcular-quadrado-memo 5)
;;; => 25
;

;Sua tarefa: implemente a versão com memoize e me explique, com suas palavras, qual é a diferença entre calcular-quadrado e calcular-quadrado-memo.
;Depois faremos um exercício um pouco mais interessante com Fibonacci, comparando o comportamento das duas versões.

;Fibonacci + memoize
;1. Primeiro, a versão tradicional
(defn fibonacci [n]
  (if (<= n 1)
    n
    (+ (fibonacci (- n 1))
       (fibonacci (- n 2)))))

;Essa função calcula Fibonacci recursivamente.
;Por exemplo:
;(fibonacci 5)
;;; => 5

;O problema é que existem cálculos repetidos.
;2. Aplicando memoize diretamente
;Podemos fazer:
(def fibonacci-memo
  (memoize fibonacci))
;
;
;E executar:
(fibonacci-memo 5)
;;; => 5

;Mas existe uma pegadinha!
;As chamadas recursivas internas continuam utilizando fibonacci, e não fibonacci-memo.
;
;Veja a diferença:
(def fibonacci-memo
  (memoize fibonacci))

;A função memoizada é uma nova função que envolve a original. A função original não foi alterada.
;Assim, quando fibonacci chama a si própria, ela não consulta o cache.

;3. Como fazer a recursão utilizar o cache?
;Uma maneira é criar uma referência para a função memoizada e utilizá-la nas chamadas recursivas.
(def fibonacci-memo
  (memoize
    (fn [n]
      (if (<= n 1)
        n
        (+ (fibonacci-memo (- n 1))
           (fibonacci-memo (- n 2)))))))

;Agora acontece algo diferente:
;1. A chamada procura o resultado no cache.
;2. Se encontrar, retorna imediatamente.
;3. Se não encontrar, executa a função.
;4. Durante a recursão, as próximas chamadas também passam pela função memoizada.


;4. Vamos praticar
;Quero que você implemente as duas versões:
;Versão A — Fibonacci tradicional
;Sem memoize.

;Versão B — Fibonacci memoizado
;Utilizando memoize de maneira que as chamadas recursivas internas também consultem o cache.
(defn fibonacci [n]
  (if (<= n 1)
    n
    (+ (fibonacci (- n 1))
       (fibonacci (- n 2)))))
;Teste com:
(fibonacci 10)
;;; => 55


(def fibonacci-memo
  (memoize
    (fn [n]
      (if (<= n 1)
        n
        (+ (fibonacci-memo (- n 1))
           (fibonacci-memo (- n 2)))))))
;
(fibonacci-memo 10)
;;; => 55
;
;
;E também com 20 e 30.
;
;Uma observação: na versão memoizada, a declaração precisa conseguir referenciar fibonacci-memo dentro da própria função. Em Clojure, declare pode ajudar nesse caso.
;Tente implementar sozinho. Quero revisar especialmente como você resolveu essa referência recursiva.

(time (fibonacci 30))

(time (fibonacci-memo 30))

(time (fibonacci 35))

(time (fibonacci-memo 35))
