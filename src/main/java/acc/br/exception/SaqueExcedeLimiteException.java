package acc.br.exception;

/**
 * Exceção lançada quando saque excede limite da conta.
 */
public class SaqueExcedeLimiteException extends RuntimeException {

    public SaqueExcedeLimiteException(String message) {
        super(message);
    }
}
