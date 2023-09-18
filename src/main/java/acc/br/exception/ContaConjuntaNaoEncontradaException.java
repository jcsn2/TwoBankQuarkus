package acc.br.exception;

/**
 * Exceção lançada quando uma conta conjunta não é encontrada.
 */
public class ContaConjuntaNaoEncontradaException extends RuntimeException {

    public ContaConjuntaNaoEncontradaException(String message) {
        super(message);
    }
}
