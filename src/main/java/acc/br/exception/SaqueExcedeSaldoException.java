package acc.br.exception;

/**
 * Exceção lançada quando saque excede limite da conta.
 */
public class SaqueExcedeSaldoException extends RuntimeException {

    public SaqueExcedeSaldoException(String message) {
        super(message);
    }
}
