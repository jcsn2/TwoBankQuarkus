package acc.br.service;

import acc.br.exception.ClienteNaoEncontradoException;
import acc.br.exception.InvestimentoNaoEncontradoException;
import acc.br.model.Clientes;
import acc.br.model.Emprestimos;
import acc.br.model.Investimentos;
import acc.br.repository.ClientesRepository;
import acc.br.repository.InvestimentosRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

/**
 * Classe de serviço para a entidade Investimentos.
 */
@ApplicationScoped
public class InvestimentosService {

    
    @Inject
    ClientesRepository clientesRepository;

    @Inject
    InvestimentosRepository investimentosRepository;

    @Inject
    EntityManager entityManager;


    /**
     * Cria um novo registro de investimento.
     *
     * @param investimento O objeto Investimentos a ser criado.
     */
    @Transactional
    public void criarInvestimento(Investimentos investimento) {
        investimento.setTaxaRetorno(investimento.getTaxaRetorno());
        investimento.setDataInicio(LocalDate.now());
        investimento.setSaldoInvestimento(investimento.getSaldoInicial());

        
        Clientes clienteExistente = clientesRepository.findById(investimento.getClienteID());
        if (clienteExistente == null) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado com ID: " + investimento.getClienteID());
        }

        Investimentos investimentoGerenciado = entityManager.merge(investimento); // Mescla a entidade no contexto de persistência
        entityManager.persist(investimentoGerenciado); // Persiste a entidad
    }

    /**
     * Atualiza um registro de investimento existente.
     *
     * @param id           O ID do registro de investimento a ser atualizado.
     * @param investimento O objeto Investimentos com as atualizações.
     * @throws InvestimentoNaoEncontradoException Se o registro de investimento não for encontrado.
     */
    @Transactional
    public void atualizarInvestimento(Long id, Investimentos investimento)
            throws InvestimentoNaoEncontradoException {
        Investimentos entity = investimentosRepository.findById(id);
        if (entity == null) {
            throw new InvestimentoNaoEncontradoException("Registro de investimento não encontrado com o ID: " + id);
        }
        
        Clientes clienteExistente = clientesRepository.findById(investimento.getClienteID());
        if (clienteExistente == null) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado com ID: " + investimento.getClienteID());
        }

        // Atualize os campos relevantes de acordo com as regras de negócio
        entity.setNomeInvestimento(investimento.getNomeInvestimento());
        entity.setTipoInvestimento(investimento.getTipoInvestimento());
        entity.setSaldoInicial(investimento.getSaldoInicial());
        entity.setDataInicio(investimento.getDataInicio());

         Investimentos investimentoGerenciado = entityManager.merge(entity); // Mescla a entidade no contexto de persistência
        entityManager.persist(investimentoGerenciado); // Persiste a entidade;
    }

    /**
     * Obtém um registro de investimento pelo ID.
     *
     * @param id O ID do registro de investimento a ser obtido.
     * @return O registro de investimento correspondente ao ID.
     * @throws InvestimentoNaoEncontradoException Se o registro de investimento não for encontrado.
     */
    public Investimentos obterInvestimento(Long id) throws InvestimentoNaoEncontradoException {
        Investimentos investimento = investimentosRepository.findById(id);
        if (investimento == null) {
            throw new InvestimentoNaoEncontradoException("Registro de investimento não encontrado com o ID: " + id);
        }
        return investimento;
    }

    /**
     * Lista todos os registros de investimento.
     *
     * @return Uma lista de todos os registros de investimento.
     */
    public List<Investimentos> listarInvestimentos() {
        return investimentosRepository.listAll();
    }

    /**
     * Remove um registro de investimento pelo ID.
     *
     * @param id O ID do registro de investimento a ser removido.
     * @throws InvestimentoNaoEncontradoException Se o registro de investimento não for encontrado.
     */
    @Transactional
    public void removerInvestimento(Long id) throws InvestimentoNaoEncontradoException {
        Investimentos investimento = investimentosRepository.findById(id);
        if (investimento == null) {
            throw new InvestimentoNaoEncontradoException("Registro de investimento não encontrado com o ID: " + id);
        }
        investimentosRepository.delete(investimento);
    }

    /**
     * Calcula o valor de retorno do investimento com base na taxa de retorno.
     *
     * @param investimento O objeto Investimentos para o qual o cálculo será realizado.
     * @return O valor de retorno do investimento.
     */
    public BigDecimal calcularRetornoInvestimento(Investimentos investimento) {
        BigDecimal taxaRetorno = investimento.getTaxaRetorno();
        BigDecimal saldoInicial = investimento.getSaldoInicial();

        return saldoInicial.multiply(taxaRetorno.divide(new BigDecimal(100)))
                .setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Lista todos os registros de investimento de um cliente.
     *
     * @param clienteID O ID do cliente para o qual os investimentos serão listados.
     * @return Uma lista de todos os registros de investimento do cliente.
     */
    public List<Investimentos> listarInvestimentosPorCliente(Integer clienteID) {
        return investimentosRepository.list("clienteID", clienteID);
    }

}