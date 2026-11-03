(ns exercise.chapter-four)
;
;🧪 Revisão do capítulo 4 — Programação funcional em Clojure
;Exercício 1 — Análise de hábitos diários
;Dificuldade: ⭐⭐☆☆☆
;
;Você está criando uma aplicação para acompanhar seus hábitos.

;Considere os dados:
(def habitos
  [{:nome "Estudar Clojure" :categoria :estudo :minutos 60 :meta 45}
   {:nome "Academia" :categoria :saude :minutos 50 :meta 60}
   {:nome "Estudar inglês" :categoria :estudo :minutos 30 :meta 30}
   {:nome "Leitura" :categoria :estudo :minutos 20 :meta 25}
   {:nome "Yoga" :categoria :saude :minutos 40 :meta 30}
   {:nome "Caminhada" :categoria :saude :minutos 25 :meta 30}
   {:nome "Estudar Datomic" :categoria :estudo :minutos 90 :meta 60}])
;
;Sua missão
;Crie uma função chamada analisar-habitos que receba essa coleção e retorne um mapa com três informações:
;1. :total-minutos-estudo: soma dos minutos de todos os hábitos da categoria :estudo.
;2. :habitos-que-bateram-meta: vetor contendo os hábitos que atingiram ou ultrapassaram suas metas, ordenados pelo maior tempo primeiro.
;3. :media-minutos-estudo: média de minutos dedicados aos hábitos da categoria :estudo.
;Para cada hábito no vetor, mantenha somente as chaves :nome, :minutos e :meta.
;Regras
;- Utilize filter, map, reduce e sort-by.
;- Utilize into para produzir o vetor.
;- Evite loop e recur.

(defn soma-habitos
  [habitos]
  (->> habitos
       (map :minutos)
       (reduce +)))

