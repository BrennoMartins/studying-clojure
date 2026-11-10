(ns content.clojure-alchemy-reading-evaluation-macros.evaluator.function-call)

;Vamos avançar. Agora chegamos a uma das partes mais importantes para entender como Clojure funciona: como o Evaluator executa listas.

;2. Como o Evaluator avalia listas
;Até aqui vimos que uma lista pode representar código:
(+ 10 20)

;Mas como o Evaluator interpreta essa estrutura?
;Quando encontra uma lista comum, ele segue, de forma simplificada, este processo:
;1. Avalia o primeiro elemento da lista.
;2. Avalia os argumentos restantes.
;3. Invoca o primeiro elemento, passando os argumentos avaliados.
;4. Retorna o resultado.
;Por exemplo:
(+ 10 (* 2 5))
;O processo é:
;Primeiro: encontra + e resolve o símbolo para a função de adição.
;Depois: avalia os argumentos:
;- 10 resulta em 10.
;- (* 2 5) é outra lista, então é avaliada recursivamente e resulta em 10.
;Por último: invoca + com os argumentos 10 e 10.

;Resultado:
;20
;Perceba que o Evaluator precisa avaliar a lista interna antes de conseguir executar a operação externa.

;3. Mas nem toda lista segue essa regra
;Aqui está uma distinção fundamental: funções comuns e special forms não são avaliadas da mesma maneira.

;Considere:
(if (> 10 5)
  "Maior"
  "Menor")

;Se if fosse uma função comum, o Clojure avaliaria todos os argumentos antes de invocá-la.
;Mas isso seria um problema: ele avaliaria tanto "Maior" quanto "Menor", mesmo que apenas um resultado seja necessário.

;Por isso, if é uma special form.
;Uma special form possui regras próprias de avaliação. No caso do if:
;1. Avalia a condição.
;2. Se ela for verdadeira, avalia somente a expressão do segundo argumento.
;3. Caso contrário, avalia somente a expressão do terceiro argumento.
;O resultado é "Maior".

;Outros exemplos de special forms
;Special form /	Responsabilidade
;if	/ Avaliação condicional
;def	/ Criação de uma Var
;let	/ Criação de bindings locais
;quote	/ Impede a avaliação de uma expressão
;do	/ Executa expressões em sequência e retorna a última
;loop	/ Estabelece um ponto de repetição
;recur	/ Retorna ao ponto de repetição

;Essas construções não são funções comuns, embora sua aparência seja semelhante.

;4. Por que isso importa?
;Compare:
(+ 10 20)

;Com:
(quote (+ 10 20))


;No primeiro caso, o Evaluator executa a função + com os argumentos avaliados.
;No segundo, quote é uma special form e impede que a lista interna seja avaliada. O resultado é a própria lista:
;(+ 10 20)

;Essa capacidade de controlar como o código é avaliado é justamente o que torna possível criar macros, nosso próximo grande assunto.
;Uma distinção para guardar:
;- Função comum: recebe argumentos já avaliados.
;- Special form: possui regras próprias e pode controlar a avaliação dos argumentos.
