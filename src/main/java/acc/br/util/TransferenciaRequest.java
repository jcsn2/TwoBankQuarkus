package acc.br.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Representa a solicitação de transferência, contendo o valor da transferência e os tipos de conta de origem e destino.
 */
public class TransferenciaRequest {

    @JsonProperty("valor")
    @NotNull
    private BigDecimal valor;

    @JsonProperty("tipoContaOrigem")
    @NotNull
    private TipoConta tipoContaOrigem;

    @JsonProperty("tipoContaDestino")
    @NotNull
    private TipoConta tipoContaDestino;

    /**
     * Obtém o valor da transferência.
     *
     * @return O valor da transferência.
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * Define o valor da transferência.
     *
     * @param valor O valor da transferência.
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * Obtém o tipo de conta de origem.
     *
     * @return O tipo de conta de origem.
     */
    public TipoConta getTipoContaOrigem() {
        return tipoContaOrigem;
    }

    /**
     * Define o tipo de conta de origem.
     *
     * @param tipoContaOrigem O tipo de conta de origem.
     */
    public void setTipoContaOrigem(TipoConta tipoContaOrigem) {
        this.tipoContaOrigem = tipoContaOrigem;
    }

    /**
     * Obtém o tipo de conta de destino.
     *
     * @return O tipo de conta de destino.
     */
    public TipoConta getTipoContaDestino() {
        return tipoContaDestino;
    }

    /**
     * Define o tipo de conta de destino.
     *
     * @param tipoContaDestino O tipo de conta de destino.
     */
    public void setTipoContaDestino(TipoConta tipoContaDestino) {
        this.tipoContaDestino = tipoContaDestino;
    }
}