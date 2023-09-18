package acc.br.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import acc.br.util.TipoTransacao;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

/**
 * Classe que representa o histórico de transações no sistema.
 */
@Entity
@Table(name = "HistoricoTransacoes")
public class HistoricoTransacoes extends PanacheEntityBase {

    /**
     * ID único do histórico de transações.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HistoricoID")
    private Long historicoID;

    /**
     * ID da transação associada ao histórico.
     */
    @NotNull(message = "{quarkus.hibernate-validator.message.notNull.historicoTransacoes.transacaoID}")
    @Column(name = "TransacaoID")
    private Long transacaoID;

    /**
     * Tipo de transação (DEPÓSITO, SAQUE, TRANSFERÊNCIA).
     */
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{quarkus.hibernate-validator.message.notNull.historicoTransacoes.tipoTransacao}")
    @Column(name = "TipoTransacao")
    private TipoTransacao tipoTransacao;

    /**
     * Valor da transação.
     */
    @DecimalMax(value = "9999999.99", message = "{quarkus.hibernate-validator.message.decimalMax.historicoTransacoes.valor}")
    @DecimalMin(value = "0.01", message = "{quarkus.hibernate-validator.message.decimalMin.historicoTransacoes.valor}")
    @Column(name = "Valor")
    private BigDecimal valor;

    /**
     * Data e hora da transação.
     */
    @PastOrPresent(message = "{quarkus.hibernate-validator.message.pastOrPresent.historicoTransacoes.dataTransacao}")
    @Column(name = "DataTransacao")
    private LocalDateTime dataTransacao;

    /**
     * ID da conta de origem associada à transação.
     */
    @Column(name = "ContaOrigemID")
    private Integer contaOrigemID;

    /**
     * ID da conta de destino associada à transação.
     */
    @Column(name = "ContaDestinoID")
    private Integer contaDestinoID;

    // Getters e setters

    /**
     * Obtém o ID do histórico de transações.
     *
     * @return O ID do histórico de transações.
     */
    public Long getHistoricoID() {
        return historicoID;
    }

    /**
     * Define o ID do histórico de transações.
     *
     * @param historicoID O ID do histórico de transações.
     */
    public void setHistoricoID(Long historicoID) {
        this.historicoID = historicoID;
    }

    /**
     * Obtém o ID da transação associada ao histórico.
     *
     * @return O ID da transação associada ao histórico.
     */
    public Long getTransacaoID() {
        return transacaoID;
    }

    /**
     * Define o ID da transação associada ao histórico.
     *
     * @param transacaoID O ID da transação associada ao histórico.
     */
    public void setTransacaoID(Long transacaoID) {
        this.transacaoID = transacaoID;
    }

    /**
     * Obtém o tipo de transação.
     *
     * @return O tipo de transação.
     */
    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    /**
     * Define o tipo de transação.
     *
     * @param tipoTransacao O tipo de transação.
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
    public LocalDateTime getDataTransacao() {
        return dataTransacao;
    }

    /**
     * Define a data e hora da transação.
     *
     * @param dataTransacao A data e hora da transação.
     */
    public void setDataTransacao(LocalDateTime dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    /**
     * Obtém o ID da conta de origem associada à transação.
     *
     * @return O ID da conta de origem associada à transação.
     */
    public Integer getContaOrigemID() {
        return contaOrigemID;
    }

    /**
     * Define o ID da conta de origem associada à transação.
     *
     * @param contaOrigemID O ID da conta de origem associada à transação.
     */
    public void setContaOrigemID(Integer contaOrigemID) {
        this.contaOrigemID = contaOrigemID;
    }

    /**
     * Obtém o ID da conta de destino associada à transação.
     *
     * @return O ID da conta de destino associada à transação.
     */
    public Integer getContaDestinoID() {
        return contaDestinoID;
    }

    /**
     * Define o ID da conta de destino associada à transação.
     *
     * @param contaDestinoID O ID da conta de destino associada à transação.
     */
    public void setContaDestinoID(Integer contaDestinoID) {
        this.contaDestinoID = contaDestinoID;
    }
}

