(ns advanced-topics.clojure-metaphysics.vars.dynamic-binding)

;1. O que é uma Var?
;Sempre que usamos def ou defn, estamos criando uma Var no namespace atual
;Por exemplo:
(def nome "Brenno")

(defn saudacao []
  (str "Olá, " nome))

;Nesse caso, nome é uma Var que aponta para o valor "Brenno".
;Podemos consultar seu valor normalmente:
;nome
;;; "Brenno"

;E também podemos acessar a própria Var usando #':
;#'nome
;;; #'user/nome

;A diferença é:
;- nome resolve a Var e retorna seu valor.
;- #'nome retorna a própria Var.
;Isso é importante porque uma Var pode ter seu valor raiz alterado, algo que veremos mais adiante.

;2. O que significa Dynamic Binding?
;Normalmente, uma Var possui um valor raiz (root binding), que é o valor associado a ela por meio de def.
;Mas o Clojure permite criar um valor temporário para uma Var durante a execução de determinado contexto.
;Imagine que temos uma aplicação com diferentes ambientes:
(def ambiente "desenvolvimento")
;Agora, queremos executar uma função temporariamente como se estivéssemos no ambiente de produção, sem alterar o valor global.
;Para isso, usamos o Dynamic Binding.
;Primeiro, precisamos declarar que a Var é dinâmica:
(def ^:dynamic *ambiente* "desenvolvimento")
;Observe duas coisas:
;- ^:dynamic informa que a Var permite binding dinâmico.
;- *ambiente* segue uma convenção de nomenclatura do Clojure para Vars dinâmicas.

;Agora podemos usar binding:
(binding [*ambiente* "produção"]
  (println *ambiente*))

;Resultado:
;produção

;E fora do binding:
(println *ambiente*)
;;; desenvolvimento

;O valor raiz não foi alterado
;O valor "produção" existiu apenas durante a execução daquele contexto de binding.

;3. Um exemplo mais próximo de uma aplicação
;Imagine que temos uma função responsável por registrar mensagens:
(def ^:dynamic *nivel-log* :info)
(defn registrar-log [mensagem]
  (println (str "[" *nivel-log* "] " mensagem)))

;Execução normal:
(registrar-log "Aplicação iniciada")
;;; [ :info ] Aplicação iniciada

;Podemos alterar temporariamente o nível:
(binding [*nivel-log* :debug]
  (registrar-log "Consultando banco de dados"))
;Resultado:
;[:debug] Consultando banco de dados
;Depois do binding, o valor volta a ser :info.

;Isso é interessante quando queremos configurar temporariamente o comportamento de funções sem precisar passar parâmetros adicionais por toda a cadeia de chamadas.
;
;4. Exercício prático
;Vamos trabalhar com um sistema simples de configuração.
;
;Comece com esta Var:
(def ^:dynamic *desconto* 0.10)

;Implemente uma função:
(defn calcular-preco [preco]
  (- preco (* preco *desconto*)))

;Ela deve calcular o preço final aplicando o desconto definido em *desconto*.
;Teste os seguintes cenários:

;Cenário 1 — Desconto padrão
(calcular-preco 100)
;;; 90.0

;Cenário 2 — Desconto temporário de 20%
(binding [*desconto* 0.20]
  (calcular-preco 100))
;;; 80.0

;Cenário 3 — Verificar se o valor original foi preservado
*desconto*
;;; 0.10
