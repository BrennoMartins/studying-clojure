(ns content.data-structure.vectors)

(def tarefas
  ["Estudar Clojure"
   "Treinar"
   "Estudar investimentos"])

(count tarefas)
(first tarefas)
(last tarefas)
(nth tarefas 1)
(get tarefas 3)

(conj tarefas "Ler um livro")

(def numeros [10 20 30])
(assoc numeros 1 25)

;conj: adiciona ao final do vetor.
;assoc: substitui em um índice existente ou adiciona no índice imediatamente após o último.
(conj numeros 40)
(assoc numeros 1 40)
(assoc numeros 3 40)



;Exercício 6 — Removendo elementos
;Clojure não possui uma função central chamada remove-at-index para vetores. Podemos resolver isso combinando funções.
;Usando subvec, crie uma expressão que remova o elemento "Treinar", preservando os outros elementos e a ordem.
;Dica: subvec recebe o vetor, um índice inicial e, opcionalmente, um índice final que não é incluído no resultado.

(def tarefas-2 ["Estudar" "Treinar" "Cozinhar" "Ler"])
(subvec tarefas-2 1 2) ;-> "Treinar"  pega a posicao 1 e termina na posicao 2
(subvec tarefas-2 2) ;-> pega so a posicao 2 pra frente


(concat (subvec tarefas-2 0 1) (subvec tarefas-2 2 4))
;Só falta um detalhe: concat retorna uma sequência, não necessariamente um vetor. Para obter explicitamente um vetor, você pode usar vec:
(vec (concat (subvec tarefas-2 0 1) (subvec tarefas-2 2 4)))


(def tarefas-hoje ["Estudar Clojure" "Treinar"])
(def tarefas-amanha ["Ler um livro" "Estudar investimentos"])

;Escreva uma expressão que produza um único vetor com todas as tarefas, mantendo primeiro as de hoje e depois as de amanhã.
;Depois, responda: qual é a diferença entre concat e conj nesse caso?
(concat tarefas-hoje tarefas-amanha)
(conj tarefas-hoje tarefas-amanha)

;Exercício 8 — Aplicação no LifeOS
;Imagine que o LifeOS mantém uma sequência de prioridades:
(def prioridades [:alta :media :baixa])
;1. Crie uma nova versão em que :urgente seja o primeiro elemento.
(assoc prioridades 0 :urgente)
;2. Crie outra versão em que :media seja substituída por :normal.
(assoc prioridades 1 :normal)
;3. Consulte a prioridade que está no índice 2.
(get prioridades 2)

(def tarefas-3
  ["Estudar Clojure"
   "Treinar"
   "Estudar investimentos"
   "Ler um livro"])

;1. Remova "Treinar".
(vec (concat (subvec tarefas-3 0 1) (subvec tarefas-3 2 4)))
;2. Substitua "Estudar investimentos" por "Revisar investimentos".
(assoc tarefas-3 1 "Revisar investimentos")
;3. Adicione "Praticar Clojure" ao final.
(conj tarefas-3 "Praticar Clojure")
;4. Retorne um vetor, sem alterar tarefas.
(vec tarefas-3)

(defn remover-indice [vetor indice]
  (vec
    (concat
      (subvec vetor 0 indice)
      (subvec vetor (inc indice)))))

(-> tarefas-3
    (remover-indice 1)
    (assoc  1 "Revisar investimentos")
    (conj "Praticar Clojure")
    (vec))