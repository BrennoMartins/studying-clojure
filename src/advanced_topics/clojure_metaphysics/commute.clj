(ns advanced-topics.clojure-metaphysics.commute)

;1. Relembrando o alter
;Até agora, usamos alter assim:
(def visitas (ref 0))
(dosync
  (alter visitas inc))

;O alter calcula uma nova versão do estado com base no valor observado pela transação. ;
;Se outra transação modificar a mesma Ref enquanto a nossa está em andamento, pode ocorrer um conflito e a transação precisar ser repetida.

;2. Conhecendo o commute
;O commute também altera uma Ref dentro de dosync, mas tem uma diferença importante:
;ele permite que o STM reaplique a operação sobre o valor mais recente da Ref no momento da confirmação da transação.

;Veja:
(def visitas (ref 0))
(dosync
  (commute visitas inc))

;Nesse caso, estamos dizendo:
;Não preciso que esta operação seja aplicada sobre uma versão específica do contador. Quero apenas incrementar o valor atual.
;Isso é útil quando a operação é comutativa: a ordem das atualizações não altera o resultado final.

;Por exemplo:
(+ (+ 10 5) 3)
;;; 18
;
(+ (+ 10 3) 5)
;;; 18
;A ordem dos incrementos não importa.

;3. Diferença entre alter e commute
;Característica	/ alter	/ commute
;Atualiza uma Ref	/ Sim	/ Sim
;Exige dosync	/ Sim /	Sim
;Pode gerar conflitos de leitura/escrita	/ Sim	/ Menos conflitos em operações compatíveis
;Considera a ordem das operações	/ Sim	/ Pode permitir reordenação
;Uso ideal	/Atualizações dependentes do estado observado	/ Atualizações comutativas

;Atenção: commute não é simplesmente uma versão mais rápida de alter.
; Só devemos utilizá-lo quando a operação puder ser reaplicada sobre o estado mais recente sem comprometer as regras do sistema.
;Por exemplo, incrementar um contador é um bom caso.
; Transferir dinheiro entre duas contas não é um bom caso para substituir alter por commute, pois precisamos preservar a consistência entre os saldos.

;4. Exercício prático
;Imagine que estamos desenvolvendo um sistema de métricas de acesso.
;Precisamos implementar duas funções:
;1. registrar-acesso: incrementa o contador usando commute.
;2. registrar-varios-acessos: recebe uma quantidade e incrementa o contador por esse valor, também usando commute.

(def acessos (ref 0))
(defn registrar-acesso
  []
  (dosync
    (commute acessos inc)))

(defn registrar-varios-acessos
  [valor]
  (dosync
    (commute acessos + valor)))
;Exemplo esperado:
(registrar-acesso)
(registrar-varios-acessos 10)
;
;@acessos
;;; 11