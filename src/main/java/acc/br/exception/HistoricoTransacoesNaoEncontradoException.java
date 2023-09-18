package acc.br.exception;

/**
 * Exceção lançada quando um registro de histórico de transações não é encontrado.
 */
public class HistoricoTransacoesNaoEncontradoException extends Exception {

    /**
     * Cria uma nova instância de HistoricoTransacoesNaoEncontradoException com uma mensagem de erro personalizada.
     *
     * @param mensagem A mensagem de erro.
     */
    public HistoricoTransacoesNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
