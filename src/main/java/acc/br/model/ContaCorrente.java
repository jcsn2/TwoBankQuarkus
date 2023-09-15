package acc.br.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

/**
 * Representa uma conta corrente bancária no sistema.
 */
@Entity
@Table(name = "ContaCorrente")
@DiscriminatorValue("Conta Corrente")
public class ContaCorrente extends Contas{

    /**
     * Nome da agência onde a conta corrente está registrada.
     */
    @NotBlank(message = "{contaCorrente.agencia.notBlank}")
    @Column(name = "Agencia")
    private String agencia;

    /**
     * Número da agência onde a conta corrente está registrada.
     */
    @NotBlank(message = "{contaCorrente.numeroConta.notBlank}")
    @Column(name = "NumeroConta")
    private String numeroConta;

    /**
     * Taxa de manutenção mensal da conta corrente.
     */
    @DecimalMin(value = "0.00", message = "{contaCorrente.taxaManutencaoMensal.decimalMin}")
    @Column(name = "TaxaManutencaoMensal")
    private BigDecimal taxaManutencaoMensal;
    
    /**
     * Limite de crédito da conta.
     */
    @DecimalMax(value = "9999999.99", message = "{quarkus.hibernate-validator.message.decimalMax.contas.limiteCredito}")
    @Column(name = "LimiteCredito")
    private BigDecimal limiteCredito;
    
    // Getters e setters
    
    /**
     * Obtém o limite de crédito da conta.
     *
     * @return O limite de crédito da conta.
     */
    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    /**
     * Define o limite de crédito da conta.
     *
     * @param limiteCredito O limite de crédito da conta.
     */
    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }
    
    
}
