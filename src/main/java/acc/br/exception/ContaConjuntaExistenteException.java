package acc.br.exception;

/**
 * Exceção lançada quando uma conta conjunta já existe.
 */
public class ContaConjuntaExistenteException extends RuntimeException {

    public ContaConjuntaExistenteException(String message) {
        super(message);
    }
}