(defn bateram-meta
  [habitos]
  (->> habitos
       (filter #(>= (:minutos %) (:meta %)))
       (sort-by :minutos >)
       (map #(select-keys % [:nome :minutos :meta]))))


(defn analisar-habitos
  [habitos]
  (let [habitos-estudos
        (filter #(= :estudo (:categoria %)) habitos)

        total-minutos-estudo
        (soma-habitos habitos-estudos)]

    {:total-minutos-estudo
     total-minutos-estudo

     :habitos-que-bateram-meta
     (into [] (bateram-meta habitos))

     :media-minutos-estudo
     (when (seq habitos-estudos)
       (/ (double total-minutos-estudo)
          (count habitos-estudos)))}))

(analisar-habitos habitos)

;when	Executar uma expressão somente quando a condição for verdadeira
;seq	Verificar se uma coleção tem elementos, retornando nil se estiver vazia

;Dica: você pode criar funções auxiliares para deixar o código mais organizado.


;Exercício 2 — Relatório de investimentos
;Dificuldade: ⭐⭐⭐☆☆
;
;Imagine que você está desenvolvendo um módulo para analisar seus aportes financeiros.
;
;Considere:
(def aportes
  [{:ativo "Tesouro IPCA+" :tipo :renda-fixa :valor 1500}
   {:ativo "ETF internacional" :tipo :exterior :valor 2000}
   {:ativo "Bitcoin" :tipo :cripto :valor 500}
   {:ativo "CDB" :tipo :renda-fixa :valor 1000}
   {:ativo "ETF internacional" :tipo :exterior :valor 800}
   {:ativo "Bitcoin" :tipo :cripto :valor 300}
   {:ativo "LCI" :tipo :renda-fixa :valor 700}])
;
;Sua missão
;Crie uma função chamada analisar-aportes que retorne um mapa contendo:
;1. :total-aportado: soma de todos os aportes.
;2. :total-por-tipo: mapa com o total investido em cada tipo.
;3. :maior-aporte: mapa representando o maior aporte individual.
;4. :aportes-acima-de-800: vetor contendo somente os aportes estritamente maiores que 800, ordenados por valor decrescente.
;Regras
;- Utilize reduce para calcular :total-aportado.
;- Utilize reduce para construir :total-por-tipo.
;- Utilize apply com max para encontrar o maior valor individual.
;- Utilize filter, sort-by e into para produzir :aportes-acima-de-800.
;Dica: para encontrar o mapa correspondente ao maior aporte, você precisará combinar a identificação do maior valor com a busca do registro que contém esse valor.
;Pense também no que sua função deve fazer se receber uma coleção vazia. Não precisa implementar esse caso agora, mas considere o problema.



(defn soma-aportes
  [aportes]
  (->> aportes
       (map :valor)
       (reduce + 0)))

(defn max-aporte
  [aportes]
  (->> aportes
       (map :valor)
       (apply max)))

(defn encontar-maxaporte
  [aportes max-valor]
  (filter #(= max-valor (:valor %)) aportes))

(defn aportes-acima-800
  [aportes]
  (filter #(< 800 (:valor %)) aportes))

(defn total-por-tipo
  [resultado aporte]
  (update resultado (:tipo aporte) + (:valor aporte)))

;(defn acumular-por-tipo
;  [resultado aporte]
;  (update resultado
;          (:tipo aporte)
;          (fnil + 0)
;          (:valor aporte)))
;
;(defn totais-por-tipo
;  [aportes]
;  (reduce acumular-por-tipo {} aportes))


(defn analisar-aportes
  [aportes]
  {:total-aportado
   (soma-aportes aportes)

   :total-por-tipo
   (let [resultado (zipmap
                     (set (map :tipo aportes))
                     (repeat 0))]
   (reduce total-por-tipo resultado aportes))

   :maior-aporte
   (encontar-maxaporte aportes (max-aporte aportes))

   :aportes-acima-de-800
   (into [] (aportes-acima-800 aportes))})



(analisar-aportes aportes)
;Exercício 3 — Gerenciador de tarefas
;Dificuldade: ⭐⭐⭐☆☆
;
;Você está implementando um sistema de organização de tarefas.
;
;Considere:
(def tarefas
  [{:nome "Estudar Clojure" :prioridade 1 :minutos 60 :concluida false}
   {:nome "Responder e-mails" :prioridade 3 :minutos 20 :concluida false}
   {:nome "Treinar" :prioridade 2 :minutos 50 :concluida true}
   {:nome "Revisar código" :prioridade 1 :minutos 40 :concluida false}
   {:nome "Ler documentação" :prioridade 2 :minutos 30 :concluida false}
   {:nome "Organizar mesa" :prioridade 3 :minutos 15 :concluida true}
   {:nome "Estudar Datomic" :prioridade 1 :minutos 90 :concluida false}])

;Sua missão
;Crie uma função chamada selecionar-tarefas que receba dois argumentos:
;- A coleção de tarefas.
;- O tempo disponível em minutos.

(defn tarefas-concluida?
  [tarefa]
  (= (:concluida tarefa) true))

(defn prioridade-critica?
  [tarefa]
  (contains? #{1 2} (:prioridade tarefa)))

(defn tempo-valido?
  [tempo-disponivel tarefa]
  (<= (:minutos tarefa) tempo-disponivel))

(defn selecionar-tarefas
  [tarefas tempo-disponivel]
  (->> tarefas
       (filter (complement tarefas-concluida?))
       (filter prioridade-critica?)
       (filter (partial tempo-valido? tempo-disponivel))
       ;eh mesma coisa que escrever (fn [tarefa] (tempo-valido? tempo-disponivel tarefa))
       ;(filter #(tempo-valido? tempo-disponivel %))
       (sort-by (juxt :prioridade :minutos))
       (map #(select-keys % [:nome :prioridade :minutos]))
       (into [])))


(selecionar-tarefas tarefas 10000)

;Ela deverá retornar um vetor contendo as tarefas que:
;- Ainda não foram concluídas.
;- Possuem prioridade 1 ou 2.
;- Cabem no tempo disponível.
;Ordene o resultado por prioridade crescente e, dentro de cada prioridade, por duração crescente.
;
;Para cada tarefa, mantenha somente :nome, :prioridade e :minutos.
;Regras
;- Utilize filter, complement, sort-by e into.
;- Utilize and e or nas condições.
;- Não utilize loop ou recur.
;Dica: você já tem todas as ferramentas necessárias para resolver esse exercício sem criar uma função auxiliar para cada condição.


;Exercício 4 — Análise de resultados financeiros
;Dificuldade: ⭐⭐⭐⭐☆
;Agora vamos trabalhar com um cenário um pouco mais complexo.
;Considere:
(def movimentacoes
  [{:descricao "Salário" :tipo :receita :valor 8000}
   {:descricao "Aluguel" :tipo :despesa :valor 2000}
   {:descricao "Freelance" :tipo :receita :valor 1500}
   {:descricao "Mercado" :tipo :despesa :valor 750}
   {:descricao "Investimentos" :tipo :aporte :valor 2000}
   {:descricao "Academia" :tipo :despesa :valor 120}
   {:descricao "Bônus" :tipo :receita :valor 1000}
   {:descricao "Reserva de emergência" :tipo :aporte :valor 500}
   {:descricao "Restaurante" :tipo :despesa :valor 250}
   {:descricao "Freelance extra" :tipo :receita :valor 800}])

;Sua missão
;Crie uma função chamada gerar-relatorio que retorne um mapa com:
;1. :total-receitas: soma das movimentações do tipo :receita.
;2. :total-despesas: soma das movimentações do tipo :despesa.
;3. :total-aportes: soma das movimentações do tipo :aporte.
;4. :maiores-despesas: vetor com as três maiores despesas, ordenadas por valor decrescente. Mantenha somente :descricao e :valor.
;5. :saldo-operacional: total de receitas menos total de despesas.

(defn soma-movimentacao-por-tipo
  [tipo movimentacoes ]
  (->> movimentacoes
       (filter #(= tipo (:tipo %)))
       (map :valor)
       (reduce + 0)))

(def soma-receita
  (partial soma-movimentacao-por-tipo :receita))

(def soma-despesa
  (partial soma-movimentacao-por-tipo :despesa))

(def soma-aporte
  (partial soma-movimentacao-por-tipo :aporte))

(defn maiores-despesas
  [movimentacoes]
  (->> movimentacoes
       (filter #(= :despesa (:tipo %)))
       (sort-by :valor >)
       (take 3)
       (map #(select-keys % [:descricao :valor]))
       (into [])))

(defn gerar-relatorio
  [movimentacoes]
  (let [receitas (soma-receita movimentacoes)
        despesas (soma-despesa movimentacoes)]
    {:total-receitas receitas
     :total-despesas despesas
     :total-aportes (soma-aporte movimentacoes)
     :maiores-despesas (maiores-despesas movimentacoes)
     :saldo-operacional (- receitas despesas)}))

(gerar-relatorio movimentacoes)


;Importante: aportes não devem ser subtraídos no cálculo do saldo operacional deste exercício.
;Regras
;- Utilize filter, map, reduce, sort-by, take e into.
;- Utilize partial para criar uma função auxiliar que some os valores de uma coleção.
;- Reutilize essa função auxiliar nos cálculos dos totais.
;Dica: pense em como você poderia criar uma função que recebe uma coleção de movimentações e calcula a soma dos seus valores, sem precisar reescrever a mesma lógica três vezes.


;Exercício 5 — Painel de produtividade
;Dificuldade: ⭐⭐⭐⭐⭐
;Este é o desafio final. Aqui você precisará combinar praticamente todos os conceitos importantes do capítulo.
;Considere:
(def atividades
  [{:nome "Estudar Clojure" :categoria :estudo :minutos 120 :concluida true}
   {:nome "Academia" :categoria :saude :minutos 60 :concluida true}
   {:nome "Ler livro" :categoria :estudo :minutos 45 :concluida false}
   {:nome "Caminhada" :categoria :saude :minutos 30 :concluida true}
   {:nome "Estudar inglês" :categoria :estudo :minutos 90 :concluida true}
   {:nome "Jogar videogame" :categoria :lazer :minutos 120 :concluida true}
   {:nome "Yoga" :categoria :saude :minutos 40 :concluida false}
   {:nome "Estudar Datomic" :categoria :estudo :minutos 60 :concluida false}
   {:nome "Assistir série" :categoria :lazer :minutos 90 :concluida true}])
;
;Sua missão
;Crie uma função chamada gerar-painel que retorne um mapa contendo:
;1. :total-minutos-concluidos -> Soma dos minutos de todas as atividades concluídas.
;2. :total-por-categoria  -> Mapa contendo o total de minutos de atividades concluídas para cada categoria.
;3. :ranking-estudos -> Vetor com as três atividades da categoria :estudo com maior duração, independentemente de estarem concluídas ou não.  Cada elemento deve conter somente :nome e :minutos.
;4. :atividades-pendentes -> Vetor com as atividades não concluídas, ordenadas pela duração decrescente. Cada elemento deve conter somente :nome, :categoria e :minutos.

(defn esta-concluido?
  [atividade]
  (:concluida atividade))

(defn total-conluido
  [atividades]
  (->> atividades
       (filter esta-concluido?)
       (map :minutos)
       (apply + 0)))

(defn somar-por-categoria
  [resultado atividade]
  (update resultado (:categoria atividade) (fnil + 0) (:minutos atividade)))

(defn total-por-categoria
  [atividades]
  (reduce somar-por-categoria {} atividades))

(defn listar-por-categoria
  [categoria atividade]
  (= (:categoria atividade) categoria))

(def categoria-estudo?
  (partial listar-por-categoria :estudo))

(defn ranking-estudos
  [atividades]
  (->> atividades
       (filter categoria-estudo?)
       (sort-by :minutos >)
       (take 3)
       (map #(select-keys % [:nome :minutos]))
       (into [])))

(defn atividades-pendentes
  [atividades]
  (->> atividades
       (filter (complement esta-concluido?))
       (sort-by :minutos >)
       (map #(select-keys % [:nome :categoria :minutos]))
       (into [])))

(defn gerar-painel
  [atividades]
  {:total-minutos-concluidos (total-conluido atividades)
   :total-por-categoria      (total-por-categoria atividades)
   :ranking-estudos          (ranking-estudos atividades)
   :atividades-pendentes     (atividades-pendentes atividades)})

(gerar-painel atividades)

;Regras
;- Utilize filter, map, reduce, sort-by, take e into.
;- Utilize complement para identificar atividades não concluídas.
;- Utilize apply para calcular o maior tempo entre as atividades.
;- Utilize partial para criar pelo menos uma função auxiliar reutilizável.
;- Evite loop e recur.
;Atenção: o maior tempo entre as atividades deverá ser calculado usando apply, mas você precisará decidir como utilizar esse resultado no painel. Não é necessário adicionar uma chave extra ao mapa final; você pode usar o resultado para validar o ranking ou incorporá-lo a uma lógica auxiliar.
;Esse exercício é propositalmente mais aberto. Existem diferentes maneiras corretas de organizar a solução.