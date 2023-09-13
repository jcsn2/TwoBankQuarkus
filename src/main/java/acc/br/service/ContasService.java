package acc.br.service;

import acc.br.model.Contas;
import acc.br.repository.ContasRepository;
import acc.br.exception.ContaExistenteException;
import acc.br.exception.ContaNaoEncontradaException;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

/**
 * Serviço para operações relacionadas a contas em um sistema bancário.
 */
@ApplicationScoped
public class ContasService {

    private final ContasRepository contasRepository;

    public ContasService(ContasRepository contasRepository) {
        this.contasRepository = contasRepository;
    }

    /**
     * Lista todas as contas no sistema.
     *
     * @return Uma lista de contas.
     */
    public List<Contas> listarContas() {
        return contasRepository.listAll();
    }

    /**
     * Obtém uma conta pelo seu ID.
     *
     * @param contaID O ID da conta a ser obtida.
     * @return A conta encontrada.
     * @throws ContaNaoEncontradaException Se a conta não for encontrada.
     */
    public Contas obterConta(Long contaID) {
        Contas conta = contasRepository.findById(contaID);
        if (conta == null) {
            throw new ContaNaoEncontradaException("Conta não encontrada com ID: " + contaID);
        }
        return conta;
    }

    /**
     * Cria uma nova conta.
     *
     * @param conta A conta a ser criada.
     * @return A conta criada.
     * @throws ContaExistenteException Se uma conta com o mesmo ID já existir.
     */
    @Transactional
    public Contas criarConta(@Valid Contas conta) {
        if (conta.getContaID() != 0 && contasRepository.findById(conta.getContaID()) != null) {
            throw new ContaExistenteException("Conta já existe com ID: " + conta.getContaID());
        }
        conta.setStatusConta("Ativa");
        contasRepository.persist(conta);
        return conta;
    }

    /**
     * Atualiza uma conta existente.
     *
     * @param contaID O ID da conta a ser atualizada.
     * @param conta   Os dados atualizados da conta.
     * @return A conta atualizada.
     * @throws ContaNaoEncontradaException Se a conta não for encontrada.
     */
    @Transactional
    public Contas atualizarConta(Long contaID, @Valid Contas conta) {
        Contas contaExistente = contasRepository.findById(contaID);
        if (contaExistente == null) {
            throw new ContaNaoEncontradaException("Conta não encontrada com ID: " + contaID);
        }
        conta.setContaID(contaID);
        contasRepository.getEntityManager().merge(conta);
        return conta;
    }

    /**
     * Remove uma conta pelo seu ID.
     *
     * @param contaID O ID da conta a ser removida.
     * @throws ContaNaoEncontradaException Se a conta não for encontrada.
     */
    @Transactional
    public void removerConta(Long contaID) {
        Contas conta = contasRepository.findById(contaID);
        if (conta == null) {
            throw new ContaNaoEncontradaException("Conta não encontrada com ID: " + contaID);
        }
        contasRepository.delete(conta);
    }
}