(ns advanced-topics.clojure-metaphysics.object-oriented-metaphysics)

;1. Object-Oriented Metaphysics
;1.1. Como funciona o modelo orientado a objetos?
;Vamos começar com um exemplo em Java:
;public class Conta {
;                    private BigDecimal saldo;
;
;                    public Conta(BigDecimal saldo) {
;                                                    this.saldo = saldo;
;                                                    }
;
;                    public void depositar(BigDecimal valor) {
;                                                             this.saldo = this.saldo.add(valor);
;                                                             }
;
;                    public BigDecimal getSaldo() {
;                                                  return saldo;
;                                                  }
;                    }
;
;
;Podemos criar uma conta:
;Conta conta = new Conta(new BigDecimal("1000"));
;
;conta.depositar(new BigDecimal("200"));
;
;
;O que aconteceu?
;
;Temos um objeto chamado conta, que possui uma identidade e mantém um estado interno.
;
;Inicialmente:
;Conta
;└── saldo: 1000
;
;
;Depois do depósito:
;Conta
;└── saldo: 1200
;
;
;A identidade da conta continua sendo a mesma. O que mudou foi seu estado interno.
;
;Essa distinção é fundamental:
;- Identidade: qual conta estamos acompanhando?
;- Estado: quais são os dados atuais dessa conta?
;- Valor: quais são os dados que representam determinado estado?
;1.2. O problema da mutabilidade
;Imagine que duas partes da aplicação compartilham a mesma referência para uma conta:
;Conta contaA = conta;
;Conta contaB = conta;
;
;
;As duas variáveis apontam para o mesmo objeto.
;
;Se contaA realizar um depósito, contaB também observará o novo saldo, porque ambas apontam para a mesma identidade.
;
;Isso pode ser útil, mas também traz riscos:
;- Uma parte da aplicação pode modificar um estado que outra parte está utilizando.
;- É necessário controlar o acesso concorrente.
;- Alterações inesperadas podem produzir comportamentos difíceis de rastrear.
;- Testes podem depender de estados modificados anteriormente.
;É especialmente complicado quando várias threads compartilham objetos mutáveis.
;1.3. Como Clojure pensa sobre isso?
;Clojure adota uma abordagem diferente: valores são imutáveis.
;
;Veja:
;(def conta {:titular "Brenno"
;            :saldo 1000})
;
;
;Agora fazemos:
;(def nova-conta
;  (assoc conta :saldo 1200))
;
;
;O que aconteceu?
;- conta continua representando um saldo de 1000.
;- nova-conta representa um saldo de 1200.
;- Nenhuma das duas estruturas foi modificada.
;Podemos conferir:
;(:saldo conta)
;;; 1000
;
;(:saldo nova-conta)
;;; 1200
;
;
;Não existe alteração interna do mapa original.
;1.4. Mas como representar algo que muda constantemente?
;Aqui está a grande questão.
;
;Imagine uma conta bancária. O saldo precisa mudar conforme depósitos e saques acontecem.
;
;Se os valores são imutáveis, como representar essa mudança?
;
;A resposta é separar duas coisas:
;Valor
;1000
;Estado anterior
;Nova atualização de estado
;
;1200
;Novo valor imutável
;Identidade
;Mesma identidade
;Agora associada ao novo valor
;
;Em vez de modificar o valor, Clojure permite que uma identidade passe a representar outro valor ao longo do tempo.
;
;É justamente aqui que entram os mecanismos que estudaremos neste capítulo:
;- Atom: identidade com atualizações atômicas.
;- Ref: identidade com atualizações transacionais.
;- Var: referência associada a definições e bindings.
;1.5. Uma comparação importante
;Conceito	Java tradicional	Clojure
;Dados	Podem ser mutáveis	Valores imutáveis
;Atualização	Modifica o objeto	Produz novo valor
;Identidade	Referência para objeto	Gerenciada por referências
;Concorrência	Exige controle sobre estado compartilhado	Oferece mecanismos próprios de coordenação
;
;Não significa que Java não possa trabalhar com imutabilidade, nem que Clojure não tenha estado mutável. A diferença está no modelo adotado como padrão.
;
;1.6. O que você precisa guardar desta seção?
;A ideia mais importante é:
;
;Em Clojure, não precisamos modificar um valor para representar a evolução de um estado. Podemos manter uma identidade e associá-la a diferentes valores imutáveis ao longo do tempo.
;
;Essa separação entre identidade e valor é a base para compreender Atoms, Refs e Vars.
;
;Na próxima seção, Clojure Metaphysics, vamos aprofundar justamente essa distinção, antes de começar a trabalhar diretamente com Atoms.