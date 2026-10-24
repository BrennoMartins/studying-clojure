(ns exercise.data-strcuture
  (:require [clojure.string :as str]
            [clojure.set :as set]))

;Nível 1 — Operações básicas em situações reais
;Exercício 1 — Controle de uma meta financeira
;O LifeOS precisa acompanhar uma meta para uma viagem.
(def meta-viagem
  {:nome "Viagem de férias"
   :objetivo 12000
   :saldo-atual 4500
   :categoria :financas
   :status :em-andamento})

;Resolva:
;1. Obtenha o nome da meta.
(get meta-viagem :nome)
;2. Calcule quanto falta para atingir o objetivo.
(- (:objetivo meta-viagem ) (:saldo-atual meta-viagem))
;3. Calcule o percentual já alcançado.
(* 100.00 (/ (:saldo-atual meta-viagem) (:objetivo meta-viagem)))
;4. Atualize o saldo para 6000, sem alterar o mapa original.
(assoc meta-viagem :saldo-atual 6000)
;5. Remova o campo :status do mapa atualizado.
(dissoc (assoc meta-viagem :saldo-atual 6000) :status)

;Exercício 2 — Cadastro de hábitos
;Você quer registrar os hábitos que pretende acompanhar diariamente.
(def habitos
  ["Ler 20 minutos"
   "Estudar Clojure"
   "Fazer exercícios"
   "Ler 20 minutos"])

;Resolva:
;1. Quantos registros existem no vector?
(count habitos)
;2. Crie um conjunto com os hábitos, eliminando duplicatas.
(set habitos)
;3. Adicione o hábito "Meditar" ao conjunto.
(conj (set habitos) "Meditar")
;4. Verifique se "Estudar Clojure" está no conjunto.
(contains? (set habitos)  "Estudar Clojure")

