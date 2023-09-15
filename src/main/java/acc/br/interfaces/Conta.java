package acc.br.interfaces;

/**
 * Interface que representa uma conta.
 */
public interface Conta {
    /**
     * Obtém o ID da conta.
     * 
     * @return O ID da conta.
     */
    Long getContaID();
    
    /**
     * Define o tipo da conta.
     * 
     * @param tipoConta O tipo da conta a ser definido.
     */
    void setTipoConta(String tipoConta);
}