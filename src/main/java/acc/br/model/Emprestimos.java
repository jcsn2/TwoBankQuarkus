package acc.br.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

/**
 * Classe que representa um empréstimo no sistema.
 */
@Entity
@Table(name = "Emprestimos")
public class Emprestimos extends PanacheEntityBase {

    /**
     * ID único do empréstimo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmprestimoID")
    private Long emprestimoID;

    /**
     * Valor do empréstimo.
     */
    @DecimalMax(value = "9999999.99", message = "{quarkus.hibernate-validator.message.decimalMax.emprestimos.valorEmprestimo}")
    @Column(name = "ValorEmprestimo")
    private BigDecimal valorEmprestimo;

    /**
     * Taxa de juros do empréstimo.
     */
    @DecimalMax(value = "100.00", message = "{quarkus.hibernate-validator.message.decimalMax.emprestimos.taxaJuros}")
    @Column(name = "TaxaJuros")
    private BigDecimal taxaJuros;

    /**
     * Data de solicitação do empréstimo.
     */
    @Past(message = "{quarkus.hibernate-validator.message.past.emprestimos.dataSolicitacao}")
    @Column(name = "DataSolicitacao")
    private LocalDate dataSolicitacao;

    /**
     * Status do empréstimo.
     */
    @Size(max = 20, message = "{quarkus.hibernate-validator.message.size.emprestimos.status}")
    @Column(name = "Status")
    private String status;

    /**
     * ID do cliente associado ao empréstimo.
     */
    @Column(name = "ClienteID")
    private Long clienteID;

    /**
     * Prazo em meses do empréstimo.
     */
    @Max(value = 999, message = "{quarkus.hibernate-validator.message.max.emprestimos.prazoMeses}")
    @Column(name = "PrazoMeses")
    private int prazoMeses;

    /**
     * Valor das parcelas do empréstimo.
     */
    @DecimalMax(value = "9999999.99", message = "{quarkus.hibernate-validator.message.decimalMax.emprestimos.valorParcelas}")
    @Column(name = "ValorParcelas")
    private BigDecimal valorParcelas;

    // Getters e setters

    /**
     * Obtém o ID do empréstimo.
     *
     * @return O ID do empréstimo.
     */
    public Long getEmprestimoID() {
        return emprestimoID;
    }

    /**
     * Define o ID do empréstimo.
     *
     * @param emprestimoID O ID do empréstimo.
     */
    public void setEmprestimoID(Long emprestimoID) {
        this.emprestimoID = emprestimoID;
    }

    /**
     * Obtém o valor do empréstimo.
     *
     * @return O valor do empréstimo.
     */
    public BigDecimal getValorEmprestimo() {
        return valorEmprestimo;
    }

    /**
     * Define o valor do empréstimo.
     *
     * @param valorEmprestimo O valor do empréstimo.
     */
    public void setValorEmprestimo(BigDecimal valorEmprestimo) {
        this.valorEmprestimo = valorEmprestimo;
    }

    /**
     * Obtém a taxa de juros do empréstimo.
     *
     * @return A taxa de juros do empréstimo.
     */
    public BigDecimal getTaxaJuros() {
        return taxaJuros;
    }

    /**
     * Define a taxa de juros do empréstimo.
     *
     * @param taxaJuros A taxa de juros do empréstimo.
     */
    public void setTaxaJuros(BigDecimal taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    /**
     * Obtém a data de solicitação do empréstimo.
     *
     * @return A data de solicitação do empréstimo.
     */
    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    /**
     * Define a data de solicitação do empréstimo.
     *
     * @param dataSolicitacao A data de solicitação do empréstimo.
     */
    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    /**
     * Obtém o status do empréstimo.
     *
     * @return O status do empréstimo.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Define o status do empréstimo.
     *
     * @param status O status do empréstimo.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Obtém o ID do cliente associado ao empréstimo.
     *
     * @return O ID do cliente associado ao empréstimo.
     */
    public Long getClienteID() {
        return clienteID;
    }

    /**
     * Define o ID do cliente associado ao empréstimo.
     *
     * @param clienteID O ID do cliente associado ao empréstimo.
     */
    public void setClienteID(Long clienteID) {
        this.clienteID = clienteID;
    }

    /**
     * Obtém o prazo em meses do empréstimo.
     *
     * @return O prazo em meses do empréstimo.
     */
    public int getPrazoMeses() {
        return prazoMeses;
    }

    /**
     * Define o prazo em meses do empréstimo.
     *
     * @param prazoMeses O prazo em meses do empréstimo.
     */
    public void setPrazoMeses(int prazoMeses) {
        this.prazoMeses = prazoMeses;
    }

    /**
     * Obtém o valor das parcelas do empréstimo.
     *
     * @return O valor das parcelas do empréstimo.
     */
    public BigDecimal getValorParcelas() {
        return valorParcelas;
    }

    /**
     * Define o valor das parcelas do empréstimo.
     *
     * @param valorParcelas O valor das parcelas do empréstimo.
     */
    public void setValorParcelas(BigDecimal valorParcelas) {
        this.valorParcelas = valorParcelas;
    }
}