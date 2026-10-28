(ns content.core-functions-in-depth.lazy-seqs.lazy)


;1. O que é uma Lazy Sequence?
;Até agora, trabalhamos com funções como map, filter e take para transformar e selecionar elementos de coleções.
;Mas existe uma característica importante dessas funções: elas podem produzir resultados sob demanda, em vez de calcular todos os elementos imediatamente.
;Esse comportamento é chamado de lazy evaluation (avaliação preguiçosa).
;Imagine que você tenha uma coleção com um milhão de números e queira apenas os três primeiros números multiplicados por dois.
;Uma abordagem que calcula tudo antes de selecionar os três primeiros faria trabalho desnecessário.
;Com sequências preguiçosas, o Clojure pode calcular somente os elementos necessários para produzir o resultado solicitado.
;
;Vamos entender isso na prática.
;2. Primeiro exemplo: map
;Considere esta expressão:
(map inc [1 2 3 4 5])
;O resultado é uma sequência com os valores incrementados:
;(2 3 4 5 6)

;O detalhe é que map não precisa calcular imediatamente todos os resultados. Ele pode retornar uma sequência preguiçosa, que calcula seus elementos quando eles são solicitados. ;
;Vamos criar uma função para observar esse comportamento:
(defn incrementar-e-mostrar
  [numero]
  (println "Processando:" numero)
  (inc numero))
;
;Agora, execute:
(def numeros-processados
  (map incrementar-e-mostrar [1 2 3 4 5]))

(incrementar-e-mostrar numeros-processados)
;Observe o que acontece: dependendo do ambiente e do REPL, você poderá ver mensagens de processamento, pois o REPL pode inspecionar ou realizar parte da sequência para exibir seu resultado.
;
;Para investigar melhor, tente:
(first numeros-processados)
;
;O primeiro elemento é 2.
;
;Em seguida, tente:
(second numeros-processados)
;
;O segundo elemento é 3.
;Você pode observar que os elementos de uma sequência preguiçosa são calculados conforme são necessários. Entretanto, o Clojure também pode armazenar em cache elementos já realizados, então não devemos interpretar cada chamada como garantia de uma nova execução da função.3

;3. Um exemplo mais interessante: range
;A função range gera uma sequência de números.
;
;Experimente:
(range 10)
;
;Resultado:
;(0 1 2 3 4 5 6 7 8 9)
;
;Também podemos especificar o início e o fim:
(range 5 10)
;
;Resultado:
;(5 6 7 8 9)
;
;E podemos definir o intervalo entre os números:
(range 0 20 5)
;
;Resultado:
;(0 5 10 15)
;
;Agora vem a parte interessante.
;E se não definirmos um limite final?
;Experimente:
;(range)
;
;Essa expressão produz uma sequência infinita de números inteiros começando em zero.
;
;Mas como isso é possível?
;
;O Clojure não precisa construir uma coleção infinita inteira na memória. Ele pode produzir os elementos conforme são solicitados.
;
;Por exemplo:
;(take 10 (range))
;
;Resultado:
;(0 1 2 3 4 5 6 7 8 9)
;
;Conseguimos obter os primeiros dez números de uma sequência infinita!
;
;Atenção: não tente realizar ou imprimir uma sequência infinita inteira sem limitar a quantidade de elementos. Use funções como take para restringir o resultado.
;4. Por que isso é útil?
;Imagine que você queira obter os primeiros cinco números pares.
;
;Podemos combinar as funções que já estudamos:
;(->> (range)
;     (filter even?)
;     (take 5))
;
;Resultado:
;(0 2 4 6 8)
;
;Observe o fluxo:
;1. range fornece uma sequência de números.
;2. filter seleciona os números pares.
;3. take limita o resultado aos cinco primeiros.
;Como a sequência é preguiçosa, o Clojure pode gerar e testar números conforme necessário, sem precisar criar previamente uma coleção infinita.
;
;Esse é um dos principais benefícios das lazy sequences: permitem trabalhar com sequências grandes ou infinitas de maneira eficiente, calculando os elementos conforme necessário.
;5. Resumo para suas anotações
;Conceito	Significado
;Lazy sequence	Sequência cujos elementos podem ser calculados sob demanda.
;map	Pode transformar elementos de forma preguiçosa.
;filter	Pode selecionar elementos de forma preguiçosa.
;range	Gera sequências numéricas, inclusive infinitas quando usado sem limites.
;take	Limita a quantidade de elementos solicitados.
;Realização (realization)	Processo de calcular os elementos de uma sequência preguiçosa.
;
;
;Uma distinção importante
;Preguiçoso não significa que o Clojure nunca calcula os elementos antecipadamente. Algumas operações podem realizar mais elementos do que você pediu, e elementos já calculados podem ser armazenados em cache.
;
;O ponto central é que a sequência não precisa ser inteiramente calculada antes de você começar a utilizá-la.

(class (map inc [1 2 3]))
(realized? (map inc [1 2 3]))

(class (reduce + [1 2 3]))
;| Função | Comportamento típico | Observação |
;|---|---|---|
;| `map` | Lazy | Transforma elementos conforme são necessários. |
;| `filter` | Lazy | Procura elementos que satisfaçam o predicado conforme são necessários. |
;| `remove` | Lazy | Produz elementos que não satisfazem o predicado. |
;| `take` | Lazy | Produz até a quantidade solicitada. |
;| `drop` | Lazy | Descarta elementos iniciais e produz os seguintes. |
;| `concat` | Lazy | Combina sequências sem precisar concatenar tudo imediatamente. |
;| `range` | Lazy | Pode representar uma sequência numérica infinita. |
;| `reduce` | Não lazy no processamento da redução | Percorre os elementos necessários para concluir o resultado. |
;| `sort` | Não lazy no processamento da ordenação | Precisa obter os elementos da coleção para ordená-los. |
;| `sort-by` | Não lazy no processamento da ordenação | Também precisa processar os elementos para determinar a ordem. |

;6. Uma regra prática para seus estudos
;Quando encontrar uma função nova, faça estas três perguntas:
;1. O que ela retorna? Um número, um mapa, um vetor, uma sequência?
;2. Quando o trabalho é executado? Durante a chamada ou quando os elementos são solicitados?
;3. Ela precisa consumir toda a coleção? Por exemplo, sort precisa conhecer todos os elementos para ordená-los, enquanto take pode limitar o consumo de uma sequência.
;Para as funções que você já estudou, guarde esta distinção:
;- map, filter, remove, take, drop e concat normalmente produzem sequências lazy.
;- reduce precisa consumir os elementos para concluir a redução.
;- sort e sort-by precisam processar os elementos para determinar a ordenação.
;- range pode representar uma sequência infinita.
;Minha recomendação: não tente decorar uma lista de todas as funções lazy do Clojure. Aprenda primeiro a reconhecer o comportamento das funções que está estudando e, quando tiver dúvida sobre uma função nova, consulte sua documentação e experimente-a no REPL.