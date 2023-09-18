package acc.br.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Representa a solicitação de depósito, contendo o valor do depósito, o tipo de depósito e o número do cheque (se aplicável).
 */
public class DepositoRequest {

    @JsonProperty("valor")
    @NotNull
    private BigDecimal valor;

    @JsonProperty("tipoDeposito")
    @NotNull
    private TipoDeposito tipoDeposito;

    @JsonProperty("numeroCheque")
    private String numeroCheque;
    
    @JsonProperty("tipoConta")
    @NotNull
    private TipoConta tipoConta;

    /**
     * Obtém o valor do depósito.
     *
     * @return O valor do depósito.
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * Define o valor do depósito.
     *
     * @param valor O valor do depósito.
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * Obtém o tipo de depósito (DINHEIRO ou CHEQUE).
     *
     * @return O tipo de depósito.
     */
    public TipoDeposito getTipoDeposito() {
        return tipoDeposito;
    }

    /**
     * Define o tipo de depósito (DINHEIRO ou CHEQUE).
     *
     * @param tipoDeposito O tipo de depósito.
     */
    public void setTipoDeposito(TipoDeposito tipoDeposito) {
        this.tipoDeposito = tipoDeposito;
    }

    /**
     * Obtém o número do cheque (se aplicável).
     *
     * @return O número do cheque.
     */
    public String getNumeroCheque() {
        return numeroCheque;
    }

    /**
     * Define o número do cheque (se aplicável).
     *
     * @param numeroCheque O número do cheque.
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