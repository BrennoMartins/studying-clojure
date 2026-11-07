(ns content.organizing-your-project.real-project-organization.file-paths-namespace-names)
;
;1. Organização de diretórios
;Imagine um projeto chamado controle-financeiro.
;
;Uma estrutura possível:
;controle-financeiro/
;├── deps.edn
;├── src/
;│   └── controle_financeiro/
;│       ├── core.clj
;│       ├── matematica.clj
;│       └── financeiro.clj
;└── test/
;    └── controle_financeiro/
;        └── financeiro_test.clj
;
;
;Observe a relação:
;Arquivo	Namespace
;src/controle_financeiro/core.clj	controle-financeiro.core
;src/controle_financeiro/matematica.clj	controle-financeiro.matematica
;src/controle_financeiro/financeiro.clj	controle-financeiro.financeiro
;
;Existe uma convenção importante:
;- Diretórios representam partes do namespace.
;- O caractere _ no diretório corresponde a - no namespace.
;- A extensão do arquivo é .clj.
;Por exemplo:
;
;controle_financeiro/financeiro.clj
;
;corresponde a:
;
;controle-financeiro.financeiro
;2. Comparação com Java
;Se você vem do Java, pode pensar assim:
;Java	Clojure
;package	ns
;import	:require
;Pacote	Namespace
;Classe .java	Arquivo .clj (não há correspondência obrigatória com classes)
;Diretório de código-fonte	Diretório de namespaces
;
;A diferença importante é que um namespace Clojure pode conter várias funções e Vars, sem precisar criar uma classe para cada conjunto de funcionalidades.
;3. Criando um arquivo Clojure
;Imagine o arquivo:
;
;src/controle_financeiro/matematica.clj
;
;Conteúdo:
;(ns controle-financeiro.matematica)
;
;(defn dobrar [numero]
;  (* numero 2))
;
;(defn triplicar [numero]
;  (* numero 3))
;
;
;Agora, no arquivo:
;
;src/controle_financeiro/core.clj
;
;Podemos escrever:
;(ns controle-financeiro.core
;  (:require [controle-financeiro.matematica :as math]))
;
;(defn executar []
;  (println (math/dobrar 10))
;  (println (math/triplicar 10)))
;
;
;Assim, core consegue utilizar as funções de matematica.

;; :paths não serve para declarar os namespaces ou packages individualmente. Ele informa ao Clojure onde procurar arquivos.
;:paths: diretórios que entram no classpath, onde o Clojure procura código e recursos.
;:deps: dependências externas declaradas, que o Clojure CLI resolve e disponibiliza no projeto.