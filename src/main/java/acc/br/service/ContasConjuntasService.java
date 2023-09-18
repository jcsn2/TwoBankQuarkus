package acc.br.service;

import acc.br.model.ContasConjuntas;
import acc.br.repository.ContasConjuntasRepository;
import acc.br.util.TipoConta;
import acc.br.exception.ContaConjuntaExistenteException;
import acc.br.exception.ContaConjuntaNaoEncontradaException;
import acc.br.exception.ContaExistenteException;
import acc.br.exception.ContaNaoEncontradaException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Serviço para operações relacionadas a contas conjuntas bancárias.
 */
@ApplicationScoped
public class ContasConjuntasService {

    @Inject
    ContasConjuntasRepository contasConjuntasRepository;
    
    @Inject
    EntityManager entityManager;
    
    public ContasConjuntasService() {
        // Construtor sem argumentos
    }

    public ContasConjuntasService(ContasConjuntasRepository contasConjuntasRepository) {
        super();
        this.contasConjuntasRepository = contasConjuntasRepository;
    }

    /**
     * Lista todas as contas conjuntas bancárias.
     *
     * @return Uma lista de contas conjuntas bancárias.
     */
    public List<ContasConjuntas> listarContasConjuntas() {
        return contasConjuntasRepository.listAll();
    }

    /**
     * Cria uma nova conta conjunta bancária.
     *
     * @param contaConjunta A conta conjunta bancária a ser criada.
     * @return A conta conjunta bancária criada.
     * @throws ContaConjuntaExistenteException se a conta conjunta já existir.
     */
    @Transactional
    public ContasConjuntas criarConta(@Valid ContasConjuntas contaConjunta) throws ContaExistenteException {
        if (contasConjuntasRepository.findById(contaConjunta.getContaConjuntaID()) != null) {
            throw new ContaConjuntaExistenteException("Conta conjunta já existe com ID: " + contaConjunta.getContaConjuntaID());
        }
        validarContaConjunta(contaConjunta);
        
        contaConjunta.setTipoConta(TipoConta.CONTA_CONJUNTA.toString());
        
        ContasConjuntas contasGerenciada = entityManager.merge(contaConjunta); // Mescla a entidade no contexto de persistência
        entityManager.persist(contasGerenciada); // Persiste a entidade
        
        return contaConjunta;
    }

    /**
     * Atualiza uma conta conjunta bancária existente.
     *
     * @param contaConjuntaID O ID da conta conjunta a ser atualizada.
     * @param contaConjunta   A conta conjunta bancária atualizada.
     * @return A conta conjunta bancária atualizada.
     * @throws ContaConjuntaNaoEncontradaException se a conta conjunta não for encontrada.
     */
    @Transactional
    public ContasConjuntas atualizarConta(Long contaID, @Valid ContasConjuntas contaConjunta) throws ContaNaoEncontradaException {
        ContasConjuntas contaConjuntaExistente = contasConjuntasRepository.findById(contaID);
        if (contaConjuntaExistente == null) {
            throw new ContaConjuntaNaoEncontradaException("Conta conjunta não encontrada com ID: " + contaID);
        }
        
        validarContaConjunta(contaConjunta);

        contaConjunta.setTipoConta(TipoConta.CONTA_CONJUNTA.toString());
        
        ContasConjuntas contasGerenciada = entityManager.merge(contaConjunta); // Mescla a entidade no contexto de persistência
        entityManager.persist(contasGerenciada); // Persiste a entidade
        
        return contaConjunta;
    }

    /**
     * Valida uma conta conjunta bancária de acordo com as regras de negócios.
     *
     * @param contaConjunta A conta conjunta bancária a ser validada.
     */
    public void validarContaConjunta(ContasConjuntas contaConjuntas) {
        validarDataAbertura(contaConjuntas.getDataAbertura());
        validarSaldo(contaConjuntas.getSaldo());
        validarTipoConta(contaConjuntas.getTipoConta());
        validarStatusConta(contaConjuntas.getStatusConta());
        validarAtiva(contaConjuntas.isAtiva());
    }

    /**
     * Valida a data de abertura de uma conta conjunta.
     *
     * @param dataAbertura A data de abertura da conta conjunta.
     */
    public void validarDataAbertura(LocalDate dataAbertura) {
        if (dataAbertura == null || dataAbertura.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de abertura inválida para a conta conjunta");
        }
    }

    /**
     * Valida o saldo de uma conta conjunta.
     *
     * @param saldo O saldo da conta conjunta.
     */
    public void validarSaldo(BigDecimal saldo) {
        if (saldo == null || saldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Saldo inválido para a conta conjunta");
        }
    }

    /**
     * Valida o tipo da conta conjunta.
     *
     * @param tipoConta O tipo da conta conjunta.
     */
    public void validarTipoConta(String tipoConta) {
        boolean tipoContaValido = false;
        
        for (TipoConta tipo : TipoConta.values()) {
            if (tipo.name().equals(tipoConta)) {
                tipoContaValido = true;
                break;
            }
        }

        if (!tipoContaValido) {
            throw new IllegalArgumentException("Tipo de conta inválido.");
        }
    }

    /**
     * Valida o status da conta conjunta.
     *
     * @param statusConta O status da conta conjunta.
     */
    public void validarStatusConta(String statusConta) {
        if (!statusConta.equals("Ativa") && !statusConta.equals("Inativa")) {
            throw new IllegalArgumentException("Status de conta inválido.");
        }
    }

    /**
     * Valida se a conta conjunta está ativa.
     *
     * @param ativa Indica se a conta conjunta está ativa.
     */
    public void validarAtiva(boolean ativa) {
        if (!ativa) {
            throw new IllegalArgumentException("A conta conjunta deve estar ativa.");
        }
    }

	public void removerContasConjunta(Long id) {
		contasConjuntasRepository.deleteById(id);
	}
}