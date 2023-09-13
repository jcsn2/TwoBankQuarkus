package acc.br.service;

import acc.br.exception.ParametroConfiguracaoNaoEncontradoException;
import acc.br.exception.PoupancaNaoEncontradaException;
import acc.br.model.ParametrosConfiguracao;
import acc.br.model.Poupanca;
import acc.br.repository.PoupancaRepository;
import acc.br.repository.ParametrosConfiguracaoRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

/**
 * Classe de serviço para a entidade Poupanca.
 */
@ApplicationScoped
public class PoupancaService {

    @Inject
    PoupancaRepository poupancaRepository;

    @Inject
    ParametrosConfiguracaoRepository parametrosConfiguracaoRepository;

    /**
     * Cria uma nova conta poupança.
     *
     * @param poupanca A conta poupança a ser criada.
     */
    @Transactional
    public void criarPoupanca(Poupanca poupanca) {
        poupanca.setDataAbertura(LocalDate.now());
        poupancaRepository.persist(poupanca);
    }

    /**
     * Atualiza uma conta poupança existente.
     *
     * @param id       O ID da conta poupança a ser atualizada.
     * @param poupanca A conta poupança com as atualizações.
     * @throws PoupancaNaoEncontradaException Se a conta poupança não for encontrada.
     */
    @Transactional
    public void atualizarPoupanca(Long id, Poupanca poupanca) throws PoupancaNaoEncontradaException {
        Poupanca entity = poupancaRepository.findById(id);
        if (entity == null) {
            throw new PoupancaNaoEncontradaException("Conta poupança não encontrada com o ID: " + id);
        }

        // Atualize os campos relevantes de acordo com as regras de negócio
        entity.setSaldoPoupanca(poupanca.getSaldoPoupanca());
        entity.setDataAniversario(poupanca.getDataAniversario());

        poupancaRepository.persist(entity);
    }

    /**
     * Obtém uma conta poupança pelo ID.
     *
     * @param id O ID da conta poupança a ser obtida.
     * @return A conta poupança correspondente ao ID.
     * @throws PoupancaNaoEncontradaException Se a conta poupança não for encontrada.
     */
    public Poupanca obterPoupanca(Long id) throws PoupancaNaoEncontradaException {
        Poupanca poupanca = poupancaRepository.findById(id);
        if (poupanca == null) {
            throw new PoupancaNaoEncontradaException("Conta poupança não encontrada com o ID: " + id);
        }
        return poupanca;
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
     * Remove uma conta poupança pelo ID.
     *
     * @param id O ID da conta poupança a ser removida.
     * @throws PoupancaNaoEncontradaException Se a conta poupança não for encontrada.
     */
    @Transactional
    public void removerPoupanca(Long id) throws PoupancaNaoEncontradaException {
        Poupanca poupanca = poupancaRepository.findById(id);
        if (poupanca == null) {
            throw new PoupancaNaoEncontradaException("Conta poupança não encontrada com o ID: " + id);
        }
        poupancaRepository.delete(poupanca);
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
            // Converte a string para BigDecimal
            return new BigDecimal(taxaPoupancaString);
        } catch (NumberFormatException e) {
            // Trate a exceção de conversão caso a string não seja um número válido.
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
    	BigDecimal saldoAtual = poupanca.getSaldoPoupanca();
        BigDecimal taxaPoupanca = obterTaxaPoupancaComoBigDecimal(); 
        LocalDate dataAniversario = LocalDate.parse(poupanca.getDataAniversario());

        // Verifica se a data atual é a data de aniversário da poupança
        LocalDate dataAtual = LocalDate.now();
        if (dataAtual.getDayOfMonth() == dataAniversario.getDayOfMonth()) {
            // Calcula a atualização mensal do saldo
        	BigDecimal atualizacaoMensal = saldoAtual.multiply(taxaPoupanca.divide(new BigDecimal(100), 4, RoundingMode.HALF_UP));
            saldoAtual = saldoAtual.add(atualizacaoMensal);
        }

        return saldoAtual;
    }
}