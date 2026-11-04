(ns content.functional-programming.cool-things-to-do-pure-functions.comp
  (:require [clojure.math :as math]))


;Parte 1 — Composição de funções com comp
;1. O que é composição de funções?
;Imagine que temos duas funções:
(defn dobrar [numero]
  (* numero 2))

(defn incrementar [numero]
  (inc numero))


;Se quisermos dobrar um número e depois incrementar o resultado, podemos fazer:
;(incrementar (dobrar 10))
;; => 21
;Funciona, mas imagine uma cadeia com cinco ou seis funções. As chamadas começam a ficar aninhadas.
;É aí que entra o comp.

;2. Conhecendo o comp
(def operacao
  (comp incrementar dobrar))

;Agora:
(operacao 10)
;;; => 21


;O detalhe mais importante:
;comp executa as funções da direita para a esquerda.

;Portanto:
;(comp incrementar dobrar)
;Equivale a:
;(incrementar (dobrar 10))

;3. Composição com três funções
;Vamos aumentar um pouco:
(defn quadruplicar [numero]
  (* numero 4))

;Podemos compor:
(def operacao
  (comp quadruplicar incrementar dobrar))

;Execução:
(operacao 10)

;4. Aplicação prática no mercadinho
;Imagine que queremos calcular o preço final de um produto com desconto e depois arredondar para duas casas decimais.
(defn aplicar-desconto [preco]
  (* preco 0.90))

(defn arredondar-preco [preco]
  (/ (math/round (* preco 100.0)) 100.0))

(def calcular-preco-final
  (comp arredondar-preco aplicar-desconto))

;Agora:
(calcular-preco-final 25.99)
;;; => 23.39

;Perceba que criamos uma função nova combinando duas funções existentes, sem precisar reescrever suas regras.
;Uma observação: comp funciona especialmente bem quando as funções recebem um argumento e retornam valores compatíveis com a próxima função.

;A função mais à direita é a única que recebe os argumentos originais da composição. As demais recebem o resultado da função anterior.
;1. Exemplo com dois argumentos
;(defn somar [a b]
;  (+ a b))
;
;(defn dobrar [numero]
;  (* numero 2))
;
;(def operacao
;  (comp dobrar somar))
;
;
;Podemos executar:
;(operacao 10 5)
;;; => 30
;
;
;O fluxo é:
;1. somar recebe 10 e 5.
;2. somar retorna 15.
;3. dobrar recebe 15.
;4. Resultado: 30.
;2. E se as duas funções receberem dois argumentos?
;Por exemplo:
;(defn somar [a b]
;  (+ a b))
;
;(defn multiplicar [a b]
;  (* a b))
;
;
;Não podemos simplesmente fazer:
;(comp multiplicar somar)
;
;
;E esperar que multiplicar receba os dois argumentos originais. Ela receberá apenas o resultado de somar.
;
;Se precisar passar múltiplos argumentos para a função seguinte, você pode retornar uma coleção e usar apply em uma função intermediária, por exemplo.


;1. O problema
;Imagine estas funções:
(defn somar [a b]
  (+ a b))

(defn multiplicar [a b]
  (* a b))

;Queremos executar:
;1. Somar 10 + 5.
;2. Multiplicar o resultado por 3 e por 2.
;O resultado esperado é 90.
;
;Mas temos um problema: somar retorna apenas um número (15), enquanto multiplicar espera dois argumentos.
;2. Usando apply

;Podemos criar uma função intermediária:
(defn multiplicar-por-valores [resultado]
  (apply multiplicar [resultado 2]))

;Agora podemos compor:
(def operacao
  (comp multiplicar-por-valores somar))

;Executando:
(operacao 10 5)
;;; => 30

;5. Exercícios — sua vez
;Vamos praticar antes de avançar para memoize.
;Exercício 1 — Composição simples
;Considere:
(defn multiplicar-por-3 [numero]
  (* numero 3))
;
(defn subtrair-5 [numero]
  (- numero 5))
;
;
;Crie uma função chamada operacao utilizando comp, que primeiro multiplique por 3 e depois subtraia 5.
(def operacao
  (comp subtrair-5 multiplicar-por-3))

;Resultado esperado:
(operacao 10)
;;; => 25

;Considere:
(defn remover-espacos [texto]
  (clojure.string/trim texto))

(defn transformar-maiusculo [texto]
  (clojure.string/upper-case texto))

(defn adicionar-prefixo [texto]
  (str "PRODUTO: " texto))
;
;
;Crie uma função formatar-produto usando comp.
(def formatar-produto
  (comp adicionar-prefixo transformar-maiusculo remover-espacos))
;Resultado esperado:
(formatar-produto "  arroz integral  ")
;; => "PRODUTO: ARROZ INTEGRAL"


;Uma observação importante
;Perceba que você criou duas funções novas sem precisar implementar novamente nenhuma regra de negócio.
;
;Isso é uma das vantagens da composição: construir comportamentos mais complexos a partir de funções menores e reutilizáveis.