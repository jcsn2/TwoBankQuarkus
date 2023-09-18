package acc.br.exception;

/**
 * Exceção lançada quando um investimento não é encontrado.
 */
public class InvestimentoNaoEncontradoException extends Exception {

    /**
     * Cria uma nova exceção de investimento não encontrado com a mensagem especificada.
     *
     * @param mensagem A mensagem de erro.
     */
    public InvestimentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}