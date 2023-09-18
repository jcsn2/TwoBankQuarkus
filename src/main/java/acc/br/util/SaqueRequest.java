package acc.br.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Representa a solicitação de saque, contendo o valor do saque e o tipo de conta.
 */
public class SaqueRequest {

    @JsonProperty("valor")
    @NotNull
    private BigDecimal valor;

    @JsonProperty("tipoConta")
    @NotNull
    private TipoConta tipoConta;

    /**
     * Obtém o valor do saque.
     *
     * @return O valor do saque.
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * Define o valor do saque.
     *
     * @param valor O valor do saque.
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * Obtém o tipo de conta.
     *
     * @return O tipo de conta.
     */
    public TipoConta getTipoConta() {
        return tipoConta;
    }

    /**
     * Define o tipo de conta.
     *
     * @param tipoConta O tipo de conta.
     */
    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }
}
