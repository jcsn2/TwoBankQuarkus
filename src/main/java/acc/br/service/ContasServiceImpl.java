package acc.br.service;

import acc.br.model.Contas;
import acc.br.repository.ContasRepository;
import acc.br.util.ContasServiceQualifier;
import acc.br.exception.ContaExistenteException;
import acc.br.exception.ContaNaoEncontradaException;
import acc.br.exception.PoupancaNaoEncontradaException;
import acc.br.interfaces.Conta;
import acc.br.interfaces.ContasService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para manipular contas genéricas.
 *
 * @param <T> O tipo de conta a ser manipulado (por exemplo, ContaCorrente, Poupanca, etc.).
 */
@ApplicationScoped
@ContasServiceQualifier
public class ContasServiceImpl<T extends Contas> implements ContasService<T> {

	@Inject
    ContasRepository contasRepository;
	
    @Inject
    EntityManager entityManager;
    
    private ContasRepository entityType;
    
    public ContasServiceImpl(ContasRepository contasRepository) {
        this.entityType = contasRepository;
    }
    
	/**
     * Lista todas as contas no sistema.
     *
     * @return Uma lista de contas.
     */
    @Override
    public List<T> listarContas() {
        if (entityType == null) {
            throw new IllegalStateException("entityType não foi definido corretamente");
        }

        // Use o tipo genérico T para construir a consulta
        String queryString = "SELECT c FROM " + entityType.getSimpleName() + " c";

        // Crie a consulta usando o tipo T
        TypedQuery<T> query = (TypedQuery<T>) entityManager.createQuery(queryString);
        
        // Execute a consulta e retorne os resultados
        return query.getResultList();
    }

	/**
     * Obtém uma conta pelo seu ID.
     *
     * @param contaID O ID da conta a ser obtida.
     * @return A conta encontrada.
     * @throws ContaNaoEncontradaException Se a conta não for encontrada.
     */
    @Override
    public T obterConta(Long contaID) throws ContaNaoEncontradaException {
        Contas conta = contasRepository.findById(contaID);
        if (conta == null) {
            throw new ContaNaoEncontradaException("Conta não encontrada com ID: " + contaID);
        }
        
        // Certifique-se de fazer o cast de Contas para T
        return (T) conta;
    }

    /**
     * Cria uma nova conta.
     *
     * @param conta A conta a ser criada.
     * @return 
     * @return A conta criada.
     * @throws ContaExistenteException Se uma conta com o mesmo ID já existir.
     */
    @Transactional
    @Override
    public T criarConta(@Valid T conta) throws ContaExistenteException {
        if (contasRepository.findById(conta.getContaID()) != null) {
            throw new ContaExistenteException("Conta já existe com ID: " + conta.getContaID());
        }
        
        entityManager.persist(conta);
        
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
    @Override
    public T atualizarConta(Long contaID, @Valid T conta) throws ContaNaoEncontradaException {
        Contas contaExistente = contasRepository.findById(contaID);
        if (contaExistente == null) {
            throw new ContaNaoEncontradaException("Conta não encontrada com ID: " + contaID);
        }
        
        entityManager.persist(conta);
        
        return conta;
    }

    /**
     * Remove uma conta pelo seu ID.
     *
     * @param contaID O ID da conta a ser removida.
     * @throws ContaNaoEncontradaException Se a conta não for encontrada.
     */
    @Transactional
    @Override
    public void removerConta(Long contaID) throws ContaNaoEncontradaException {
        Contas conta = contasRepository.findById(contaID);
        if (conta == null) {
            throw new ContaNaoEncontradaException("Conta não encontrada com ID: " + contaID);
        }
        contasRepository.delete(conta);
    }
}