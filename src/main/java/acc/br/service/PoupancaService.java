package acc.br.service;

import acc.br.exception.ContaExistenteException;
import acc.br.exception.ParametroConfiguracaoNaoEncontradoException;
import acc.br.exception.PoupancaNaoEncontradaException;
import acc.br.model.ContasConjuntas;
import acc.br.model.ParametrosConfiguracao;
import acc.br.model.Poupanca;
import acc.br.repository.ParametrosConfiguracaoRepository;
import acc.br.repository.PoupancaRepository;
import acc.br.util.PoupancaServiceQualifier;
import acc.br.util.TipoConta;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

/**
 * Classe de serviço para a entidade Poupanca.
 */
@ApplicationScoped
@PoupancaServiceQualifier
public class PoupancaService{
    
    @Inject
    ParametrosConfiguracaoRepository parametrosConfiguracaoRepository;
    
    @Inject
    PoupancaRepository poupancaRepository;
    
    @Inject
    EntityManager entityManager;

    /**
     * Cria uma nova conta poupança.
     *
     * @param contaPoupanca A conta poupança a ser criada.
     * @return A conta poupança criada.
     * @throws ContaExistenteException Se uma conta com o mesmo ID já existir.
     */
    @Transactional
    public Poupanca criarConta(@Valid Poupanca contaPoupanca) throws ContaExistenteException {
        if (poupancaRepository.findById(contaPoupanca.getPoupancaID()) != null) {
            throw new ContaExistenteException("Conta poupança já existe com ID: " + contaPoupanca.getPoupancaID());
        }
        contaPoupanca.setTipoConta(TipoConta.CONTA_POUPANCA.toString());
        		
        Poupanca contasGerenciada = entityManager.merge(contaPoupanca); // Mescla a entidade no contexto de persistência
        entityManager.persist(contasGerenciada); // Persiste a entidade
        
        return contaPoupanca;
    }

    /**
     * Atualiza uma conta poupança existente.
     *
     * @param id       O ID da conta poupança a ser atualizada.
     * @param poupanca A conta poupança com as atualizações.
     * @throws PoupancaNaoEncontradaException Se a conta poupança não for encontrada.
     */
    @Transactional
    public Poupanca atualizarConta(Long contaID, @Valid Poupanca contaPoupanca) throws PoupancaNaoEncontradaException {
        Poupanca contaPoupancaExistente = poupancaRepository.findById(contaID);
        if (contaPoupancaExistente == null) {
            throw new PoupancaNaoEncontradaException("Conta poupança não encontrada com ID: " + contaID);
        }
        contaPoupanca.setTipoConta(TipoConta.CONTA_POUPANCA.toString());

        Poupanca contasGerenciada = entityManager.merge(contaPoupanca); // Mescla a entidade no contexto de persistência
        entityManager.persist(contasGerenciada); // Persiste a entidade
        
        return contaPoupanca;
    }

    /**
     * Lista todas as contas poupança.
     *
     * @return Uma lista de todas as contas poupança.
     */
    public List<Poupanca> listarPoupancas() {
        return poupancaRepository.listAll();
    }
    
    /**
     * Obtém a taxa de poupança da tabela de parâmetros de configuração e a converte para BigDecimal.
     *
     * @return A taxa de poupança como um BigDecimal.
     * @throws ParametroConfiguracaoNaoEncontradoException Se a taxa de poupança não for encontrada na tabela de configuração.
     */
    public BigDecimal obterTaxaPoupancaComoBigDecimal() throws ParametroConfiguracaoNaoEncontradoException {

        ParametrosConfiguracao parametrosConfiguracao = parametrosConfiguracaoRepository.find("nomeParametro", "TaxaPoupanca").firstResult();

        if (parametrosConfiguracao == null) {
            throw new ParametroConfiguracaoNaoEncontradoException("Parâmetro 'TaxaPoupanca' não encontrado na tabela ParametrosConfiguracao.");
        }

        String taxaPoupancaString = parametrosConfiguracao.getValorParametro();

        try {
            return new BigDecimal(taxaPoupancaString);
        } catch (NumberFormatException e) {
            throw new ParametroConfiguracaoNaoEncontradoException("Erro ao converter 'TaxaPoupanca' para BigDecimal: " + e.getMessage());
        }
    }

    /**
     * Calcula a atualização mensal do saldo da poupança com base na taxa de poupança.
     *
     * @param poupanca A conta poupança para a qual a atualização será calculada.
     * @return O novo saldo da poupança após a atualização.
     * @throws ParametroConfiguracaoNaoEncontradoException Se a taxa de poupança não for encontrada na tabela de configuração.
     */
    public BigDecimal calcularAtualizacaoMensal(Poupanca poupanca) throws ParametroConfiguracaoNaoEncontradoException {
        BigDecimal saldoAtual = poupanca.getSaldo();
        BigDecimal taxaPoupanca = obterTaxaPoupancaComoBigDecimal(); 
        LocalDate dataAniversario = LocalDate.parse(poupanca.getDataAniversario());

        // Verifica se a data atual é a data de aniversário da poupança
        LocalDate dataAtual = LocalDate.now();
        if (dataAtual.getDayOfMonth() == dataAniversario.getDayOfMonth()) {
            // Calcula a atualização mensal do saldo
            BigDecimal atualizacaoMensal = saldoAtual.multiply(taxaPoupanca.divide(new BigDecimal(100), 4, RoundingMode.HALF_UP));
            saldoAtual = saldoAtual.add(atualizacaoMensal);
            
            poupanca.setSaldo(saldoAtual);
        }

        return saldoAtual;
    }

	public void removerPoupanca(Long id) {
		poupancaRepository.deleteById(id);
	}
}
