package acc.br.util;

/**
 * Enum que define os tipos de conta disponíveis no sistema.
 */
public enum TipoConta {
    /**
     * Representa uma conta corrente.
     */
    CONTA_CORRENTE("Conta Corrente"),

    /**
     * Representa uma conta poupança.
     */
    CONTA_POUPANCA("Conta Poupança"),

    /**
     * Representa uma conta conjunta.
     */
    CONTA_CONJUNTA("Conta Conjunta");

    private final String descricao;

    /**
     * Construtor privado para associar uma descrição a cada tipo de conta.
     *
     * @param descricao A descrição do tipo de conta.
     */
    private TipoConta(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém a descrição do tipo de conta.
     *
     * @return A descrição do tipo de conta.
     */
    public String getDescricao() {
        return descricao;
    }
}
