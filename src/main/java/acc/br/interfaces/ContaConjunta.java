package acc.br.interfaces;

/**
 * Classe que implementa a interface {@link Conta} e representa uma conta conjunta.
 */
public class ContaConjunta implements Conta {
    private Long contaID; 

    /**
     * Obtém o ID da conta conjunta.
     * 
     * @return O ID da conta conjunta.
     */
    @Override
    public Long getContaID() {
        return contaID;
    }
    
    /**
     * Obtém o ID da conta conjunta.
     * 
     * @return O ID da conta conjunta.
     */
	@Override
	public void setTipoConta(String tipoConta) {
		// TODO Auto-generated method stub
	}

}