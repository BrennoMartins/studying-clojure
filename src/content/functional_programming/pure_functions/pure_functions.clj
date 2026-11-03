(ns content.functional-programming.pure-functions.pure-functions)


;1. O que é uma função pura?
;Uma função é considerada pura quando atende a duas propriedades:

;1. Determinismo
;Sempre que receber os mesmos argumentos, produzirá o mesmo resultado.
(defn somar
  [a b]
  (+ a b))
;
(somar 10 20) ; => 30
(somar 10 20) ; => 30
(somar 10 20) ; => 30

;Não importa quantas vezes você execute a função. O resultado será sempre 30.


;2. Ausência de efeitos colaterais (Side Effects)
;A função não pode modificar ou depender de algo externo ao seu contexto de execução.
;Por exemplo:
(def total (atom 0))
;
(defn somar!
  [valor]
  (swap! total + valor)
  @total)

;Essa função não é pura porque modifica um estado externo (total).

;Observe:
(somar! 10) ; => 10
(somar! 10) ; => 20
(somar! 10) ; => 30
;O mesmo argumento produziu resultados diferentes porque a função depende do estado anterior.
;Um detalhe importante: uma função pode retornar sempre o mesmo resultado e ainda assim não ser pura, caso produza efeitos colaterais.

;3. Por que isso é importante?
;O livro destaca algumas vantagens das funções puras.
;Benefício	Explicação
;Previsibilidade;	Mesmo argumento, mesmo resultado.
;Testabilidade	;Não precisamos preparar estados externos complexos.
;Reutilização	;Podemos utilizar a função em diferentes contextos.
;Concorrência;	Menos problemas relacionados à alteração compartilhada de estado.
;Depuração;	É mais fácil identificar onde um resultado foi produzido.

