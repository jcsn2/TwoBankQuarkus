package acc.br.exception;

/**
 * Exceção lançada quando uma conta poupança não é encontrada no sistema.
 */
public class PoupancaNaoEncontradaException extends RuntimeException {

    public PoupancaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}