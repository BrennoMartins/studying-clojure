(ns content.organizing-your-project.real-project-organization.the-ns-macro)
;
;A macro ns
;Já utilizamos bastante:
;(ns controle-financeiro.core
;  (:require [controle-financeiro.matematica :as math]))
;
;
;Agora vamos entender melhor essa declaração.
;
;A macro ns permite:
;- Definir o namespace atual.
;- Declarar dependências com :require.
;- Importar classes Java com :import.
;- Referenciar Vars de outros namespaces.

;Um detalhe sobre a macro ns
;A declaração ns é uma macro, não uma função comum.
;
;Ela configura o namespace atual e suas referências, além de permitir declarar imports e outras opções.
;
;Por exemplo:
;(ns controle-financeiro.core
;  (:require [clojure.string :as str])
;  (:import [java.time LocalDate]))
;
;
;Aqui temos:
;- :require: dependência de outro namespace Clojure.
;- :import: importação de uma classe Java.