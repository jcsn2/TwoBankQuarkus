package acc.br.interfaces;

/**
 * Classe que implementa a interface {@link Conta} e representa uma conta corrente.
 */
public class ContaCorrente implements Conta {
    private Long contaID; 

    /**
     * Obtém o ID da conta corrente.
     * 
     * @return O ID da conta corrente.
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
