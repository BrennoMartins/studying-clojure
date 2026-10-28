(ns exercise.seq-function)

;Exercício 1 — Análise de hábitos (Médio)
;Ela deverá retornar uma coleção contendo somente os hábitos que atingiram suas metas, ordenados pelo maior tempo dedicado.
;Cada elemento do resultado deve conter apenas:
;- :nome
;- :minutos
;- :meta
;- :excedente — quantos minutos ultrapassaram a meta.
;Exemplo da estrutura esperada:
;({:nome "Clojure"
;  :minutos 45
;  :meta 30
;  :excedente 15}
; ...)
;A ordem e a quantidade de elementos devem corresponder aos dados de entrada.

(def habitos
  [{:nome "Leitura" :minutos 30 :meta 20}
   {:nome "Academia" :minutos 50 :meta 60}
   {:nome "Clojure" :minutos 45 :meta 30}
   {:nome "Meditação" :minutos 10 :meta 10}
   {:nome "Caminhada" :minutos 25 :meta 30}
   {:nome "Inglês" :minutos 40 :meta 20}])


(defn habito-bateu-meta?
  [habito]
  (>= (:minutos habito) (:meta habito)))

(defn adicionar-excedente
  [habito]
  (assoc habito :excedente (- (:minutos habito) (:meta habito))))

(defn resultado
  [habitos-default]
  {:nome      (:nome habitos-default)
   :minutos   (:minutos habitos-default)
   :meta      (:meta habitos-default)
   :excedente (:excedente habitos-default)})

(defn analisar-habitos
  [habitos]
  (->> habitos
       (filter habito-bateu-meta?)
       (sort-by :minutos >)
       (map adicionar-excedente)
       (map resultado)))

;Maneiras de melhorar o map depois de outro map
;(map (fn [habito]
;       (resultado (adicionar-excedente habito))))

