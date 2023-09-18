package acc.br.exception;

/**
 * Exceção lançada quando uma tentativa é feita para criar uma conta que já existe.
 */
public class ContaExistenteException extends RuntimeException {

    /**
     * Cria uma nova exceção de conta existente com a mensagem especificada.
     *
     * @param mensagem A mensagem de erro.
     */
    public ContaExistenteException(String mensagem) {
        super(mensagem);
    }
}

