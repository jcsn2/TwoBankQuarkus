package acc.br.exception;

/**
 * Exceção lançada quando uma transação financeira não é encontrada.
 */
public class TransacoesNaoEncontradaException extends Exception {

    public TransacoesNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
