package acc.br.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

/**
 * Classe que representa os investimentos no sistema.
 */
@Entity
@Table(name = "Investimentos")
public class Investimentos extends PanacheEntityBase {

    /**
     * ID único do investimento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InvestimentoID")
    private Long investimentoID;

    /**
     * Nome do investimento.
     */
    @Size(max = 100, message = "{quarkus.hibernate-validator.message.size.investimentos.nomeInvestimento}")
    @Column(name = "NomeInvestimento")
    private String nomeInvestimento;

    /**
     * Tipo de investimento.
     */
    @Size(max = 50, message = "{quarkus.hibernate-validator.message.size.investimentos.tipoInvestimento}")
    @Column(name = "TipoInvestimento")
    private String tipoInvestimento;

    /**
     * Saldo do investimento.
     */
    @DecimalMax(value = "9999999.99", message = "{quarkus.hibernate-validator.message.decimalMax.investimentos.saldoInvestimento}")
    @Column(name = "SaldoInvestimento")
    private BigDecimal saldoInvestimento;

    /**
     * Data de início do investimento.
     */
    @PastOrPresent(message = "{quarkus.hibernate-validator.message.pastOrPresent.investimentos.dataInicio}")
    @Column(name = "DataInicio")
    private LocalDate dataInicio;

    /**
     * ID do cliente associado ao investimento.
     */
    @Column(name = "ClienteID")
    private Long clienteID;

    /**
     * Taxa de retorno do investimento.
     */
    @DecimalMax(value = "100.00", message = "{quarkus.hibernate-validator.message.decimalMax.investimentos.taxaRetorno}")
    @Column(name = "TaxaRetorno")
    private BigDecimal taxaRetorno;

    /**
     * Saldo inicial do investimento.
     */
    @DecimalMax(value = "9999999.99", message = "{quarkus.hibernate-validator.message.decimalMax.investimentos.saldoInicial}")
    @Column(name = "SaldoInicial")
    private BigDecimal saldoInicial;

    // Getters e setters

    /**
     * Obtém o ID do investimento.
     *
     * @return O ID do investimento.
     */
    public Long getInvestimentoID() {
        return investimentoID;
    }

    /**
     * Define o ID do investimento.
     *
     * @param investimentoID O ID do investimento.
     */
    public void setInvestimentoID(Long investimentoID) {
        this.investimentoID = investimentoID;
    }

    /**
     * Obtém o nome do investimento.
     *
     * @return O nome do investimento.
     */
    public String getNomeInvestimento() {
        return nomeInvestimento;
    }

    /**
     * Define o nome do investimento.
     *
     * @param nomeInvestimento O nome do investimento.
     */
    public void setNomeInvestimento(String nomeInvestimento) {
        this.nomeInvestimento = nomeInvestimento;
    }

    /**
     * Obtém o tipo de investimento.
     *
     * @return O tipo de investimento.
     */
    public String getTipoInvestimento() {
        return tipoInvestimento;
    }

    /**
     * Define o tipo de investimento.
     *
     * @param tipoInvestimento O tipo de investimento.
     */
    public void setTipoInvestimento(String tipoInvestimento) {
        this.tipoInvestimento = tipoInvestimento;
    }

    /**
     * Obtém o saldo do investimento.
     *
     * @return O saldo do investimento.
     */
    public BigDecimal getSaldoInvestimento() {
        return saldoInvestimento;
    }

    /**
     * Define o saldo do investimento.
     *
     * @param saldoInvestimento O saldo do investimento.
     */
    public void setSaldoInvestimento(BigDecimal saldoInvestimento) {
        this.saldoInvestimento = saldoInvestimento;
    }

    /**
     * Obtém a data de início do investimento.
     *
     * @return A data de início do investimento.
     */
    public LocalDate getDataInicio() {
        return dataInicio;
    }

    /**
     * Define a data de início do investimento.
     *
     * @param dataInicio A data de início do investimento.
     */
    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * Obtém o ID do cliente associado ao investimento.
     *
     * @return O ID do cliente associado ao investimento.
     */
    public Long getClienteID() {
        return clienteID;
    }

    /**
     * Define o ID do cliente associado ao investimento.
     *
     * @param clienteID O ID do cliente associado ao investimento.
     */
    public void setClienteID(Long clienteID) {
        this.clienteID = clienteID;
    }

    /**
     * Obtém a taxa de retorno do investimento.
     *
     * @return A taxa de retorno do investimento.
     */
    public BigDecimal getTaxaRetorno() {
        return taxaRetorno;
    }

    /**
     * Define a taxa de retorno do investimento.
     *
     * @param taxaRetorno A taxa de retorno do investimento.
     */
    public void setTaxaRetorno(BigDecimal taxaRetorno) {
        this.taxaRetorno = taxaRetorno;
    }

    /**
     * Obtém o saldo inicial do investimento.
     *
     * @return O saldo inicial do investimento.
     */
    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    /**
     * Define o saldo inicial do investimento.
     *
     * @param saldoInicial O saldo inicial do investimento.
     */
    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }
}