package acc.br.exception;

/**
 * Exceção lançada quando um empréstimo já existe no sistema e não pode ser duplicado.
 */
public class EmprestimoExistenteException extends RuntimeException {

    public EmprestimoExistenteException(String mensagem) {
        super(mensagem);
    }
}
