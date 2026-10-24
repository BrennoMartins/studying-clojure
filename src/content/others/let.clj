(ns content.others.let)


;IF-LET
;Agora vamos aprender uma forma muito útil de combinar uma condição com um binding.
;Imagine que o LifeOS recebe um mapa de usuário:
(def usuario {:nome "Brenno"})

(let [nome (:nome usuario)]
  (if nome
    (str "Olá, " nome "!")
    "Nome não informado"))

(defn saudar-usuario [usuario]
  (if-let [nome (:nome usuario)]
    (str "Olá, " nome "!")
    "Nome não informado"))

(saudar-usuario {:nome "Brenno"})
;; => "Olá, Brenno!"
;
(saudar-usuario {})
;; => "Nome não informado"

;(if-let [nome expressao]
;  resultado-se-verdadeiro
;  resultado-se-falso)

;Vamos fazer mais um exercício parecido para fixar. Se você tiver um mapa {:saldo 2500}, como escreveria um if-let que retorna "Saldo: 2500"
; quando existe saldo e "Saldo não informado" quando ele está ausente? Tente apenas escrever a expressão if-let, sem a função externa.

(defn mostra-saldo [mapa-saldo]
  (if-let [saldo (:saldo mapa-saldo)]
    (str "Saldo: " saldo)
    "Saldo não informado"))

(mostra-saldo {:saldo 0})

;WHEN-LET
;Implemente uma função chamada mensagem-aporte usando when-let.
;Ela deve receber um mapa como {:valor 500} e retornar "Aporte recebido: 500" quando :valor estiver preenchido. Se o valor estiver ausente, deve retornar nil.
;Tente escrever a função usando o padrão do exemplo acima.

(defn mensagem-aporte
  [aporte]
  (when-let [aporte-recebido (:valor aporte)]
    (str "Aporte recebido: " aporte-recebido)))

(mensagem-aporte {:valor 500})

;IF-SOME
;O if-some verifica se o resultado é diferente de nil. Ao contrário do if-let, ele não considera false como ausência de valor.
(if-let [valor false]
  "Tem valor"
  "Sem valor")
;; => "Sem valor"

(if-some [valor false]
  "Tem valor"
  "Sem valor")
;; => "Tem valor"


(def configuracao
  {:notificacoes? false})

;Escreva uma função chamada mensagem-notificacoes que receba um mapa e use if-some para:
;- Retornar "Configuração: false" quando receber {:notificacoes? false}.
;- Retornar "Configuração: true" quando receber {:notificacoes? true}.
;- Retornar "Configuração não informada" quando a chave estiver ausente ou tiver valor nil.

(defn mensagem-notificacoes
  [configuracao]
  (if-some [tem-configuracao? (:notificacoes? configuracao)]
    (if tem-configuracao?
      "Configuração: true"
      "Configuração: false")
    "Configuração não informada"))

(mensagem-notificacoes {:notificacoes? true})
(mensagem-notificacoes {:notificacoes? false})
(mensagem-notificacoes {})



;Agora um último desafio para fechar essa parte: você consegue reescrever a função usando when-some,
; retornando "Configuração: false" ou "Configuração: true" quando o valor existir, e nil quando não existir?

(defn mensagem-notificacoes-when-some
  [configuracao]
  (when-some [tem-configuracao? (:notificacoes? configuracao)]
    (if tem-configuracao?
      "Configuração: true"
      "Configuração: false")))

(mensagem-notificacoes-when-some {:notificacoes? true})
(mensagem-notificacoes-when-some {:notificacoes? false})
(mensagem-notificacoes-when-some {})


;Minha recomendação: para o seu momento de estudo, lembre-se desta regra:
;- if-let: um binding para verificar um valor.
;- let: quantos bindings você precisar.
;- if: para avaliar uma condição lógica, como (and condicao1 condicao2).
;Se você precisa verificar se dois valores estão presentes, pode usar let para extrair ambos e depois um if para validar as condições.