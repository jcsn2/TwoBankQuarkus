package acc.br.service;

import acc.br.model.ContasConjuntas;
import acc.br.repository.ContasConjuntasRepository;
import acc.br.exception.ContaConjuntaExistenteException;
import acc.br.exception.ContaConjuntaNaoEncontradaException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

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

    /**
     * Lista todas as contas conjuntas bancárias.
     *
     * @return Uma lista de contas conjuntas bancárias.
     */
    public List<ContasConjuntas> listarContasConjuntas() {
        return contasConjuntasRepository.listAll();
    }

    /**
     * Obtém uma conta conjunta bancária pelo seu ID.
     *
     * @param contaConjuntaID O ID da conta conjunta a ser obtida.
     * @return A conta conjunta bancária correspondente.
     * @throws ContaConjuntaNaoEncontradaException se a conta conjunta não for encontrada.
     */
    public ContasConjuntas obterContaConjunta(Long contaConjuntaID) {
        ContasConjuntas contaConjunta = contasConjuntasRepository.findById(contaConjuntaID);
        if (contaConjunta == null) {
            throw new ContaConjuntaNaoEncontradaException("Conta conjunta não encontrada com ID: " + contaConjuntaID);
        }
        return contaConjunta;
    }

    /**
     * Cria uma nova conta conjunta bancária.
     *
     * @param contaConjunta A conta conjunta bancária a ser criada.
     * @return A conta conjunta bancária criada.
     * @throws ContaConjuntaExistenteException se a conta conjunta já existir.
     */
    @Transactional
    public ContasConjuntas criarContaConjunta(ContasConjuntas contaConjunta) {
        if (contasConjuntasRepository.findById(contaConjunta.getContaConjuntaID()) != null) {
            throw new ContaConjuntaExistenteException("Conta conjunta já existe com ID: " + contaConjunta.getContaConjuntaID());
        }
        validarContaConjunta(contaConjunta);
        contasConjuntasRepository.persist(contaConjunta);
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
    public ContasConjuntas atualizarContaConjunta(Long contaConjuntaID, ContasConjuntas contaConjunta) {
        ContasConjuntas contaConjuntaExistente = contasConjuntasRepository.findById(contaConjuntaID);
        if (contaConjuntaExistente == null) {
            throw new ContaConjuntaNaoEncontradaException("Conta conjunta não encontrada com ID: " + contaConjuntaID);
        }
        validarContaConjunta(contaConjunta);
        contaConjunta.setContaConjuntaID(contaConjuntaID);
        contasConjuntasRepository.persist(contaConjunta);
        return contaConjunta;
    }

    /**
     * Remove uma conta conjunta bancária existente.
     *
     * @param contaConjuntaID O ID da conta conjunta a ser removida.
     * @throws ContaConjuntaNaoEncontradaException se a conta conjunta não for encontrada.
     */
    @Transactional
    public void removerContaConjunta(Long contaConjuntaID) {
        ContasConjuntas contaConjunta = contasConjuntasRepository.findById(contaConjuntaID);
        if (contaConjunta == null) {
            throw new ContaConjuntaNaoEncontradaException("Conta conjunta não encontrada com ID: " + contaConjuntaID);
        }
        contaConjunta.delete();
    }

    /**
     * Valida uma conta conjunta bancária de acordo com as regras de negócios.
     *
     * @param contaConjunta A conta conjunta bancária a ser validada.
     */
    public void validarContaConjunta(ContasConjuntas contaConjunta) {
        validarDataAbertura(contaConjunta.getDataAbertura());
        validarSaldo(contaConjunta.getSaldo());
        validarTitulares(contaConjunta.getTitulares());
        validarTipoConta(contaConjunta.getTipoConta());
        validarStatusConta(contaConjunta.getStatusConta());
        validarAtiva(contaConjunta.isAtiva());
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
     * Valida a lista de titulares de uma conta conjunta.
     *
     * @param titulares A lista de titulares da conta conjunta.
     */
    public void validarTitulares(String titulares) {
        // Exemplo de validação: Verifica se a lista de titulares não está vazia.
        if (titulares == null || titulares.isEmpty()) {
            throw new IllegalArgumentException("A lista de titulares não pode estar vazia.");
        }
    }

    /**
     * Valida o tipo da conta conjunta.
     *
     * @param tipoConta O tipo da conta conjunta.
     */
    public void validarTipoConta(String tipoConta) {
        // Exemplo de validação: Verifica se o tipo de conta é válido.
        if (!tipoConta.equals("Conta Poupança") && !tipoConta.equals("Conta Corrente")) {
            throw new IllegalArgumentException("Tipo de conta inválido.");
        }
    }

    /**
     * Valida o status da conta conjunta.
     *
     * @param statusConta O status da conta conjunta.
     */
    public void validarStatusConta(String statusConta) {
        // Exemplo de validação: Verifica se o status da conta é válido.
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
        // Exemplo de validação: Verifica se a conta está ativa e possui saldo positivo.
        if (!ativa) {
            throw new IllegalArgumentException("A conta conjunta deve estar ativa.");
        }
    }
}
