package acc.br.exception;

/**
 * Exceção lançada quando uma auditoria de transação não é encontrada no sistema.
 */
public class AuditoriaTransacaoNaoEncontradaException extends RuntimeException {

    /**
     * Cria uma nova instância da exceção com a mensagem especificada.
     *
     * @param mensagem A mensagem de erro.
     */
    public AuditoriaTransacaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    /**
     * Cria uma nova instância da exceção com a mensagem e a causa especificadas.
     *
     * @param mensagem A mensagem de erro.
     * @param causa    A causa da exceção.
     */
    public AuditoriaTransacaoNaoEncontradaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
