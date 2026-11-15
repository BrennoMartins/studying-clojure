(ns content.writing-macros.brews-for-the-brave-and-true.if-validate)

;Agora chegamos à segunda parte de Brews for the Brave and True: if-valid.
;Aqui está o ponto em que os conceitos de macros começam a se conectar com algo bastante prático.

;Na seção anterior, criamos uma função validar que recebe dados e regras e retorna um mapa com os erros encontrados.
;Queremos agora escrever algo assim:
;(if-valid cliente validacoes-cliente erros
;          (println "Cliente válido!")
;          (println "Erros encontrados:" erros))

;Repare que erros é uma variável que queremos disponibilizar dentro dos blocos de código.

;1. Por que usar uma macro?
;Uma função comum não consegue criar uma variável local que fique disponível para o código que foi passado como argumento.
;Por exemplo, se tentássemos fazer isso com uma função:
;(defn if-valid [dados validacoes bloco]
;  (let [erros (validar dados validacoes)]
;    ...))
;O argumento bloco já teria sido avaliado antes de entrar na função. Portanto, não conseguiríamos fazer com que ele enxergasse uma variável local criada dentro dela.
;A macro resolve isso porque recebe as expressões sem avaliá-las e pode construir uma estrutura de código que cria a variável antes de executar os blocos.

;2. Construindo a macro
;Vamos implementar:
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
(defmacro if-valid
  [dados validacoes nome-erros bloco-sucesso bloco-erro]
  `(let [~nome-erros (validar ~dados ~validacoes)]
     (if (empty? ~nome-erros)
       ~bloco-sucesso
       ~bloco-erro)))

;Observe os elementos:
;- defmacro: define uma macro.
;- dados e validacoes: recebem as expressões que serão usadas na validação.
;- nome-erros: recebe o símbolo que será usado como nome da variável local.
;- bloco-sucesso e bloco-erro: recebem código que será executado posteriormente.
;- ~: insere cada expressão recebida na estrutura criada pela sintaxe quote.

;3. Utilizando a macro
;Considerando as funções e os dados da seção anterior:
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

(if-valid cliente
          validacoes-cliente
          erros
          (println "Cliente válido!")
          (println "Erros:" erros))

(def cliente-invalido
  {:nome ""
   :email "email-invalido"
   :idade 15})

(if-valid cliente-invalido
          validacoes-cliente
          erros
          (println "Cliente válido!")
          (println "Erros:" erros))

;Conceitualmente, a macro gera algo equivalente a:
;(let [erros (validar cliente validacoes-cliente)]
;  (if (empty? erros)
;    (println "Cliente válido!")
;    (println "Erros:" erros)))

;O código gerado é executado pelo avaliador normalmente.
;Se o cliente for válido, executamos o primeiro bloco. Caso contrário, executamos o segundo, que consegue acessar a variável erros.

;4. Inspecionando a expansão
;Podemos visualizar o código produzido pela macro:
(macroexpand-1
  '(if-valid cliente
             validacoes-cliente
             erros
             (println "Cliente válido!")
             (println "Erros:" erros)))

;Isso ajuda bastante durante o desenvolvimento de macros: em vez de tentar entender apenas o comportamento final,
; podemos examinar o código que estamos gerando.

;O que aprendemos com if-valid?
;A macro não está fazendo a validação em si. Essa responsabilidade continua na função validar.
;A macro está organizando o fluxo de execução e permitindo que o código consumidor tenha acesso à variável de erros.

;Essa é uma boa divisão de responsabilidades:
;- validar: contém a lógica de validação.
;- if-valid: oferece uma sintaxe conveniente para trabalhar com o resultado.
;Essa é uma das grandes vantagens das macros em Clojure: criar novas abstrações de linguagem, não apenas encapsular operações.