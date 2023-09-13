package acc.br.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

/**
 * Classe que representa uma conta poupança no sistema.
 */
@Entity
@Table(name = "Poupanca")
public class Poupanca extends PanacheEntityBase {

    /**
     * ID único da conta poupança.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PoupancaID")
    private Long poupancaID;

    /**
     * Saldo da conta poupança.
     */
    @DecimalMax(value = "99999999.99", message = "{quarkus.hibernate-validator.message.decimalMax.poupanca.saldoPoupanca}")
    @Column(name = "SaldoPoupanca")
    private BigDecimal saldoPoupanca;

    /**
     * Data de abertura da conta poupança.
     */
    @PastOrPresent(message = "{quarkus.hibernate-validator.message.pastOrPresent.poupanca.dataAbertura}")
    @Column(name = "DataAbertura")
    private LocalDate dataAbertura;

    /**
     * ID do cliente titular da conta poupança.
     */
    @Column(name = "ClienteID")
    private Long clienteID;

    /**
     * Data de aniversário da conta poupança.
     */
    @Size(max = 10, message = "{quarkus.hibernate-validator.message.size.poupanca.dataAniversario}")
    @Column(name = "DataAniversario")
    private String dataAniversario;

    // Getters e setters

    /**
     * Obtém o ID da conta poupança.
     *
     * @return O ID da conta poupança.
     */
    public Long getPoupancaID() {
        return poupancaID;
    }

    /**
     * Define o ID da conta poupança.
     *
     * @param poupancaID O ID da conta poupança.
     */
    public void setPoupancaID(Long poupancaID) {
        this.poupancaID = poupancaID;
    }

    /**
     * Obtém o saldo da conta poupança.
     *
     * @return O saldo da conta poupança.
     */
    public BigDecimal getSaldoPoupanca() {
        return saldoPoupanca;
    }

    /**
     * Define o saldo da conta poupança.
     *
     * @param saldoPoupanca O saldo da conta poupança.
     */
    public void setSaldoPoupanca(BigDecimal saldoPoupanca) {
        this.saldoPoupanca = saldoPoupanca;
    }

    /**
     * Obtém a data de abertura da conta poupança.
     *
     * @return A data de abertura da conta poupança.
     */
    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    /**
     * Define a data de abertura da conta poupança.
     *
     * @param dataAbertura A data de abertura da conta poupança.
     */
    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    /**
     * Obtém o ID do cliente titular da conta poupança.
     *
     * @return O ID do cliente titular da conta poupança.
     */
    public Long getClienteID() {
        return clienteID;
    }

    /**
     * Define o ID do cliente titular da conta poupança.
     *
     * @param clienteID O ID do cliente titular da conta poupança.
     */
    public void setClienteID(Long clienteID) {
        this.clienteID = clienteID;
    }

    /**
     * Obtém a data de aniversário da conta poupança.
     *
     * @return A data de aniversário da conta poupança.
     */
    public String getDataAniversario() {
        return dataAniversario;
    }

    /**
     * Define a data de aniversário da conta poupança.
     *
     * @param dataAniversario A data de aniversário da conta poupança.
     */
    public void setDataAniversario(String dataAniversario) {
        this.dataAniversario = dataAniversario;
    }
}