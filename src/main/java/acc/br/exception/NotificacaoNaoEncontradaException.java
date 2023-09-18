package acc.br.exception;

/**
 * Exceção lançada quando uma notificação não é encontrada no sistema.
 */
public class NotificacaoNaoEncontradaException extends RuntimeException {

    /**
     * Cria uma nova instância de NotificacaoNaoEncontradaException com a mensagem de erro especificada.
     *
     * @param mensagem A mensagem de erro.
     */
    public NotificacaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}