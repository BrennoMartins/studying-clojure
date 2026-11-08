(ns content.organizing-your-project.refer)

;Até agora, quando utilizamos uma função de outro namespace, precisamos informar seu namespace:
;(require '[utilitarios.matematica :as math])
;
;(math/dobrar 10)
;
;
;Mas imagine que você queira utilizar várias funções desse namespace sem precisar escrever math/ toda vez.
;
;É aí que entra o refer.
;1. Utilizando refer
;Podemos fazer assim:
;(ns aplicacao
;  (:require [utilitarios.matematica :refer [dobrar]]))

;Agora podemos chamar diretamente:
;(dobrar 10)
;;; 20
;
;
;Observe a diferença:
;Forma	Chamada
;:as	(math/dobrar 10)
;:refer	(dobrar 10)
;
;2. Referenciando várias funções
;Podemos disponibilizar várias funções do mesmo namespace:
;(ns aplicacao
;  (:require [utilitarios.matematica
;             :refer [dobrar triplicar somar]]))
;
;
;Assim, podemos chamar:
;(dobrar 10)
;(triplicar 10)
;(somar 10 20)
;

;Também existe :refer :all, que disponibiliza todos os nomes públicos do namespace:
;(require '[utilitarios.matematica :refer :all])
;Mas é preciso cuidado: isso pode causar conflitos de nomes e deixar menos claro de onde cada função veio.
;Minha recomendação: prefira :as na maioria dos casos. Use :refer quando fizer sentido e os nomes forem claros.