(ns content.organizing-your-project.creating-and-switching-to-namespaces)
;
;1. O que é um namespace?
;Um namespace é um espaço de nomes que organiza Vars, evitando conflitos entre definições.

;Imagine que temos duas funcionalidades com o mesmo nome:
;;; Namespace financeiro
;(defn calcular-total [valores]
;  (reduce + valores))
;
;;; Namespace vendas
;(defn calcular-total [itens]
;  (reduce + (map :preco itens)))
;
;
;As duas funções possuem o mesmo nome, mas pertencem a namespaces diferentes.
;
;Podemos identificá-las assim:
;financeiro/calcular-total
;vendas/calcular-total
;
;
;Isso é semelhante à organização de pacotes no Java:
;com.empresa.financeiro.Calculadora
;com.empresa.vendas.Calculadora
;
;
;A ideia é organizar o código e evitar colisões de nomes.
;2. Conhecendo ns e *ns*
;No REPL, podemos consultar o namespace atual:
;*ns*
;
;
;Normalmente, no início, você estará no namespace user.
;
;Podemos trocar de namespace utilizando:
;(ns financeiro)
;
;
;Agora, se executar:
;*ns*
;
;
;O namespace atual será financeiro.
;
;Também podemos definir funções dentro dele:
;(ns financeiro)
;
;(defn calcular-total [valores]
;  (reduce + valores))
;
;
;E depois trocar:
;(ns vendas)
;
;
;Agora estamos em outro namespace.
;
;Importante: trocar de namespace não apaga as definições anteriores. Elas continuam associadas ao namespace em que foram criadas.
;3. Vamos praticar
;Execute mentalmente ou no seu REPL:
;(ns financeiro)
;
;(def taxa 0.10)
;
;(defn calcular-imposto [valor]
;  (* valor taxa))
;
;(ns vendas)
;
;(def taxa 0.15)
;
;(defn calcular-imposto [valor]
;  (* valor taxa))

;Agora responda:
;1. Qual é o resultado de (financeiro/calcular-imposto 100)?
;2. Qual é o resultado de (vendas/calcular-imposto 100)?
;3. Por que podemos ter duas funções chamadas calcular-imposto sem conflito?
;Não avance ainda para require e refer. Vamos consolidar primeiro a criação e a troca de namespaces.

;
;Próximo conceito: require
;Até agora, trocamos de namespace com ns. Mas imagine que estamos trabalhando no namespace vendas e precisamos utilizar uma função definida em financeiro.
;
;Como fazemos isso?
;
;É aí que entra require.
;
;Exemplo:
;(ns vendas)
;
;(require 'financeiro)
;
;(financeiro/calcular-imposto 100)
;;; 10.0

;O require carrega o namespace para que possamos utilizar suas definições qualificadas.
;Atenção: o apóstrofo (') indica que estamos passando o símbolo financeiro, e não tentando avaliar uma expressão com esse nome.

;Como você poderia criar um apelido math para esse namespace e chamar a função dobrar usando esse apelido?
;
;Dica: vamos conhecer :as.

;Isso! :as é a opção que usamos para criar um alias.
;
;Mas precisamos colocá-la dentro do require:
;(require '[utilitarios.matematica :as math])
;
;Agora podemos chamar:
;(math/dobrar 10)
;;; 20
;
;
;Entendendo a sintaxe
;- ': indica o símbolo do namespace.
;- []: contém o namespace e suas opções.
;- :as: define um apelido.
;- math: apelido escolhido.
;Mais um detalhe
;Você também pode usar :as diretamente na declaração ns:
;(ns aplicacao
;  (:require [utilitarios.matematica :as math]))
;
;Essa é uma forma bastante comum de organizar os imports em arquivos Clojure.