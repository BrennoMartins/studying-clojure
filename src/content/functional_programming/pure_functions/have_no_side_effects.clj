(ns content.functional-programming.pure-functions.have-no-side-effects)

;Agora vamos aprofundar o segundo aspecto das funções puras: ausência de efeitos colaterais.
;Um efeito colateral acontece quando uma função faz alguma coisa além de calcular e retornar seu resultado.

;Alguns exemplos:
;Operação	É efeito colateral?
;Alterar um atom	_> Sim
;Modificar uma variável global ->	Sim
;Escrever em um arquivo	-> Sim
;Inserir dados no banco	-> Sim
;Enviar uma requisição HTTP	-> Sim
;Imprimir no terminal com println	-> Sim
;Retornar um novo mapa	-> Não
;Retornar um novo vetor	-> Não
;Calcular uma soma	-> Não

;Um detalhe importante: uma função pura não precisa ser simples ou fazer apenas cálculos matemáticos. Ela pode executar transformações complexas de dados, desde que não dependa de estado externo variável nem produza efeitos colaterais.


;Resumo
;Pure Functions
;Uma função pura:
;- Produz o mesmo resultado para os mesmos argumentos.
;- Não depende de estado externo variável.
;- Não produz efeitos colaterais.
;Referential Transparency
;Uma expressão possui transparência referencial quando podemos substituí-la pelo seu resultado sem alterar o comportamento do programa.
;Side Effects
;São efeitos produzidos além do simples retorno do resultado, como:
;- Alterar estado.
;- Escrever em arquivos.
;- Persistir dados.
;- Fazer requisições HTTP.
;- Imprimir no terminal.
;Principal aprendizado
;Não precisamos eliminar os efeitos colaterais de uma aplicação. Precisamos organizá-los para que as regras de negócio sejam predominantemente puras.
;
;Essa separação facilita testes, manutenção e previsibilidade.