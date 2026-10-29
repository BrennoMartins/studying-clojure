(ns content.core-functions-in-depth.function-function.partial)

;2. partial — Criando funções parcialmente aplicadas
;Imagine que você frequentemente precise calcular o preço de um produto com um acréscimo de 10%.
;Uma função simples poderia ser:
(defn adicionar-percentual
  [valor percentual]
  (* valor (+ 1 (/ percentual 100))))
;
;Exemplos:
(adicionar-percentual 100 10)
;;; => 110.0
(adicionar-percentual 200 10)
;;; => 220.0

;Mas imagine que você queira aplicar sempre os mesmos 10%. Seria inconveniente repetir esse argumento em todas as chamadas.
;É aqui que entra o partial.
;Como funciona?
;O partial recebe uma função e alguns argumentos que serão fixados. O resultado é uma nova função, que poderá receber os argumentos restantes.
;Veja:
(def adicionar-10-por-cento
  (partial adicionar-percentual 10))
;Antes de continuar, observe a ordem dos parâmetros da função original: valor vem primeiro e percentual vem depois.
;Portanto, a definição acima fixa o valor em 10, não o percentual. Não é o que queremos!

;Vamos corrigir a função original para colocar o percentual primeiro:
(defn adicionar-percentual
  [percentual valor]
  (* valor (+ 1 (/ percentual 100))))

;Agora podemos criar a função especializada:
(def adicionar-10-por-cento
  (partial adicionar-percentual 10))

;E usá-la:
(adicionar-10-por-cento 100)
;;; => 110.0
;
(adicionar-10-por-cento 200)
;;; => 220.0
;
;O partial criou uma nova função que já sabe que o primeiro argumento será 10.
;
;É como se tivéssemos escrito:
(defn adicionar-10-por-cento
  [valor]
  (adicionar-percentual 10 valor))
;
;A diferença é que, com partial, você não precisa escrever essa função manualmente.
;Quando usar partial?
;É útil quando:
;- Você chama repetidamente uma função com alguns argumentos fixos.
;- Quer criar uma função especializada a partir de outra mais genérica.
;- Quer passar essa função especializada para map, filter ou outra função de ordem superior.
;Por exemplo:
(def multiplicar
  (partial * 10))
;
(multiplicar 5 10)
;;; => 50
;
;Como * aceita vários argumentos, o partial fixa o primeiro argumento em 10, permitindo receber os demais posteriormente.

;🧪 Exercícios para você
;Exercício 1 — Desconto fixo
;Considere:
(defn aplicar-desconto
  [percentual valor]
  (* valor (- 1 (/ percentual 100))))
;
;Crie uma função chamada desconto-de-15 utilizando partial, de modo que:
(def desconto-de-15
  (partial aplicar-desconto 15))

(desconto-de-15 200)
;;; => 170.0
;Regra: não use defn para criar desconto-de-15; use partial.

;Exercício 2 — Multiplicador
;Crie uma função chamada dobrar utilizando partial e a função *.
(def dobrar
  (partial * 2))
;Resultado esperado:
(dobrar 5)
;;; => 10
(dobrar 12)
;;; => 24

;Exercício 3 — Aplicando com map
;Considere:
(def tempos [10 20 30 40])

(vec (map dobrar tempos))
;Utilize partial e map para criar uma expressão que dobre todos os valores.
;
;Resultado esperado:
;[20 40 60 80]
;
;Tente resolver os três exercícios. Depois revisamos suas soluções e seguimos para complement, a última função desta seção.