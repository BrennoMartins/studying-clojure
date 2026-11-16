(ns exercise.chapter-eight)

;Exercício 1 — Fácil: dobrar-expressao
;Objetivo: praticar defmacro, syntax quote e unquote (~).

;Crie uma macro chamada dobrar-expressao que receba uma expressão e gere código para multiplicar o resultado dessa expressão por 2.
(defmacro dobrar-expressao
  [expressao]
  `(* 2 ~expressao))

(dobrar-expressao (+ 10 20))

;Exemplos esperados:
(dobrar-expressao 10)
;;; 20
;
(dobrar-expressao (+ 10 5))
;;; 30
;
(dobrar-expressao (* 3 4))
;;; 24

;Requisitos:
;- Use defmacro.
;- Use syntax quote.
;- Use unquote para inserir a expressão recebida.
;- Teste a expansão com macroexpand-1.

;Exercício 2 — Fácil: executar-com-log
;Objetivo: praticar &, ~@ e composição de expressões com do.
;Crie uma macro chamada executar-com-log que receba várias expressões e:
;1. Imprima "Iniciando execução...".
;2. Execute todas as expressões recebidas, na ordem.
;3. Imprima "Execução finalizada!".
(defmacro executar-com-log
  [& expressoes]
  `(do
    (println "Iniciando execução...")
    ~@expressoes
    (println "Execução finalizada!")))

;Exemplo de utilização:
(executar-com-log
  (println "Calculando...")
  (+ 10 20)
  (println "Cálculo concluído"))

;Resultado esperado no console:
;Iniciando execução...
;Calculando...
;Cálculo concluído
;Execução finalizada!
;
;Requisitos:
;- Receba uma quantidade variável de expressões.
;- Utilize ~@ para inserir as expressões individualmente.
;- Preserve a ordem de execução.

;Exercício 3 — Médio: quando-positivo
;Objetivo: evitar avaliação duplicada.
;Crie uma macro chamada quando-positivo que receba uma expressão numérica:
(defmacro quando-positivo [exp-num]
  `(let [result# ~exp-num]
    (if (pos? result#)
      result#
      0))
  )
;- Se o resultado for maior que zero, retorne esse resultado.
;- Caso contrário, retorne 0.
;Exemplos:
(macroexpand-1 '(quando-positivo 10))
;;; 10
;
(quando-positivo -5)
;;; 0
;
;(quando-positivo (+ 5 3))
;;; 8
;
;
;Agora vem a parte importante.
;
;Considere:
(def contador (atom 0))
;
(defn gerar-valor []
  (swap! contador inc)
  10)
;
;
;Ao executar:
(quando-positivo (gerar-valor))
;O contador deve ser incrementado uma única vez.
;
;Requisitos:
;- Utilize let na expressão gerada.
;- Utilize gensym ou auto-gensym (nome#) para evitar captura de variáveis.
;- Garanta que a expressão recebida seja avaliada apenas uma vez.


;Exercício 4 — Médio: com-resultado
;Objetivo: entender e evitar variable capture.
;Crie uma macro chamada com-resultado que receba uma expressão e um bloco de código.

(defn executa-chamada
  [])
(defmacro com-resultado
  [somar & expr]
  `(let [resultado# ~somar]
     (let [~'resultado resultado#]
       (do
         ~@expr))))

; (let [~'resultado resultado#] sobre isso
;Como essa expressão está dentro de um syntax quote, o Clojure transforma o símbolo resultado em algo equivalente a:
;exercise.chapter-eight/resultado
;Mas um let exige que o nome da variável local seja um símbolo simples, sem namespace.
;Por isso aparece o erro simple-symbol?.
;2. Como resolver?
;Precisamos dizer ao Clojure que queremos inserir o símbolo resultado literalmente, sem qualificá-lo.
;Para isso, usamos:
;~'resultado

;Observe a combinação:
;- 'resultado: cria o símbolo sem avaliá-lo.
;- ~: insere esse símbolo no código gerado.



;A macro deve:
;1. Avaliar a expressão recebida.
;2. Guardar o resultado em uma variável local.
;3. Executar o bloco, disponibilizando o resultado por meio de uma variável chamada resultado.
;Exemplo:
(com-resultado (+ 10 20)
               (println resultado)
               (* resultado 2))
;
;
;Resultado esperado:
;30
;60
;
;
;Agora considere esta situação:
(let [resultado 100]
  (com-resultado 50
                 (+ resultado 10)))
;O resultado esperado é 110, e não 60.

;Requisitos:
;- Utilize syntax quote e unquote.
;- Evite capturar a variável externa resultado.
;- Garanta que a expressão inicial seja avaliada uma única vez.
;- Permita múltiplas expressões no bloco.
;Dica: pense em como o gensym pode ajudar a criar uma variável interna que não entre em conflito com outras variáveis.

;Exercício 5 — Difícil: if-valid
;Objetivo: construir uma macro mais completa, reunindo os principais conceitos do capítulo.
;Vamos aproveitar a função validar que desenvolvemos na seção anterior.

;Considere estas regras:
(defn nome-valido? [nome]
                             (not (empty? nome)))

(defn email-valido? [email]
  (boolean (re-seq #"@" email)))

(def validacoes-cliente
  {:nome nome-valido?
   :email email-valido?})

;E estes dados:
(def cliente
  {:nome "Ana"
   :email "ana@email.com"})

;Sua missão é criar uma macro com esta interface:

(defn validar-dados
  [campo funcao dado resultado-acumulado]
  (if (funcao dado)
    resultado-acumulado
    (assoc resultado-acumulado campo "Valor Invalido")))

(validar-dados :nome nome-valido? "" {})



;(defmacro if-valid [cliente validacoes erros bloco-valido bloco-erros]
;
;
;
;  )
;
;
;
;(if-valid cliente validacoes-cliente erros
;          (println "Cliente válido!")
;          (println "Erros encontrados:" erros))
;;
;
;A macro deverá:
;1. Executar a função validar.
;2. Criar uma variável com o nome informado (erros, no exemplo).
;3. Se o mapa de erros estiver vazio, executar o bloco de sucesso.
;4. Caso contrário, executar o bloco de erro.
;5. Disponibilizar a variável de erros dentro do bloco correspondente.
;Requisitos técnicos:
;- Utilize defmacro.
;- Utilize syntax quote e unquote.
;- Utilize let na expressão gerada.
;- Utilize ~@ para permitir blocos com múltiplas expressões, se optar por receber os blocos como sequências.
;- Evite variável interna com risco de captura.
;- Garanta que os dados e as regras não sejam avaliados desnecessariamente mais de uma vez.
;Teste também com um cliente inválido:
;(def cliente-invalido
;  {:nome ""
;   :email "email-invalido"})
;
;
;O bloco de erro deve ser executado e apresentar os problemas encontrados.