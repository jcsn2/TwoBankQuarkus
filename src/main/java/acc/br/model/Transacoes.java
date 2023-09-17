package acc.br.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import acc.br.util.TipoConta;
import acc.br.util.TipoTransacao;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

/**
 * Classe que representa as transações financeiras.
 */
@Entity
@Table(name = "Transacoes")
public class Transacoes extends PanacheEntityBase {

    /**
     * ID único da transação.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransacaoID")
    private Long transacaoID;

    /**
     * Tipo da transação (Deposito, Saque ou Transferencia).
     */
    @NotNull(message = "{quarkus.hibernate-validator.message.notNull.transacoes.tipoTransacao}")
    @Enumerated(EnumType.STRING)
    @Column(name = "TipoTransacao")
    private TipoTransacao tipoTransacao;

    /**
     * Valor da transação.
     */
    @NotNull(message = "{quarkus.hibernate-validator.message.notNull.transacoes.valor}")
    @DecimalMin(value = "0.01", message = "{quarkus.hibernate-validator.message.decimalMin.transacoes.valor}")
    @DecimalMax(value = "99999999.99", message = "{quarkus.hibernate-validator.message.decimalMax.transacoes.valor}")
    @Column(name = "Valor", precision = 10, scale = 2)
    private BigDecimal valor;

    /**
     * Data e hora da transação.
     */
    @NotNull(message = "{quarkus.hibernate-validator.message.notNull.transacoes.dataHoraTransacao}")
    @PastOrPresent(message = "{quarkus.hibernate-validator.message.pastOrPresent.transacoes.dataHoraTransacao}")
    @Column(name = "DataHoraTransacao")
    private LocalDate dataHoraTransacao;

    /**
     * ID da conta de origem da transação.
     */
    @NotNull(message = "{quarkus.hibernate-validator.message.notNull.transacoes.contaID}")
    @Column(name = "ContaID")
    private Long contaID;

    /**
     * ID da conta de destino da transação (se aplicável).
     */
    @Column(name = "ContaDestinoID")
    private Long contaDestinoID;

    /**
     * Número do cheque (se aplicável).
     */
    @Size(max = 20, message = "{quarkus.hibernate-validator.message.size.transacoes.numeroCheque}")
    @Column(name = "NumeroCheque")
    private String numeroCheque;
    
    @NotNull(message = "{quarkus.hibernate-validator.message.notNull.transacoes.tipoConta}")
    @Enumerated(EnumType.STRING)
    @Column(name = "TipoConta")
    private TipoConta tipoConta;

    // Getters e setters

    /**
     * Obtém o ID da transação.
     *
     * @return O ID da transação.
     */
    public Long getTransacaoID() {
        return transacaoID;
    }

    /**
     * Define o ID da transação.
     *
     * @param transacaoID O ID da transação.
     */
    public void setTransacaoID(Long transacaoID) {
        this.transacaoID = transacaoID;
    }

    /**
     * Obtém o tipo da transação.
     *
     * @return O tipo da transação.
     */
    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    /**
     * Define o tipo da transação.
     *
     * @param tipoTransacao O tipo da transação.
     */
    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    /**
     * Obtém o valor da transação.
     *
     * @return O valor da transação.
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * Define o valor da transação.
     *
     * @param valor O valor da transação.
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * Obtém a data e hora da transação.
     *
     * @return A data e hora da transação.
     */
    public LocalDate getDataHoraTransacao() {
        return dataHoraTransacao;
    }

    /**
     * Define a data e hora da transação.
     *
     * @param dataHoraTransacao A data e hora da transação.
     */
    public void setDataHoraTransacao(LocalDate dataHoraTransacao) {
        this.dataHoraTransacao = dataHoraTransacao;
    }

    /**
     * Obtém o ID da conta de origem da transação.
     *
     * @return O ID da conta de origem da transação.
     */
    public Long getContaID() {
        return contaID;
    }

    /**
     * Define o ID da conta de origem da transação.
     *
     * @param contaID O ID da conta de origem da transação.
     */
    public void setContaID(Long contaID) {
        this.contaID = contaID;
    }

    /**
     * Obtém o ID da conta de destino da transação (se aplicável).
     *
     * @return O ID da conta de destino da transação (se aplicável).
     */
    public Long getContaDestinoID() {
        return contaDestinoID;
    }

    /**
     * Define o ID da conta de destino da transação (se aplicável).
     *
     * @param contaDestinoID O ID da conta de destino da transação (se aplicável).
     */
    public void setContaDestinoID(Long contaDestinoID) {
        this.contaDestinoID = contaDestinoID;
    }

    /**
     * Obtém o número do cheque (se aplicável).
     *
     * @return O número do cheque (se aplicável).
     */
    public String getNumeroCheque() {
        return numeroCheque;
    }

    /**
     * Define o número do cheque (se aplicável).
     *
     * @param numeroCheque O número do cheque (se aplicável).
     */
    public void setNumeroCheque(String numeroCheque) {
        this.numeroCheque = numeroCheque;
    }
    
    /**
     * Obtém o tipo da conta.
     *
     * @return O tipo da conta.
     */
    public TipoConta getTipoConta() {
        return tipoConta;
    }

    /**
     * Define o tipo da conta.
     *
     * @param tipoConta O tipo da conta.
     */
    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }
}