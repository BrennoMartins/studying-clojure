(ns content.data-structure.lists)

(def numeros-lista '(10 20 30 40))
(def numeros-vetor [10 20 30 40])
(type numeros-lista)
(type numeros-vetor)
(count numeros-lista)

;first retorna o primeiro elemento.
;rest retorna os elementos restantes, sem o primeiro.
(first numeros-lista)
(rest numeros-lista)

(first (rest numeros-lista))
(nth numeros-lista 2)

(cons 5 numeros-lista)
(conj numeros-lista 5)
(conj numeros-vetor 5)

;Porque conj adiciona um elemento na posição mais eficiente para a estrutura de dados:
;- Em listas, adiciona no início.
;- Em vetores, adiciona no final.

;Já cons cria uma sequência com o elemento no início, independentemente de você estar passando uma lista ou um vetor.

;(10 20 30)
'(10 20 30)
(list 10 20 30)

(def tarefas '(estudar treinar cozinhar))

(cons 'ler tarefas)
(conj tarefas 'ler)
(vec numeros-lista)
(list 10 20 30)
(conj '(10 20 30) 5)

;Escreva expressões para:
(def passos '(abrir-editor escrever-codigo executar-testes))

;1. Obter o primeiro passo.
(first passos)
;2. Obter os passos restantes.
(rest passos)
;3. Adicionar revisar-codigo ao início da lista.
(conj passos 'revisar-codigo)
;4. Verificar quantos passos existem.
(count passos)


;Crie uma expressão que:
;1. Adicione revisar-codigo no início.
;2. Remova o primeiro elemento do resultado.
;3. Retorne a quantidade de elementos restantes.
;Tente fazer isso combinando conj, rest e count, sem criar variáveis intermediárias.

(-> passos
    (conj 'revisar-codigo)
    (rest)
    (count))


;A diferença mais importante é a estrutura interna:
;- Vector: estrutura em árvore com acesso indexado eficiente. Consultar ou substituir um elemento por índice costuma ser muito rápido.
;- List: lista ligada, na qual cada elemento aponta para o restante. Acesso por índice exige percorrer os elementos anteriores, mas adicionar ao início é muito barato.
