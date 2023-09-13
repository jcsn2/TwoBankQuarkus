package acc.br.exception;

/**
 * Exceção lançada quando um cliente não é encontrado no sistema.
 */
public class ClienteNaoEncontradoException extends RuntimeException {

    /**
     * Construtor da exceção com uma mensagem personalizada.
     *
     * @param mensagem A mensagem de erro associada à exceção.
     */
    public ClienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
