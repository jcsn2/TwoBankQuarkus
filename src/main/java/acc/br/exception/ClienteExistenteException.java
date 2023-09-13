package acc.br.exception;

/**
 * Exceção lançada quando um cliente já existe no sistema.
 */
public class ClienteExistenteException extends RuntimeException {

    /**
     * Cria uma nova exceção de cliente existente com a mensagem especificada.
     *
     * @param mensagem A mensagem de erro.
     */
    public ClienteExistenteException(String mensagem) {
        super(mensagem);
    }
    
}