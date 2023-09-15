package acc.br.interfaces;

/**
 * Classe que implementa a interface {@link Conta} e representa uma conta de poupança.
 */
public class Poupanca implements Conta {
    private Long contaID;

    /**
     * Obtém o ID da conta de poupança.
     * 
     * @return O ID da conta de poupança.
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
