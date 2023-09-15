package acc.br.interfaces;

import acc.br.exception.ContaExistenteException;
import acc.br.exception.ContaNaoEncontradaException;
import acc.br.model.Contas;

import javax.validation.Valid;
import java.util.List;

/**
 * Interface que define os serviços para manipulação de contas genéricas.
 *
 * @param <T> O tipo de conta a ser manipulado (por exemplo, ContaCorrente, Poupanca, etc.).
 */
public interface ContasService<T extends Contas> {

    /**
     * Cria uma nova conta do tipo especificado.
     *
     * @param conta A conta a ser criada.
     * @return A conta criada.
     * @throws ContaExistenteException Se uma conta com o mesmo ID já existir.
     */
    T criarConta(@Valid T conta) throws ContaExistenteException;

    /**
     * Lista todas as contas do tipo especificado.
     *
     * @return Uma lista de todas as contas do tipo especificado.
     */
    List<T> listarContas();

    /**
     * Obtém uma conta pelo seu ID.
     *
     * @param contaID O ID da conta a ser obtida.
     * @return A conta com o ID especificado.
     * @throws ContaNaoEncontradaException Se a conta não for encontrada.
     */
    T obterConta(Long contaID) throws ContaNaoEncontradaException;

    /**
     * Atualiza uma conta existente.
     *
     * @param contaID O ID da conta a ser atualizada.
     * @param conta   A conta com os novos dados.
     * @return A conta atualizada.
     * @throws ContaNaoEncontradaException Se a conta não for encontrada.
     */
    T atualizarConta(Long contaID, @Valid T conta) throws ContaNaoEncontradaException;

    /**
     * Remove uma conta pelo seu ID.
     *
     * @param contaID O ID da conta a ser removida.
     * @throws ContaNaoEncontradaException Se a conta não for encontrada.
     */
    void removerConta(Long contaID) throws ContaNaoEncontradaException;

}