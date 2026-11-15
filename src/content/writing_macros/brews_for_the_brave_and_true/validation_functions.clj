(ns content.writing-macros.brews-for-the-brave-and-true.validation-functions)

;1. Definindo os dados e as regras
;Vamos usar um exemplo próximo de um cadastro de cliente:
(def cliente
  {:nome "Brenno"
   :email "brenno@email.com"
   :idade 35})

;Agora, em vez de escrever uma função enorme que verifica todos os campos, podemos criar uma função para cada regra:
(defn nome-valido? [nome]
  (not (empty? nome)))

(defn email-valido? [email]
  (boolean (re-seq #"@" email)))

(defn idade-valida? [idade]
  (and (number? idade)
       (>= idade 18)))

;Cada função recebe um valor e responde se ele é válido.
;Observe que essas funções são puras: não alteram os dados, não acessam o banco e não dependem de estado externo.

;2. Organizando as validações
;Podemos reunir as funções em um mapa:
(def validacoes-cliente
  {:nome nome-valido?
   :email email-valido?
   :idade idade-valida?})

;Agora temos uma estrutura que associa cada campo à sua regra de validação.
;Isso é interessante porque podemos percorrer esse mapa sem precisar escrever uma condição específica para cada campo.

;3. Criando uma função genérica de validação
;Vamos construir uma função que recebe os dados e as regras:
(defn validar
  [dados validacoes]
  (reduce
    (fn [resultado [campo funcao-validacao]]
      (let [valor (get dados campo)]
        (if (funcao-validacao valor)
          resultado
          (assoc resultado campo "Valor inválido"))))
    {}
    validacoes))

;Executando:
(validar cliente validacoes-cliente)
;
;Resultado:
;{}

;Como todos os campos são válidos, o mapa de erros fica vazio.

;Se alterarmos o cliente:
(def cliente-invalido
  {:nome ""
   :email "email-invalido"
   :idade 15})

(validar cliente-invalido validacoes-cliente)
;A validação retorna:
;{:nome "Valor inválido"
; :email "Valor inválido"
; :idade "Valor inválido"}

;4. O que está acontecendo no reduce?
;Essa é a parte mais importante do exemplo:
;(fn [resultado [campo funcao-validacao]]
;  (let [valor (get dados campo)]
;    (if (funcao-validacao valor)
;      resultado
;      (assoc resultado campo "Valor inválido"))))

;O reduce percorre cada entrada do mapa validacoes-cliente.
;Em cada iteração:
;- campo recebe a chave, como :nome.
;- funcao-validacao recebe a função associada àquela chave.
;- get busca o valor correspondente nos dados.
;- A função de validação é executada sobre esse valor.
;- Se for inválido, assoc adiciona o erro ao acumulador.
;O acumulador começa como {} e vai recebendo os erros encontrados.

;Perceba a abstração: a função validar não sabe o que significa um nome válido, um e-mail válido ou uma idade válida. Ela apenas sabe executar funções de validação e reunir os erros.
;Isso é programação funcional combinada com funções como valores e estruturas de dados.