(ns content.core-functions-in-depth.programming-to-abstractions.treating.collections-as-seq)

;A grande ideia é que podemos escrever operações que dependem da abstração de sequência, sem precisar conhecer previamente o tipo exato da coleção.

;Considere os dados de hábitos do LifeOS:
(def habitos
  ["Leitura" "Exercício" "Meditação"])

(defn resumir-primeiro-habito
  [habitos]
  (str "Primeiro hábito: " (first habitos)))

;;Testando com Vectors
(resumir-primeiro-habito habitos)
;; => "Primeiro hábito: Leitura"

;;Testando com Listas
(resumir-primeiro-habito '("Leitura" "Exercício" "Meditação"))
;; => "Primeiro hábito: Leitura"

;A mesma função funciona com vetor e lista porque ambos permitem acessar o primeiro elemento por meio de first.
;Um detalhe: se a coleção estiver vazia, first retorna nil, e a string será "Primeiro hábito: ".

;;Conceitos
;; first
;; cons
;; last
;; rest

;A função cons constrói uma sequência colocando um elemento no início de outra sequência.
(cons "Leitura" '("Exercício" "Meditação"))
;; => ("Leitura" "Exercício" "Meditação")

(cons "Leitura"  ["Exercício" "Meditação"])
;; => ("Leitura" "Exercício" "Meditação")

;Compare com conj, que você já conhece:
(conj ["Exercício" "Meditação"] "Leitura")
;; => ["Exercício" "Meditação" "Leitura"]

(conj '("Exercício" "Meditação") "Leitura")
;; => ("Leitura" "Exercício" "Meditação")

;A diferença é que conj adiciona um elemento na posição mais eficiente para a coleção, enquanto cons sempre coloca o elemento no início da sequência resultante.
;conj em vetores, coloca em ultimo
;conj em listas, coloca em primeiro
;cons sempre coloca na primeira posicao

;Implemente adicionar-habito-no-inicio, que recebe um hábito e uma coleção de hábitos e retorna uma sequência com o novo hábito na primeira posição.
(defn adicionar-habito-no-inicio
  [habito habitos]
  (cons habito habitos ))

(adicionar-habito-no-inicio
  "Alongamento"
  '("Leitura" "Exercício"))
;; => ("Alongamento" "Leitura" "Exercício")

;Uma observação: cons retorna uma sequência, não necessariamente um vetor. Isso é importante quando a função faz parte de um sistema maior e o tipo da coleção retornada importa.


;Abstração por indireção (Abstraction Through Indirection) significa acessar ou manipular alguma coisa por meio de uma interface comum, sem precisar conhecer os detalhes de como ela funciona internamente.

;Implemente resumir-ultimo-habito, que recebe uma coleção de hábitos e retorna uma string como:


;; => "Último hábito: Meditação"
(defn resumir-ultimo-habito
  [habitos]
  (str "Último hábito: " (last habitos)))

(resumir-ultimo-habito ["Leitura" "Exercício" "Meditação"])
