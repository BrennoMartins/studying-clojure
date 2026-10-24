(ns content.functions.destructuring)

(def meta-viagem
  {:nome "Férias"
   :objetivo 10000
   :saldo-atual 2500})

;Sem destructuring, você poderia acessar os valores assim:
(:nome meta-viagem)
;; => "Férias"

(:objetivo meta-viagem)
;; => 10000

;Agora queremos extrair os três valores usando let e destructuring.
;Sua missão: implemente esta expressão para que ela retorne o vetor ["Férias" 10000 2500].
(let [ nome (:nome meta-viagem)
      objetivo (:objetivo meta-viagem)
      saldo-atual (:saldo-atual meta-viagem)
      ]
  [nome objetivo saldo-atual])


;No seu código, você consulta o mapa três vezes. O destructuring permite extrair esses valores em uma única declaração.
;Experimente completar esta versão:
(let [{:keys [nome objetivo saldo-atual]} meta-viagem]
  [nome objetivo saldo-atual])

;Antes de executar, observe a estrutura:
;- {:keys [...]} indica que queremos extrair valores de um mapa usando keywords.
;- Os nomes dentro de [...] serão as variáveis criadas.
;- meta-viagem é o mapa de origem.

;Considere este mapa:
(def projeto
  {:nome "LifeOS"
   :status "Em andamento"})

(let [{:keys [nome status prioridade]} projeto]
  [nome status prioridade])
;["LifeOS" "Em andamento" nil]

;E se quisermos que um projeto sem prioridade definida receba automaticamente o valor "Normal"?
;Clojure permite especificar valores padrão no destructuring de mapas usando :or.
;Observe a estrutura:
(let [{:keys [nome status prioridade]
       :or {prioridade "Normal"}} projeto]
  [nome status prioridade])


;Agora vamos aplicar isso a uma função, que é uma das utilizações mais importantes de destructuring.
;Imagine que queremos criar uma função que gere uma mensagem de resumo de um projeto.
;Sem destructuring, poderíamos escrever:
(defn resumir-projeto [projeto]
  (str (:nome projeto) " - " (:status projeto)))

;Sua missão é reescrever essa função usando destructuring diretamente nos parâmetros.
(defn resumir-projeto
  [{:keys [nome status]}]
    (str nome " - " status))

(resumir-projeto projeto)

;O resultado espe rado é:
;;; => "LifeOS - Em andamento"

;Quando usar o Destructuring nos parametros x no Let
;A função usa apenas alguns campos do mapa	-> Destructuring nos parâmetros
;Precisa usar o mapa inteiro e também extrair alguns campos	-> Destructuring em let
;Precisa fazer várias etapas de preparação	-> let, conforme a necessidade
;A função recebe vários argumentos, incluindo um mapa	-> Depende de como os dados são utilizados
;Minha recomendação para você: prefira destructuring diretamente nos parâmetros quando a função trabalha claramente com determinados campos de um mapa.	-> Use let quando ele ajudar a organizar a lógica ou quando precisar continuar usando o mapa original.
;
;Uma observação: não existe uma regra rígida de estilo exigindo uma das opções. O importante é que o código fique claro para quem vai lê-lo.


;Crie uma função resumir-projeto que receba um mapa com :nome, :status e :prioridade.
;Requisitos:
;- Use destructuring nos parâmetros.
;- Se :prioridade não estiver definida, utilize "Normal".
;- Retorne uma string neste formato: "LifeOS - Em andamento - Prioridade: Normal".
;Teste também com um mapa que tenha :prioridade "Alta".

(defn resumir-projeto
  [{:keys [nome status prioridade] :or {prioridade "Normal"}}]
  (str nome " - " status " - Prioridade: " prioridade))

(resumir-projeto projeto)
(resumir-projeto {:nome "LifeOS"
                  :status "Em andamento"
                  :prioridade "Alta"})

;outra forma
(defn resumir-projeto-outro
  [{name :nome stats :status level :prioridade}]
  (str name " - " stats " - Prioridade: " level))
(resumir-projeto-outro (assoc projeto :prioridade "Alta"))

;[{name :nome stats :status level :prioridade}] -> Quando você quer escolher livremente os nomes das variáveis.
;[{:keys [nome status prioridade]}] -> Quando os nomes das variáveis correspondem aos nomes das keywords e você quer uma sintaxe mais concisa.

;Até aqui, usamos destructuring com mapas. Agora vamos experimentar com vetores.
;Imagine que você tenha esta estrutura representando uma tarefa:
(def tarefa ["Estudar Clojure" "Pendente" 3])

;Os elementos representam, nessa ordem:
;1. Nome da tarefa
;2. Status
;3. Prioridade numérica
;Sua missão: usando let e destructuring de vetor, extraia os três valores para as variáveis nome, status e prioridade, retornando:
(let [[nome status prioridade] tarefa]
  [nome status prioridade])
;["Estudar Clojure" "Pendente" 3]

;Às vezes, você não precisa de todos os valores de um vetor.
;Imagine:
(def tarefa ["Estudar Clojure" "Pendente" 3])

;Você quer extrair apenas o nome e a prioridade, ignorando o status.
;Como você escreveria o destructuring para obter o resultado abaixo?
(let [[nome _ prioridade] tarefa]
  [nome prioridade])
;["Estudar Clojure" 3]


;Imagine que você tenha uma função que recebe um vetor representando uma tarefa:
(def tarefa ["Estudar Clojure" "Pendente" 3])

;Implemente a função resumir-tarefa usando destructuring diretamente nos parâmetros, de modo que ela retorne uma string no formato:
;"Estudar Clojure - Prioridade: 3"
;Você pode ignorar o status, pois ele não será utilizado na mensagem.
(defn resumir-tarefa
  [[nome _ prioridade]]
  (str nome " - Prioridade: " prioridade))

(resumir-tarefa tarefa)