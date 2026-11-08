(ns content.functional-programming.peg-thing.peg-thing)

;criando tabuleiro de 15 posicoes
(defn tabuleiro
  []
  (loop [contador 0
         tabuleiro []]
    (if (< contador 15)
      (recur (inc contador)
             (conj tabuleiro (not= contador 0)))
      tabuleiro)))




;Um movimento válido exige:
;- Origem ocupada (true).
;- Intermediária ocupada (true).
;- Destino vazio (false).
;Quero que você crie uma função:
(defn movimento-valido?
  [tabuleiro origem intermediaria destino]
  (let [valor-origem (get tabuleiro origem)
        valor-intermediaria (get tabuleiro intermediaria)
        valor-destino (get tabuleiro destino)]
    (and valor-origem valor-intermediaria (not valor-destino))))

;Ela deverá retornar true ou false.
;
;Exemplo conceitual:
(movimento-valido? [true true false] 0 1 2)
;; => true

(movimento-valido? [true false false] 0 1 2)
;; => false


;Executar o movimento
;Agora vem uma parte interessante: já sabemos identificar se um movimento é válido. Precisamos efetivamente executá-lo.
;Imagine:
;[true true false]

;Movimento: origem 0, intermediária 1, destino 2.
;Resultado esperado:
;[false false true]
;

(defn executar-movimento
  [tabuleiro origem intermediaria destino]
  (if (movimento-valido? tabuleiro origem intermediaria destino)
    (-> tabuleiro
        (assoc origem false)
        (assoc intermediaria false)
        (assoc destino true))
    tabuleiro))

;Precisamos criar:
(executar-movimento [true false false] 0 1 2)
;
;
;Regras:
;- Utilize assoc.
;- Não altere o vetor original.
;- Origem passa para false.
;- Intermediária passa para false.
;- Destino passa para true.
;- Não precisa validar o movimento nesta função; essa responsabilidade já pertence à movimento-valido?.
;Uma dica: você pode encadear três operações de assoc utilizando ->.

(defn representar-posicao
  [status]
  (if status
    "●"
    "○"))

(defn representar-linha
  [tabuleiro]
  (clojure.string/join " " (map representar-posicao tabuleiro)))

(representar-posicao true)
(representar-posicao false)

(representar-linha [true false true])


;Próximo exercício: montar o tabuleiro triangular
;Agora temos um desafio um pouco maior.
;Crie uma função representar-tabuleiro que receba o vetor de 15 posições e produza:
;○
;●   ●
;●   ●   ●
;●   ●   ●   ●
;●   ●   ●   ●   ●

;(defn representar-tabuleiro
;  [tabuleiro]
;
;  (subvec tabuleiro 0))

;(defn tabuleiro-num
;  []
;  (loop [contador 0
;         tabuleiro []]
;    (if (< contador 15)
;      (recur (inc contador)
;             (conj tabuleiro contador))
;      tabuleiro)))
;
;(tabuleiro-num)

(defn representar-tabuleiro
  [tabuleiro]
  (let [linha-um (representar-linha (subvec tabuleiro 0 1))
        linha-dois (representar-linha (subvec tabuleiro 1 3))
        linha-tres (representar-linha (subvec tabuleiro 3 6))
        linha-quatro (representar-linha (subvec tabuleiro 6 10))
        linha-cinco (representar-linha (subvec tabuleiro 10 15))]
    (clojure.string/join "\n" [linha-um linha-dois linha-tres linha-quatro linha-cinco])))




;Requisitos:
;- Reutilize representar-linha.
;- Utilize subvec para separar as linhas do vetor.
;- Utilize clojure.string/join para juntar as linhas usando "\n".
;Dica: os intervalos de índices das cinco linhas são:
;Linha	Índices
;1	0 até 0
;2	1 até 2
;3	3 até 5
;4	6 até 9
;5	10 até 14
;
;Lembre-se: o índice final de subvec não é incluído.
;
;Tente implementar. Não precisa usar map obrigatoriamente neste exercício.


;2. Exercício
;Crie uma função chamada representar-tabuleiro-centralizado.
;
;Ela deve receber o vetor do tabuleiro e produzir a representação centralizada.
;

(defn representar-tabuleiro-centralizado
  [tabuleiro]
  (let [linha-um (representar-linha (subvec tabuleiro 0 1))
        linha-dois (representar-linha (subvec tabuleiro 1 3))
        linha-tres (representar-linha (subvec tabuleiro 3 6))
        linha-quatro (representar-linha (subvec tabuleiro 6 10))
        linha-cinco (representar-linha (subvec tabuleiro 10 15))]
    (clojure.string/join
      "\n"
      [(str "        " linha-um)
       (str "      " linha-dois)
       (str "    " linha-tres)
       (str "  " linha-quatro)
       linha-cinco])))

(representar-tabuleiro-centralizado (tabuleiro) )

;Regras:
;- Reaproveite representar-linha.
;- Use subvec para separar as cinco linhas.
;- Use str para adicionar os espaços à esquerda.
;- Use clojure.string/join "\n" para juntar tudo.
;Sugestão de espaçamento:
;Linha	Espaços à esquerda
;1	8
;2	6
;3	4
;4	2
;5	0
;
;Tente implementar aproveitando sua função anterior. Não vou entregar a solução ainda; quero ver como você organiza isso.