;(map #(resultado (adicionar-excedente %)))
(analisar-habitos habitos)
;



;Imagine que você está desenvolvendo seu sistema de controle financeiro e precisa analisar uma coleção de aportes.
(def aportes [1500 -200 800 2500 -100 1200 400 3000])

(def resultado {:saldo-final           0
                :quantidade-positivos  0
                :total-positivos       0
                :maior-aporte          0
                :aportes-acima-de-1000 []})


(defn verificar-maior-aporte
  [aportes]
  (loop [contador 0
         ultimo-valor-maior 0]
    (if (< contador (count aportes))
      (if (< ultimo-valor-maior (nth aportes contador))
        (recur (inc contador) (nth aportes contador))
        (recur (inc contador) ultimo-valor-maior))
      ultimo-valor-maior)))

(defn analisar-aportes
  [aportes]
  (let [mapa-resultado resultado]
    (-> mapa-resultado
        (assoc :saldo-final (reduce + aportes))
        (assoc :quantidade-positivos (count (filter pos? aportes)))
        (assoc :total-positivos (reduce + (filter pos? aportes)))
        (assoc :maior-aporte (verificar-maior-aporte aportes))
        (assoc :aportes-acima-de-1000 (filter #(< 1000 %) aportes))
        )))

(analisar-aportes aportes)

;;;;;Solucao mais idiomatica
(defn acumular-aporte
  [resultado aporte]
  (let [resultado-com-saldo
        (update resultado :saldo-final + aporte)

        resultado-com-positivos
        (if (pos? aporte)
          (-> resultado-com-saldo
              (update :quantidade-positivos inc)
              (update :total-positivos + aporte))
          resultado-com-saldo)

        maior-atual (:maior-aporte resultado-com-positivos)]
    (if (or (nil? maior-atual)
            (> aporte maior-atual))
      (assoc resultado-com-positivos :maior-aporte aporte)
      resultado-com-positivos)))

(defn analisar-aportes
  [aportes]
  (let [valores-iniciais
        {:saldo-final          0
         :quantidade-positivos 0
         :total-positivos      0
         :maior-aporte         nil}

        resultado
        (reduce acumular-aporte valores-iniciais aportes)]
    (assoc resultado
      :aportes-acima-de-1000
      (filter #(< 1000 %) aportes))))

(def aportes [1500 -200 800 2500 -100 1200 400 3000])

(analisar-aportes aportes)




;🥋 Exercício 3 — Priorização inteligente de tarefas
;Dificuldade: Média/Difícil

;A função deve retornar somente as tarefas que atendam simultaneamente aos seguintes critérios:
;1. A prioridade deve ser 1 ou 2.
;2. A duração deve ser menor ou igual ao tempo disponível.
;3. As tarefas devem ser ordenadas pela prioridade, da mais alta para a mais baixa — neste caso, prioridade 1 vem antes da 2.
;4. Em caso de empate na prioridade, as tarefas mais curtas devem aparecer primeiro.

;Resultado esperado
;({:nome "Ler documentação" :prioridade 2 :minutos 30}
; {:nome "Revisar código" :prioridade 1 :minutos 40})
;
;Correção: esse exemplo de resultado não está ordenado corretamente de acordo com os critérios! O resultado correto é:
;({:nome "Revisar código" :prioridade 1 :minutos 40}
; {:nome "Ler documentação" :prioridade 2 :minutos 30})
;
;A tarefa "Estudar Clojure" fica de fora porque exige 60 minutos, acima do limite de 45. "Treinar" também fica de fora porque exige 50 minutos. As tarefas de prioridade 3 são excluídas.
;📋 Requisitos
;- Use filter para selecionar as tarefas.
;- Use sort-by para ordenar o resultado.
;- Combine condições com and e or.
;- Você pode usar fn ou #() para criar funções anônimas.
;- Não use loop, recur ou funções que ainda não estudamos.
;Desafio extra
;Crie uma segunda função que receba o resultado de selecionar-tarefas e retorne somente os nomes das tarefas, preservando a ordem.
;
;Por exemplo:
;("Revisar código" "Ler documentação")

;Considere a seguinte coleção:
(def tarefas
  [{:nome "Estudar Clojure" :prioridade 1 :minutos 60}
   {:nome "Responder e-mails" :prioridade 3 :minutos 20}
   {:nome "Treinar" :prioridade 2 :minutos 50}
   {:nome "Revisar código" :prioridade 1 :minutos 40}
   {:nome "Ler documentação" :prioridade 2 :minutos 30}
   {:nome "Organizar mesa" :prioridade 1 :minutos 15}])
;
;🎯 Objetivo
;Implemente a função:

(defn selecionar-tarefas
  [tarefas tempo]
  (let [tarefas-prioritarias (filter #(or (= 1 (:prioridade %)) (= 2 (:prioridade %))) tarefas)
        tarefas-elegiveis (filter #(<= (:minutos %) tempo) tarefas-prioritarias)
        ordernar-tarefas (sort-by (fn [tarefa]
                                    [(:prioridade tarefa)
                                     (:minutos tarefa)]) tarefas-elegiveis)]
    ordernar-tarefas))

(selecionar-tarefas tarefas 45)
;O segundo argumento representa o tempo máximo disponível em minutos.


;Vamos para o Exercício 4 — Análise de produtividade. 🚀
;
;Agora vamos combinar map, filter, reduce, sort-by e take em um cenário mais próximo de uma aplicação real.
;🎯 Exercício 4 — Ranking de produtividade
;Imagine que você está desenvolvendo o LifeOS e quer analisar as atividades realizadas durante a semana.
;
;Temos os seguintes dados:
(def atividades
  [{:nome "Estudar Clojure" :categoria :estudo :minutos 120}
   {:nome "Academia" :categoria :saude :minutos 60}
   {:nome "Ler livro" :categoria :estudo :minutos 45}
   {:nome "Caminhada" :categoria :saude :minutos 30}
   {:nome "Estudar inglês" :categoria :estudo :minutos 90}
   {:nome "Jogar videogame" :categoria :lazer :minutos 120}
   {:nome "Yoga" :categoria :saude :minutos 40}
   {:nome "Estudar Datomic" :categoria :estudo :minutos 60}
   {:nome "Assistir série" :categoria :lazer :minutos 90}])
;


;(def saida-analisar-produtividade {:total-minutos-estudo        0
;                                   :ranking-estudos             nil
;                                   :total-minutos-por-categoria nil})
;
;(defn acumular-produtividade
;  [resultado atividade]
;  (let [minutos-por-categoria {}]
;    (println (:categoria atividade) )
;    (update resultado :total-minutos-estudo + (:minutos atividade))
;    (update resultado :ranking-estudos conj {:nome (:nome atividade) :minutos (:minutos atividade)})
;
;    (update (assoc minutos-por-categoria (:categoria atividade) 0) (:categoria atividade) + (:minutos atividade))
;    ;(update resultado :total-minutos-por-categoria conj minutos-por-categoria)
;    ))
;
;(defn analisar-produtividade
;  [atividades]
;  (let [ranking (sort-by :minutos > atividades)]
;    (reduce acumular-produtividade saida-analisar-produtividade atividades)
;
;
;    )
;
;  )

(defn ranking-estudos
  [atividades]
  (take 3 (sort-by :minutos > atividades)))

(defn remover-categoria
  [atividade]
  (dissoc atividade :categoria))

(defn minutos-por-categoria
  [map-categoria atividade]
  (update map-categoria (:categoria atividade) + (:minutos atividade)))

(defn analisar-produtividade
  [atividades]
  (let [mapa-categoria (zipmap (set (map :categoria atividades)) (repeat 0))]
    {:total-minutos-estudo        (reduce + (map :minutos atividades))
     :ranking-estudos             (ranking-estudos (map remover-categoria atividades))
     :total-minutos-por-categoria (reduce minutos-por-categoria mapa-categoria atividades)}
    ))

(analisar-produtividade atividades)

=
;{
; :total-minutos-estudo        120
; :ranking-estudos             ({:nome "Estudar Clojure" :minutos 120}
;                               {:nome "Estudar inglês" :minutos 90}
;                               {:nome "Estudar Datomic" :minutos 60})
; :total-minutos-por-categoria {:estudo 315
;                               :saude  130
;                               :lazer  210}}))



;Crie uma função chamada analisar-produtividade que receba a coleção de atividades e retorne um mapa com três informações:
;1. :total-minutos-estudo
;A soma dos minutos de todas as atividades da categoria :estudo.
;2. :ranking-estudos
;Uma lista com as 3 atividades de estudo de maior duração, ordenadas da maior para a menor.
;
;Cada atividade deve conter somente:
;- :nome
;- :minutos

;3. :total-minutos-por-categoria
;
;Um mapa com o total de minutos acumulados em cada categoria.
;
;Exemplo do formato esperado:
;{:total-minutos-estudo 315
;
; :ranking-estudos
; ({:nome "Estudar Clojure" :minutos 120}
;  {:nome "Estudar inglês" :minutos 90}
;  {:nome "Estudar Datomic" :minutos 60})
;
; :total-minutos-por-categoria
; {:estudo 315
;  :saude 130
;  :lazer 210}}
;
;🧰 Regras do exercício
;Tente resolver utilizando as funções que já estudamos:
;- filter
;- map
;- reduce
;- sort-by
;- take
;- assoc ou update
;- ->> e, se necessário, ->
;Não utilize loop ou recur.
;
;Você pode criar funções auxiliares para deixar o código mais organizado.
;💡 Dicas
;Dica 1: Para calcular o total de minutos de estudo, você pode filtrar as atividades e depois usar map para extrair os minutos antes de aplicar reduce.
;
;Dica 2: Para ordenar do maior para o menor, lembre-se de que sort-by aceita um comparador como >.
;
;Dica 3: Para acumular os minutos por categoria, pense em usar reduce com um mapa como acumulador. A função update pode ajudar bastante.


(defn minutos-por-categoria
  [map-categoria atividade]
  (update map-categoria
          (:categoria atividade)
          +
          (:minutos atividade)))


(defn total-minutos-estudo
  [atividades]
  (->> atividades
       (filter #(= :estudo (:categoria %)))
       (map :minutos)
       (reduce + 0)))


(defn ranking-estudos
  [atividades]
  (->> atividades
       (filter #(= :estudo (:categoria %)))
       (sort-by :minutos >)
       (take 3)
       (map #(select-keys % [:nome :minutos]))))


(defn minutos-por-categoria-total
  [atividades]
  (reduce minutos-por-categoria
          (zipmap (set (map :categoria atividades))
                  (repeat 0))
          atividades))


(defn analisar-produtividade
  [atividades]
  {:total-minutos-estudo
   (total-minutos-estudo atividades)

   :ranking-estudos
   (ranking-estudos atividades)

   :total-minutos-por-categoria
   (minutos-por-categoria-total atividades)})


;🚀 Exercício 5 — Relatório financeiro pessoal
;Vamos aproveitar o contexto do LifeOS e trabalhar com uma situação diferente das anteriores.
;
;Imagine que você tem uma coleção de movimentações financeiras:
;(def movimentacoes
;  [{:descricao "Salário" :tipo :receita :valor 8000}
;   {:descricao "Aluguel" :tipo :despesa :valor 2000}
;   {:descricao "Freelance" :tipo :receita :valor 1500}
;   {:descricao "Mercado" :tipo :despesa :valor 750}
;   {:descricao "Investimentos" :tipo :aporte :valor 2000}
;   {:descricao "Academia" :tipo :despesa :valor 120}
;   {:descricao "Bônus" :tipo :receita :valor 1000}
;   {:descricao "Reserva de emergência" :tipo :aporte :valor 500}
;   {:descricao "Restaurante" :tipo :despesa :valor 250}
;   {:descricao "Freelance extra" :tipo :receita :valor 800}])
;
;Seu objetivo
;Implemente a função gerar-relatorio-financeiro, que recebe a coleção e retorna um mapa com quatro informações:
;
;1. :saldo-receitas
;
;A soma de todas as receitas.
;
;2. :total-despesas
;
;A soma de todas as despesas.
;
;3. :total-aportes
;
;A soma de todos os aportes.
;
;4. :maiores-despesas
;
;As três maiores despesas, ordenadas do maior valor para o menor. Cada elemento deve conter somente :descricao e :valor.
;Resultado esperado
;{:saldo-receitas 11300
; :total-despesas 3120
; :total-aportes 2500
; :maiores-despesas
; ({:descricao "Aluguel" :valor 2000}
;  {:descricao "Mercado" :valor 750}
;  {:descricao "Restaurante" :valor 250})}
;
;Regras
;Tente resolver utilizando as funções que já estudamos:
;- filter
;- map
;- reduce
;- sort-by
;- take
;- select-keys (opcional; você pode reutilizar sua função de transformação de mapas)
;                - ->> e ->
;                Você pode criar funções auxiliares, como fizemos nos exercícios anteriores.
;
;                Não use loop ou recur.
;                Minha recomendação
;                Antes de começar a programar, pense em como resolver cada requisito separadamente:
;                - Como selecionar somente as receitas?
;                - Como extrair os valores e somá-los?
;                - Como repetir esse raciocínio para despesas e aportes?
;                - Como selecionar as despesas, ordená-las e obter somente as três maiores?
;                Depois, pense em como reunir os resultados em um único mapa.
;
;                Tente fazer sozinho primeiro! Não precisa acertar de primeira. Quando me enviar seu código, vou revisar seu raciocínio antes de sugerir melhorias.