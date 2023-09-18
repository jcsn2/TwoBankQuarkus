package acc.br.exception;

/**
 * Exceção lançada quando um cliente já existe no sistema.
 */
public class CPF_invalido extends RuntimeException {

    /**
     * Cria uma nova exceção de cliente existente com a mensagem especificada.
     *
     * @param mensagem A mensagem de erro.
     */
    public CPF_invalido(String mensagem) {
        super(mensagem);
    }
    
}