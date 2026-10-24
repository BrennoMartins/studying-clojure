(ns content.functions.rest-params
  (:require [clojure.set :as set]
            [clojure.string :as str]))

;O rest param vira uma seq () quando recebido na variavel
;Exercício 1 — Gerador de etiquetas
;No LifeOS, você quer gerar uma string com várias etiquetas para um projeto.
;A função deve receber o nome do projeto como primeiro argumento e zero ou mais etiquetas adicionais.
;Exemplos:
(defn gerar-etiquetas
  [nome-etiqueta & etiquetas]
  (str "Projeto: " nome-etiqueta " | Etiquetas: "  (str/join ", " etiquetas)))


(gerar-etiquetas " LifeOS " :clojure :estudos :produtividade)
;;; => " Projeto: LifeOS | Etiquetas: :clojure, :estudos, :produtividade "
;
(gerar-etiquetas " LifeOS ")
;;; => " Projeto: LifeOS | Etiquetas: "


;Exercício 2 — Verificador de categorias permitidas
;Imagine que um projeto pode pertencer a uma ou mais categorias.
(def categorias-permitidas
  #{:estudos :financas :saude :trabalho})

;Crie uma função categorias-validas? que receba zero ou mais categorias e retorne true somente quando todas as categorias fornecidas forem permitidas.
(defn categorias-validas?
  [& categorias]
  (every? categorias-permitidas categorias))

;Exemplos:
(categorias-validas? :estudos :financas)

(categorias-validas? :estudos :inexistente)
;;; => false

(categorias-validas?)
;;; => true

;Exercício 3 — Montador de mensagem
;Você quer criar mensagens para notificar o usuário sobre diferentes eventos.
;A função montar-mensagem deve receber:
;- O destinatário como primeiro argumento.
;- Uma ou mais partes da mensagem como argumentos restantes.

(defn montar-mensagem
  [destinatario & mensagem]
  (str destinatario (str/join mensagem)))

  ;Exemplos:
(montar-mensagem " Brenno " " Meta atingida " " Parabéns! ")
;; => " Brenno: Meta atingida Parabéns! "

(montar-mensagem " Brenno " " Nova tarefa ")
;;; => " Brenno: Nova tarefa "

;Requisitos: use str e &. Não precisa adicionar pontuação automática ou validar os textos.
;Exercício 4 — Remover itens de uma lista de tarefas
;Crie uma função remover-tarefas que receba:
;- Um vector de tarefas como primeiro argumento.
;- Zero ou mais nomes de tarefas para remover.
(defn remover-tarefas
  [tarefas & tarefas-para-remover]
  (vec (set/difference tarefas tarefas-para-remover)))


(defn remover-tarefas
  [tarefas & tarefas-para-remover]
  (let [tarefas-removidas (set tarefas-para-remover)]
    (remove
      (fn [tarefa]
        (contains? tarefas-removidas tarefa))
      tarefas)))

;Exemplo:
(remover-tarefas
  [" Estudar Clojure " " Ler " " Treinar " " Ler "]
  " Ler " " Treinar ")
;; => [" Estudar Clojure "]


;Outro exemplo:
(remover-tarefas [" Estudar Clojure " " Ler "])
;; => [" Estudar Clojure " " Ler "]




;Requisitos:
;Esse exercício é mais desafiador porque você precisará pensar como comparar as tarefas com os valores recebidos. Não precisa resolver tudo em uma única expressão; pode criar uma função auxiliar se quiser.



;Exercício 5 — Resumo de despesas
;Imagine que você deseja calcular o total de despesas variáveis de um mês.
;
;Crie uma função resumo-despesas que receba:
;- Uma categoria como primeiro argumento.
;- Zero ou mais valores de despesas como argumentos restantes.
;Ela deve retornar um mapa neste formato:
;(resumo-despesas :alimentacao 50 30 20)
;;; => {:categoria :alimentacao
;;;     :quantidade 3
;;;     :total 100}
;
;
;Para nenhuma despesa:
;(resumo-despesas :transporte)
;;; => {:categoria :transporte
;;;     :quantidade 0
;;;     :total 0}
;
;
;Requisitos: use &, count, reduce ou outra abordagem que você conheça para calcular o total. Como ainda estamos estudando fundamentos de funções, pode usar apply + para somar os valores.