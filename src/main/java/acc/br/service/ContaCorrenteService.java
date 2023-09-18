package acc.br.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import acc.br.exception.ContaCorrenteNaoEncontradaException;
import acc.br.exception.ContaExistenteException;
import acc.br.exception.ContaNaoEncontradaException;
import acc.br.model.ContaCorrente;
import acc.br.repository.ContaCorrenteRepository;
import acc.br.util.TipoConta;

/**
 * Serviço para operações relacionadas a contas correntes bancárias.
 */
@ApplicationScoped 
public class ContaCorrenteService{
    
    @Inject
    ContaCorrenteRepository contaCorrenteRepository;
    
    @Inject
    EntityManager entityManager;

    /**
     * Cria uma nova conta corrente.
     *
     * @param contaCorrente A conta corrente a ser criada.
     * @return A conta corrente criada.
     * @throws ContaExistenteException Se uma conta com o mesmo ID já existir.
     */
    @Transactional
    public ContaCorrente criarConta(@Valid ContaCorrente contaCorrente) throws ContaExistenteException {
        if (contaCorrenteRepository.findById(contaCorrente.getcontaCorrenteID()) != null) {
            throw new ContaExistenteException("Conta corrente já existe com ID: " + contaCorrente.getcontaCorrenteID());
        }
        contaCorrente.setTipoConta(TipoConta.CONTA_CORRENTE.toString());
        
        ContaCorrente contasGerenciada = entityManager.merge(contaCorrente); // Mescla a entidade no contexto de persistência
        entityManager.persist(contasGerenciada); // Persiste a entidade
        
        return contaCorrente;
    }

    /**
     * Atualiza uma conta corrente existente.
     *
     * @param id             O ID da conta corrente a ser atualizada.
     * @param contaCorrente  A conta corrente com as atualizações.
     * @return A conta corrente atualizada.
     * @throws ContaCorrenteNaoEncontradaException Se a conta corrente não for encontrada.
     */
    @Transactional
    public ContaCorrente atualizarConta(Long contaID, @Valid ContaCorrente contaCorrente) throws ContaNaoEncontradaException {
        ContaCorrente contaCorrenteExistente = contaCorrenteRepository.findById(contaID);
        if (contaCorrenteExistente == null) {
            throw new ContaCorrenteNaoEncontradaException("Conta corrente não encontrada com ID: " + contaID);
        }
        
        contaCorrente.setTipoConta(TipoConta.CONTA_CORRENTE.toString());

        ContaCorrente contasGerenciada = entityManager.merge(contaCorrente); // Mescla a entidade no contexto de persistência
        entityManager.persist(contasGerenciada); // Persiste a entidade
        
        return contaCorrente;
    }

    /**
     * Lista todas as contas correntes.
     *
     * @return Uma lista de todas as contas correntes.
     */
    public List<ContaCorrente> listarContasCorrentes() {
        return contaCorrenteRepository.listAll();
    }

	public void removerContaCorrente(Long id) {
		contaCorrenteRepository.deleteById(id);
	}
}
