package acc.br.service;

import acc.br.exception.ContaExistenteException;
import acc.br.exception.ParametroConfiguracaoNaoEncontradoException;
import acc.br.exception.PoupancaNaoEncontradaException;
import acc.br.interfaces.Conta;
import acc.br.model.Clientes;
import acc.br.model.Contas;
import acc.br.model.ParametrosConfiguracao;
import acc.br.model.Poupanca;
import acc.br.repository.PoupancaRepository;
import acc.br.util.PoupancaServiceQualifier;
import acc.br.util.TipoConta;
import acc.br.repository.ContasRepository;
import acc.br.repository.ParametrosConfiguracaoRepository;
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
public class PoupancaService extends ContasServiceImpl<Poupanca>{
	
	@Inject
    ParametrosConfiguracaoRepository parametrosConfiguracaoRepository;
    
    @Inject
    PoupancaRepository poupancaRepository;
    
    @Inject
    EntityManager entityManager;

    public PoupancaService(ContasRepository contasRepository) {
        super(contasRepository);
    }

    /**
     * Cria uma nova conta poupança.
     *
     * @param poupanca A conta poupança a ser criada.
     */
	@Transactional
    @Override
    public <T extends Conta> T criarConta(@Valid T conta) throws ContaExistenteException {
        if (contasRepository.findById(conta.getContaID()) != null) {
            throw new ContaExistenteException("Conta poupança já existe com ID: " + conta.getContaID());
        }
        conta.setTipoConta(TipoConta.CONTA_POUPANCA.toString());
        
        super.criarConta(conta);
        
        return conta;
    }

    /**
     * Atualiza uma conta poupança existente.
     *
     * @param id       O ID da conta poupança a ser atualizada.
     * @param poupanca A conta poupança com as atualizações.
     * @throws PoupancaNaoEncontradaException Se a conta poupança não for encontrada.
     */
    @Transactional
    @Override
    public <T extends Conta> T atualizarConta(Long contaID, @Valid T conta) throws PoupancaNaoEncontradaException {
        Contas contaPoupancaExistente = contasRepository.findById(contaID);
        if (contaPoupancaExistente == null) {
            throw new PoupancaNaoEncontradaException("Conta poupança não encontrada com ID: " + contaID);
        }
        conta.setTipoConta(TipoConta.CONTA_POUPANCA.toString());

        super.atualizarConta(contaID, conta);
        
        return conta;
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
     * @throws ParametroConfiguracaoNaoEncontradoException 
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
}