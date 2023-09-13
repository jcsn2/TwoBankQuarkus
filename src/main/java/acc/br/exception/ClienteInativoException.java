package acc.br.exception;

/**
 * Exceção lançada quando um cliente está inativo e uma operação não pode ser realizada.
 */
public class ClienteInativoException extends RuntimeException {

    /**
     * Construtor da exceção com uma mensagem personalizada.
     *
     * @param mensagem A mensagem de erro associada à exceção.
     */
    public ClienteInativoException(String mensagem) {
        super(mensagem);
    }
}

