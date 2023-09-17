package acc.br.exception;

/**
 * Exceção lançada quando um parâmetro de configuração não é encontrado.
 */
public class ParametroConfiguracaoDuplicadoException extends Exception {

    /**
     * Cria uma nova exceção de parâmetro de configuração não encontrado com a mensagem especificada.
     *
     * @param mensagem A mensagem de erro.
     */
    public ParametroConfiguracaoDuplicadoException(String mensagem) {
        super(mensagem);
    }
}