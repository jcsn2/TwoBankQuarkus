package acc.br.exception;

/**
 * Exceção lançada quando uma conta não é encontrada.
 */
public class ContaNaoEncontradaException extends RuntimeException {

    /**
     * Cria uma nova exceção de conta não encontrada com a mensagem especificada.
     *
     * @param mensagem A mensagem de erro.
     */
    public ContaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}

