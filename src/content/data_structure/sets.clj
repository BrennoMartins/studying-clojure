(ns content.data-structure.sets
  (:require [clojure.set :as set]))

(def linguagens #{:clojure :java :javascript})
(set [1 2 2 3 3 3])
;Essa diferença é importante: #{...} rejeita duplicatas na declaração literal; set elimina duplicatas de uma coleção de entrada.

(def prioridades #{:baixa :media :alta})
(contains? prioridades :alta)
(contains? prioridades :urgente)
(:alta prioridades)
(:urgente prioridades)


(def tecnologias #{:java :clojure})

(conj tecnologias :react)
(conj tecnologias :java)
(disj tecnologias :java)

[ :java :clojure :java ]
'( :java :clojure :java )
#{ :java :clojure }

(def linguagens-a #{:java :clojure})
(def linguagens-b #{:clojure :javascript})
(set/union linguagens-a linguagens-b)
(set/intersection linguagens-a linguagens-b)
(set/difference linguagens-a linguagens-b)

(def categorias-permitidas #{:estudos :financas :saude})
(def categorias-projeto #{:estudos :financas})

;Escreva expressões para:
;1. Verificar se :estudos está entre as categorias permitidas.
(contains? categorias-permitidas :estudos)
;2. Adicionar :trabalho a categorias-projeto.
(conj categorias-projeto :trabalho)
;3. Descobrir quais categorias permitidas ainda não estão em categorias-projeto.
(set/intersection categorias-projeto categorias-permitidas)


(def categorias-disponiveis
  #{:estudos :financas :saude :trabalho})

(def categorias-ativas
  #{:estudos :financas})
;Escreva expressões para:
;1. Encontrar todas as categorias disponíveis e ativas, sem duplicatas.
(set/union categorias-disponiveis categorias-ativas)
;2. Encontrar as categorias disponíveis que ainda não estão ativas.
(set/difference categorias-disponiveis categorias-ativas)
;3. Ativar a categoria :saude, sem alterar categorias-ativas.
(conj categorias-ativas :saude)
;4. Verificar se :trabalho está ativa no conjunto original.
(contains? categorias-ativas :trabalho)


;Diferenca entre every? e some
;every? verifica se o resultado da função é verdadeiro para cada elemento da sequência. Como uma keyword encontrada no conjunto é truthy e nil é falsey, a validação funciona.
;recebe uma função como primeiro argumento e uma coleção como segundo

;Estrutura	Pode ser chamada como função?	Exemplo
;Set	Sim	(:java #{:java :clojure}) → :java
;Keyword	Sim	(:nome {:nome "Brenno"}) → "Brenno"
;Map	Sim	({:nome "Brenno"} :nome) → "Brenno"
;Vector	Não	[10 20 30] não é uma função
;List	Não	'(10 20 30) não é uma função
(def categorias-permitidas
  #{:estudos :financas :saude :trabalho})

(every? categorias-permitidas #{:estudos :financas})
(some  categorias-permitidas #{:estudos :financas})

;Expressão	Resultado	Por quê?
;(every? even? [2 4 6])	true	Todos são pares
;(every? even? [2 3 6])	false	Um elemento não é par
;(some even? [1 3 6 8])	true	Encontrou um par
;(some even? [1 3 5])	nil	Não encontrou nenhum par
;(some #{:a :b} [:x :b :c])	:b	Encontrou um elemento do set

;;SEQ
;seq é a abstração que permite trabalhar com os elementos de diferentes collections de maneira uniforme e sequencial,
; em vez de precisar criar uma função específica para cada tipo de collection.
