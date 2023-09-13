package acc.br.exception;

/**
 * Exceção lançada quando um empréstimo não é encontrado no sistema.
 */
public class EmprestimoNaoEncontradoException extends RuntimeException {

    public EmprestimoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
