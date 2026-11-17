(ns content.writing-macros.things-to-watch-out-for.double-evaluation)

;4.2 Double Evaluation — Avaliação duplicada
;Outro problema comum é avaliar uma expressão mais de uma vez.
;Imagine esta macro:
(defmacro verificar-valor [expressao]
  `(if (> ~expressao 0)
     ~expressao
     0))

;Utilização:
(verificar-valor (do
                   (println "Calculando...")
                   10))

;O resultado é 10, mas quantas vezes a expressão foi executada?
;Vamos olhar a expansão:
;(if (> (do
;         (println "Calculando...")
;         10)
;       0)
;  (do
;    (println "Calculando...")
;    10)
;  0)

;Observe que a expressão aparece duas vezes.
;Consequentemente, "Calculando..." será impresso duas vezes.

;Isso pode ser um problema sério quando a expressão contém efeitos colaterais, como:
;- Registrar uma venda.
;- Atualizar estoque.
;- Fazer uma chamada HTTP.
;- Gravar informações no banco de dados.

;Como resolver?
;A solução é avaliar a expressão uma única vez e guardar o resultado em uma variável local.
(defmacro verificar-valor [expressao]
  `(let [valor# ~expressao]
     (if (> valor# 0)
       valor#
       0)))

(verificar-valor (do
                   (println "Calculando...")
                   10))

;Agora a expansão será equivalente a:
;(let [valor1234
;      (do
;        (println "Calculando...")
;        10)]
;  (if (> valor1234 0)
;    valor1234
;    0))

;A expressão é executada somente uma vez.

;4.3 Comparando os dois problemas
;Problema	/ O que acontece	Solução
;Variable Capture / Um símbolo introduzido pela macro interfere em outro binding	gensym ou nome#
;Double Evaluation /	Uma expressão recebida é executada várias vezes	Avaliar uma vez e guardar em let

;Um princípio importante para escrever macros:
;Sempre que criar uma macro, pergunte:
;1. Estou introduzindo algum nome que pode conflitar com o código de quem vai utilizá-la?
;2. Estou avaliando alguma expressão recebida mais de uma vez?
;Essas duas verificações evitam muitos bugs difíceis de identificar.