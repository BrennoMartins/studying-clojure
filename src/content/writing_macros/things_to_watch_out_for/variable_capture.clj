(ns content.writing-macros.things-to-watch-out-for.variable-capture)

;Imagine que criamos uma macro que recebe uma expressão e executa essa expressão dentro de um let.
;Parece simples, certo?
;Vamos utilizar:
;(let [resultado 100]
;  (minha-macro resultado))

;Qual seria o resultado esperado?
;Talvez você espere 100, porque a variável externa possui esse valor.

;Mas o resultado será 10.
;Por que isso acontece?
;Vamos observar a expansão da macro:
;(let [resultado 100]
;  (let [resultado 10]
;    resultado))
;A variável resultado criada pela macro interfere na resolução do símbolo resultado da expressão recebida.
;Isso é chamado de Variable Capture.

;A macro introduziu um nome que acabou capturando uma referência que deveria apontar para outro binding.
;Como resolver?
;Usando gensym.

(defmacro minha-macro [expressao]
  (let [nome-unico (gensym "resultado")]
    `(let [~nome-unico 10]
       ~expressao)))

;Agora o código gerado utiliza um símbolo único, por exemplo:
;(let [resultado1234 10]
;  resultado)

;O símbolo resultado continua apontando para a variável externa.
;Resultado:
;100

;Uma alternativa mais simples: auto-gensym
;O syntax quote possui um recurso que gera símbolos únicos automaticamente:
(defmacro minha-macro [expressao]
  `(let [resultado# 10]
     ~expressao))
;O # depois do nome faz o Clojure gerar um símbolo único.
;É uma solução bastante conveniente para evitar captura acidental de variáveis introduzidas pela macro.
