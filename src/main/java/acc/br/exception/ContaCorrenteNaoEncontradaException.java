package acc.br.exception;

/**
 * Exceção lançada quando uma conta conjunta não é encontrada.
 */
public class ContaCorrenteNaoEncontradaException extends RuntimeException {

    public ContaCorrenteNaoEncontradaException(String message) {
        super(message);
    }
}
