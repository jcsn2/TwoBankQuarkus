package acc.br.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

/**
 * Classe que representa uma conta conjunta no sistema.
 */
@Entity
@Table(name = "ContasConjuntas")
public class ContasConjuntas extends PanacheEntityBase {

    /**
     * ID único da conta conjunta.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContaConjuntaID")
    private Long contaConjuntaID;

    /**
     * Lista de titulares da conta conjunta.
     */
    @Size(max = 2000, message = "{quarkus.hibernate-validator.message.size.contasConjuntas.titulares}")
    @Column(name = "Titulares")
    private String titulares;

    /**
     * Tipo da conta conjunta.
     */
    @NotBlank(message = "{quarkus.hibernate-validator.message.notBlank.contasConjuntas.tipoConta}")
    @Size(max = 50, message = "{quarkus.hibernate-validator.message.size.contasConjuntas.tipoConta}")
    @Column(name = "TipoConta")
    private String tipoConta;

    /**
     * Saldo da conta conjunta.
     */
    @NotNull(message = "{quarkus.hibernate-validator.message.notNull.contasConjuntas.saldo}")
    @DecimalMin(value = "0.00", message = "{quarkus.hibernate-validator.message.decimalMin.contasConjuntas.saldo}")
    @DecimalMax(value = "9999999.99", message = "{quarkus.hibernate-validator.message.decimalMax.contasConjuntas.saldo}")
    @Column(name = "Saldo")
    private BigDecimal saldo;

    /**
     * Data de abertura da conta conjunta.
     */
    @PastOrPresent(message = "{quarkus.hibernate-validator.message.pastOrPresent.contasConjuntas.dataAbertura}")
    @Column(name = "DataAbertura")
    private LocalDate dataAbertura;

    /**
     * Status da conta conjunta.
     */
    @Size(max = 20, message = "{quarkus.hibernate-validator.message.size.contasConjuntas.statusConta}")
    @Column(name = "StatusConta")
    private String statusConta;
    
    /**
     * Indica se a conta conjunta está ativa.
     */
    @Column(name = "Ativa")
    private boolean ativa;

    // Getters e setters

    /**
     * Obtém o ID da conta conjunta.
     *
     * @return O ID da conta conjunta.
     */
    public Long getContaConjuntaID() {
        return contaConjuntaID;
    }

    /**
     * Define o ID da conta conjunta.
     *
     * @param contaConjuntaID O ID da conta conjunta.
     */
    public void setContaConjuntaID(Long contaConjuntaID) {
        this.contaConjuntaID = contaConjuntaID;
    }

    /**
     * Obtém a lista de titulares da conta conjunta.
     *
     * @return A lista de titulares da conta conjunta.
     */
    public String getTitulares() {
        return titulares;
    }

    /**
     * Define a lista de titulares da conta conjunta.
     *
     * @param titulares A lista de titulares da conta conjunta.
     */
    public void setTitulares(String titulares) {
        this.titulares = titulares;
    }

    /**
     * Obtém o tipo da conta conjunta.
     *
     * @return O tipo da conta conjunta.
     */
    public String getTipoConta() {
        return tipoConta;
    }

    /**
     * Define o tipo da conta conjunta.
     *
     * @param tipoConta O tipo da conta conjunta.
     */
    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    /**
     * Obtém o saldo da conta conjunta.
     *
     * @return O saldo da conta conjunta.
     */
    public BigDecimal getSaldo() {
        return saldo;
    }

    /**
     * Define o saldo da conta conjunta.
     *
     * @param saldo O saldo da conta conjunta.
     */
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    /**
     * Obtém a data de abertura da conta conjunta.
     *
     * @return A data de abertura da conta conjunta.
     */
    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    /**
     * Define a data de abertura da conta conjunta.
     *
     * @param dataAbertura A data de abertura da conta conjunta.
     */
    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    /**
     * Obtém o status da conta conjunta.
     *
     * @return O status da conta conjunta.
     */
    public String getStatusConta() {
        return statusConta;
    }

    /**
     * Define o status da conta conjunta.
     *
     * @param statusConta O status da conta conjunta.
     */
    public void setStatusConta(String statusConta) {
        this.statusConta = statusConta;
    }
    
    /**
     * Verifica se a conta conjunta está ativa.
     *
     * @return true se a conta conjunta estiver ativa, caso contrário, false.
     */
    public boolean isAtiva() {
        return ativa;
    }
}