package acc.br.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
 * Classe que representa uma conta no sistema.
 */
@Entity
@Table(name = "Contas")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "TipoConta", discriminatorType = DiscriminatorType.STRING)
public class Contas extends PanacheEntity {

    /**
     * ID único da conta.
     */
    @Column(name = "ContaID")
    private Long contaID;

    /**
     * Tipo da conta.
     */
    @NotBlank(message = "{quarkus.hibernate-validator.message.notBlank.contas.tipoConta}")
    @Size(max = 50, message = "{quarkus.hibernate-validator.message.size.contas.tipoConta}")
    @Column(name = "TipoConta")
    private String tipoConta;

    /**
     * Saldo da conta.
     */
    @NotNull(message = "{quarkus.hibernate-validator.message.notNull.contas.saldo}")
    @DecimalMin(value = "0.00", message = "{quarkus.hibernate-validator.message.decimalMin.contas.saldo}")
    @DecimalMax(value = "9999999.99", message = "{quarkus.hibernate-validator.message.decimalMax.contas.saldo}")
    @Column(name = "Saldo")
    private BigDecimal saldo;

    /**
     * Data de abertura da conta.
     */
    @PastOrPresent(message = "{quarkus.hibernate-validator.message.pastOrPresent.contas.dataAbertura}")
    @Column(name = "DataAbertura")
    private LocalDate dataAbertura;

    /**
     * Status da conta.
     */
    @Size(max = 20, message = "{quarkus.hibernate-validator.message.size.contas.statusConta}")
    @Column(name = "StatusConta")
    private String statusConta;

    /**
     * ID do cliente associado à conta.
     */
    @Column(name = "ClienteID")
    private Long clienteID;
    
    /**
     * Indica se a conta está ativa.
     */
    @Column(name = "Ativa")
    private boolean ativa;

    // Getters e setters

    /**
     * Obtém o ID da conta.
     *
     * @return O ID da conta.
     */
    public Long getContaID() {
        return contaID;
    }

    /**
     * Define o ID da conta.
     *
     * @param contaID O ID da conta.
     */
    public void setContaID(Long contaID) {
        this.contaID = contaID;
    }

    /**
     * Obtém o tipo da conta.
     *
     * @return O tipo da conta.
     */
    public String getTipoConta() {
        return tipoConta;
    }

    /**
     * Define o tipo da conta.
     *
     * @param tipoConta O tipo da conta.
     */
    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    /**
     * Obtém o saldo da conta.
     *
     * @return O saldo da conta.
     */
    public BigDecimal getSaldo() {
        return saldo;
    }

    /**
     * Define o saldo da conta.
     *
     * @param saldo O saldo da conta.
     */
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    /**
     * Obtém a data de abertura da conta.
     *
     * @return A data de abertura da conta.
     */
    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    /**
     * Define a data de abertura da conta.
     *
     * @param dataAbertura A data de abertura da conta.
     */
    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    /**
     * Obtém o status da conta.
     *
     * @return O status da conta.
     */
    public String getStatusConta() {
        return statusConta;
    }

    /**
     * Define o status da conta.
     *
     * @param statusConta O status da conta.
     */
    public void setStatusConta(String statusConta) {
        this.statusConta = statusConta;
    }

    /**
     * Obtém o ID do cliente associado à conta.
     *
     * @return O ID do cliente associado à conta.
     */
    public Long getClienteID() {
        return clienteID;
    }

    /**
     * Define o ID do cliente associado à conta.
     *
     * @param clienteID O ID do cliente associado à conta.
     */
    public void setClienteID(Long clienteID) {
        this.clienteID = clienteID;
    }
    
    /**
     * Verifica se a conta está ativa.
     *
     * @return true se a conta estiver ativa, caso contrário, false.
     */
    public boolean isAtiva() {
        return ativa;
    }
}