;Exercício 3 — Padronização de projetos
;O LifeOS recebe nomes de projetos que podem conter espaços extras e letras minúsculas.
(def projeto
  {:nome "  controle financeiro  "
   :categoria :financas
   :tags #{:dinheiro :planejamento}})

;Resolva:
;1. Produza o nome "CONTROLE FINANCEIRO", sem espaços extras.
(str/upper-case (str/trim  (:nome projeto)))
;2. Crie uma descrição no formato "Projeto: CONTROLE FINANCEIRO".
(str "Projeto: " (str/upper-case (str/trim  (:nome projeto))))
;3. Adicione a tag :investimentos sem perder as tags existentes.
(update projeto :tags conj :investimentos)

;;ou
(assoc projeto
  :tags (conj (:tags projeto) :investimentos))
;4. Crie um novo mapa com a categoria :estudos, preservando os demais campos.
(assoc projeto :categoria :estudos)

;Nível 2 — Combinando várias estruturas
;Exercício 4 — Planejamento semanal
;Você mantém uma lista ordenada de tarefas para a semana.
(def tarefas
  ["Estudar Clojure"
   "Revisar carteira"
   "Ler um livro"
   "Fazer exercícios"])

;1. Adicione "Planejar a semana" ao final.
(conj tarefas "Planejar a semana")
;2. Atualize "Revisar carteira" para "Revisar investimentos".
(assoc tarefas 1 "Revisar investimentos")
;3. Remova "Ler um livro" da lista usando uma função auxiliar sua, como fez no exercício anterior com subvec e concat.
(defn remover-indice [vetor indice]
  (vec
    (concat
      (subvec vetor 0 indice)
      (subvec vetor (inc indice)))))

(-> tarefas
    (remover-indice 0))

;4. Calcule quantas tarefas existem depois das alterações.
;Conceitos: vectors, índices, assoc, conj, subvec, concat e count.
;Exercício 5 — Categorias permitidas e projetos ativos
;O LifeOS permite algumas categorias, mas nem todas estão sendo usadas.
(def categorias-permitidas
  #{:estudos :financas :saude :trabalho :lazer})

(def categorias-em-uso
  #{:estudos :financas :lazer})

(def categorias-de-outro-projeto
  #{:saude :trabalho})

;Resolva:
;1. Descubra quais categorias permitidas ainda não estão em uso.
(set/difference categorias-permitidas categorias-em-uso)

;2. Descubra quais categorias estão em uso ou aparecem no outro projeto, sem duplicatas.
(set/union categorias-em-uso categorias-de-outro-projeto)

;3. Descubra quais categorias aparecem no outro projeto, mas não estão em uso.
(set/difference categorias-de-outro-projeto categorias-em-uso)

;4. Verifique se :trabalho está em uso.
(contains? categorias-em-uso :trabalho)

;5. Verifique se todas as categorias em uso são permitidas. Você pode comparar os conjuntos com set/difference.
(set/difference categorias-em-uso categorias-permitidas)


;Exercício 6 — Registro de investimentos
;Você está cadastrando uma posição na sua carteira.
(def investimento
  {:ativo "IVVB11"
   :quantidade 12
   :preco-medio 350.50
   :categoria :etf
   :corretora "Avenue"})

;Resolva:
;1. Calcule o valor de aquisição da posição (quantidade × preço médio).
(* (:quantidade investimento) (:preco-medio investimento))

;2. Atualize a quantidade para 15.
(assoc investimento :quantidade 15)
;3. Crie uma nova versão do mapa com a quantidade atualizada e a corretora "Outra corretora".
(-> investimento
    (assoc :quantidade 15)
    (assoc :corretora "Outra corretora"))

;4. Remova o campo :corretora da nova versão.
(-> investimento
    (assoc :quantidade 15)
    (assoc :corretora "Outra corretora")
    (dissoc :corretora))

;5. Produza uma string como "Ativo: IVVB11 | Categoria: ETF".
(str "Ativo: " (:ativo investimento) " | Categoria: " (str/upper-case (name (:categoria investimento))))

;Nível 3 — Pequenos problemas de aplicação
;Exercício 7 — Conclusão de tarefas
;O LifeOS precisa organizar tarefas e identificar quais já foram concluídas.
(def tarefas-projeto
  [{:nome "Modelar domínio"
    :status :concluida
    :categoria :estudos}
   {:nome "Criar API"
    :status :em-andamento
    :categoria :trabalho}
   {:nome "Escrever testes"
    :status :pendente
    :categoria :trabalho}
   {:nome "Revisar código"
    :status :concluida
    :categoria :estudos}])
;
;
;Resolva:
;1. Obtenha o nome da primeira tarefa.
(:nome (get tarefas-projeto 0))

;2. Obtenha o status da segunda tarefa.
(:status (get tarefas-projeto 1))

;3. Conte quantas tarefas existem no vector.
(count tarefas-projeto)

;4. Crie um novo mapa para a terceira tarefa, alterando o status para :em-andamento.
(-> (get tarefas-projeto 2)
    (assoc :status :em-andamento))

;5. Crie um conjunto contendo as categorias distintas de todas as tarefas. Você pode montar o conjunto manualmente a partir das categorias que identificou.
(set [:estudos :trabalho :trabalho :estudos])

;Exercício 8 — Histórico de movimentações financeiras
;Você quer registrar as movimentações de uma reserva financeira.
(def movimentacoes
  [{:tipo :aporte :valor 1000}
   {:tipo :aporte :valor 500}
   {:tipo :retirada :valor 200}
   {:tipo :aporte :valor 300}])


;Resolva:
;1. Obtenha o valor da primeira movimentação.
(:valor (get movimentacoes 1))
;2. Calcule o total dos três aportes.
(+ (:valor (get movimentacoes 0)) (:valor (get movimentacoes 1)) (:valor (get movimentacoes 3)))
;3. Calcule o total líquido movimentado, considerando as retiradas como valores negativos.
(- (+ (:valor (get movimentacoes 0)) (:valor (get movimentacoes 1)) (:valor (get movimentacoes 3))) (:valor (get movimentacoes 2)))
;4. Crie uma nova versão da segunda movimentação, alterando o valor para 700.
(assoc (get movimentacoes 1) :valor 700)
;5. Explique por que atualizar o mapa da segunda movimentação não modifica o vector original automaticamente.
;Pois cloujure eh imutavel

;Exercício 9 — Validação de um projeto
(def projeto-novo
  {:nome "  Aprender Datomic "
   :categoria :estudos
   :tags #{:clojure :banco-de-dados}
   :status :pendente})

(def categorias-validas
  #{:estudos :financas :saude :trabalho})

(def status-validos
  #{:pendente :em-andamento :concluido})

;Resolva:
;1. Verifique se a categoria do projeto é válida.
(not (empty? (set/intersection categorias-validas (set [(:categoria projeto-novo)]))))
;2. Verifique se o status é válido.
(not (empty? (set/intersection status-validos (set [(:status projeto-novo)]))))

(contains? categorias-validas (:categoria projeto-novo))
(contains? status-validos (:status projeto-novo))

;3. Crie uma versão do projeto com o nome "Aprender Datomic", sem espaços extras.
(assoc projeto-novo :nome (str/trim (:nome projeto-novo)))
;4. Adicione a tag :life-os.
(assoc projeto-novo :tags (set/union (:tags projeto-novo) #{:life-os}))
;5. Remova o status do mapa final.
(dissoc projeto-novo :status)
;Desafio extra: monte um mapa com :categoria-valida e :status-valido, contendo os resultados booleanos das validações.
;Exemplo de formato esperado:
;{:categoria-valida true
; :status-valido true}

(defn validando-campos-projeto
  [map]
  (let [categoria-valida? (not (empty? (set/intersection categorias-validas (set [(:categoria map)]))))
        status-valida? (not (empty? (set/intersection status-validos (set [(:status map)]))))]
  {:categoria-valida categoria-valida?
    :status-valido status-valida?}))

(validando-campos-projeto (assoc projeto-novo :categoria :sem))
(validando-campos-projeto projeto-novo)



;Exercício 10 — Resumo de um painel do LifeOS
;Este é o exercício final. Você precisa preparar um resumo dos seus projetos pessoais.
(def painel
  {:usuario "Brenno"
   :projetos
   [{:nome "LifeOS"
     :categoria :estudos
     :status :em-andamento}
    {:nome "Carteira de investimentos"
     :categoria :financas
     :status :em-andamento}
    {:nome "Rotina de exercícios"
     :categoria :saude
     :status :concluido}]
   :categorias-favoritas #{:estudos :financas}})

;Resolva:
;1. Obtenha o nome do usuário.
(:usuario painel)
;2. Conte quantos projetos existem.
(count (:projetos painel))
;3. Obtenha o nome do segundo projeto.
(:nome (get (:projetos painel) 1))
;4. Crie uma versão do segundo projeto com status :concluido, sem alterar o mapa original.
(assoc (get (:projetos painel) 1) :status :concluido)
;5. Adicione :saude às categorias favoritas.
(assoc painel :categorias-favoritas (set/union (:categorias-favoritas painel) #{:saude}))
;6. Verifique se :trabalho está entre as categorias favoritas.
(not (empty? (set/intersection (:categorias-favoritas painel) #{:trabalho})))
;7. Crie um mapa de resumo com esta estrutura:
;{:usuario "Brenno"
; :total-projetos 3
; :categorias #{:estudos :financas :saude}}
{:usuario (:usuario painel)
 :total-projetos (count (:projetos painel))
 :categorias (:categorias-favoritas (assoc painel :categorias-favoritas (set/union (:categorias-favoritas painel) #{:saude})))}
