(ns content.data-structure.maps)

(def projeto
  {:nome "LifeOS"
   :status "em andamento"
   :prioridade 1})

(get projeto :nome)
(:nome projeto)
(projeto :nome)
;Um detalhe importante: essas três formas não são equivalentes em todos os cenários.
; Por exemplo, usar um mapa como função pode gerar um erro se o valor chamado não for um mapa,
; enquanto get é mais explícito sobre a operação de consulta.

(get projeto :descricao)
(:descricao projeto)
(get projeto :descricao "Sem descrição")

(keys projeto)
(vals projeto)
(count projeto)

(assoc projeto :responsavel "Brenno")
(assoc projeto :status "Concluido")
(dissoc projeto :prioridade)

(assoc (assoc (dissoc projeto :prioridade) :categoria "estudos" ) :status "Em andamento")
;;threading macro
(-> projeto
    (dissoc :prioridade)
    (assoc :categoria "estudos")
    (assoc :status "Em andamento"))



(def investimento
  {:ativo "IVVB11"
   :quantidade 10
   :preco-medio 350.50
   :categoria "ETF"})

;Exercício 9 — Consulta
;1. Consulte o ativo.
;2. Consulte a quantidade.
;3. Consulte uma chave inexistente, :corretora, usando "Não informada" como valor padrão.
(get investimento :ativo)
(:quantidade investimento)
(get investimento :corretora "Não informada")


;Exercício 10 — Atualização
;Crie uma nova versão do mapa em que:
;- A quantidade passe para 15.
;- A corretora seja "Avenue".
;O mapa original deve permanecer inalterado.
(-> investimento
    (assoc :quantidade 15)
    (assoc :corretora "Avenue"))


;Exercício 11 — Valor total
;Calcule o valor total da posição, multiplicando :quantidade por :preco-medio, sem escrever os números diretamente na expressão.
;Dica: você pode usar get ou a keyword como função.
(* (get investimento :quantidade) (get investimento :preco-medio))
;+ idiomatico
(* (:quantidade investimento)
   (:preco-medio investimento))

;Exercício 12 — Transformação completa
;Crie uma nova versão de investimento que:
;- Atualize a quantidade para 15.
;- Adicione :corretora com o valor "Avenue".
;- Remova :categoria.
(-> investimento
    (assoc :quantidade 15)
    (assoc :corretora "Avenue")
    (dissoc :categoria))


;Para guardar: assoc substitui; update transforma. E ambos retornam um novo mapa, sem alterar o original.
; Update espera uma funcao
(update projeto :prioridade + 1 1)
(assoc projeto :prioridade 3)
