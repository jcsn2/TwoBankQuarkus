package acc.br.service;

import acc.br.exception.EmprestimoExistenteException;
import acc.br.exception.EmprestimoNaoEncontradoException;
import acc.br.model.Clientes;
import acc.br.model.Emprestimos;
import acc.br.repository.EmprestimosRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Serviço para gerenciar empréstimos em um sistema bancário.
 */
@ApplicationScoped
public class EmprestimosService {

    @Inject
    EmprestimosRepository emprestimosRepository;

    @Inject
    EntityManager entityManager;


    /**
     * Cria um novo empréstimo no sistema.
     *
     * @param emprestimo O empréstimo a ser criado.
     * @return O empréstimo criado.
     */
    @Transactional
    public Emprestimos criarEmprestimo(Emprestimos emprestimo) {
        validarEmprestimo(emprestimo);

        List<Emprestimos> emprestimosExistente = emprestimosRepository.findByClienteIDAndStatus(emprestimo.getClienteID(), emprestimo.getStatus());
        if (!emprestimosExistente.isEmpty()) {
            throw new EmprestimoExistenteException("Empréstimo já existente para este cliente com o mesmo status");
        }
        Emprestimos emprestimoGerenciado = entityManager.merge(emprestimo); // Mescla a entidade no contexto de persistÃªncia
        entityManager.persist(emprestimoGerenciado); // Persiste a entidade
        return emprestimo;
    }


    /**
     * Atualiza um empréstimo existente no sistema, aplicando a taxa de juros mensal.
     *
     * @param id         O ID do empréstimo a ser atualizado.
     * @param emprestimo Os dados atualizados do empréstimo.
     * @return O empréstimo atualizado.
     * @throws EmprestimoNaoEncontradoException Se o empréstimo não for encontrado.
     */
    @Transactional
    public Emprestimos atualizarEmprestimo(Long id, Emprestimos emprestimo) {
       // VerificaExistenciaEmprestimo(emprestimo); //Desnecessário a verificacão é feita abaixo
        validarEmprestimo(emprestimo);

        Emprestimos emprestimoExistente = emprestimosRepository.findById(id);
        if (emprestimoExistente == null) {
            throw new EmprestimoNaoEncontradoException("Empréstimo não encontrado");
        }

        // Calcular o novo valor das parcelas com base na taxa de juros mensal
        BigDecimal taxaJurosMensal = emprestimoExistente.getTaxaJuros().divide(BigDecimal.valueOf(100), 5, RoundingMode.HALF_UP).divide(BigDecimal.valueOf(12), 5, RoundingMode.HALF_UP);
        int prazoMeses = emprestimoExistente.getPrazoMeses();
        BigDecimal valorEmprestimo = emprestimo.getValorEmprestimo();
        
        BigDecimal valorParcelas = calcularValorParcelas(valorEmprestimo, taxaJurosMensal, prazoMeses);

        // Atualizar os campos do empréstimo existente
        emprestimoExistente.setValorEmprestimo(valorEmprestimo);
        emprestimoExistente.setTaxaJuros(emprestimo.getTaxaJuros());
        emprestimoExistente.setDataSolicitacao(emprestimo.getDataSolicitacao());
        emprestimoExistente.setStatus(emprestimo.getStatus());
        emprestimoExistente.setPrazoMeses(emprestimo.getPrazoMeses());
        emprestimoExistente.setValorParcelas(valorParcelas);

        Emprestimos emprestimoGerenciado = entityManager.merge(emprestimoExistente); // Mescla a entidade no contexto de persistÃªncia
        entityManager.persist(emprestimoGerenciado); // Persiste a entidade
        return emprestimoExistente;
    }

    /**
     * Calcula o valor das parcelas de um empréstimo com base no valor do empréstimo, na taxa de juros mensal e no prazo em meses.
     *
     * @param valorEmprestimo    O valor total do empréstimo.
     * @param taxaJurosMensal    A taxa de juros mensal (em formato decimal, por exemplo, 0.05 para 5%).
     * @param prazoMeses         O prazo em meses para pagamento do empréstimo.
     * @return O valor das parcelas mensais do empréstimo.
     */
    private BigDecimal calcularValorParcelas(BigDecimal valorEmprestimo, BigDecimal taxaJurosMensal, int prazoMeses) {
        BigDecimal umMaisTaxaJurosMensalElevadoPrazo = BigDecimal.ONE.add(taxaJurosMensal).pow(prazoMeses);
        return valorEmprestimo.multiply(taxaJurosMensal).multiply(umMaisTaxaJurosMensalElevadoPrazo).divide(umMaisTaxaJurosMensalElevadoPrazo.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
    }


    /**
     * Deleta um empréstimo do sistema.
     *
     * @param id O ID do empréstimo a ser deletado.
     */
    @Transactional
    public void deletarEmprestimo(Long id) {
        Emprestimos emprestimoExistente = emprestimosRepository.findById(id);
        if (emprestimoExistente == null) {
            throw new EmprestimoNaoEncontradoException("Empréstimo não encontrado");
        }

        emprestimosRepository.delete(emprestimoExistente);
    }

    /**
     * Lista todos os empréstimos cadastrados no sistema.
     *
     * @return Uma lista de empréstimos.
     */
    public List<Emprestimos> listarEmprestimos() {
        return emprestimosRepository.listAll();
    }

    /**
     * Busca um empréstimo por ID.
     *
     * @param id O ID do empréstimo a ser buscado.
     * @return O empréstimo encontrado ou null se não encontrado.
     */
    public Emprestimos buscarEmprestimoPorId(Long id) {
        return emprestimosRepository.findById(id);
    }

    /**
     * Valida os campos de um empréstimo de acordo com as regras de negócio do sistema bancário.
     *
     * @param emprestimo O empréstimo a ser validado.
     * @throws IllegalArgumentException Se algum dos campos do empréstimo não estiver de acordo com as regras de negócio.
     */
    private void validarEmprestimo(Emprestimos emprestimo) {
        // if (emprestimo.getValorEmprestimo() == null || emprestimo.getValorEmprestimo().compareTo(BigDecimal.ZERO) <= 0) {
        //     throw new IllegalArgumentException("O valor do empréstimo deve ser maior que zero.");
        // }

        if (emprestimo.getTaxaJuros() == null || emprestimo.getTaxaJuros().compareTo(BigDecimal.ZERO) < 0 || emprestimo.getTaxaJuros().compareTo(new BigDecimal(100)) > 0) {
            throw new IllegalArgumentException("A taxa de juros deve estar entre 0% e 100%.");
        }

        if (emprestimo.getDataSolicitacao() == null) {
            throw new IllegalArgumentException("A data de solicitação do empréstimo é obrigatória.");
        }

        if (emprestimo.getStatus() == null || emprestimo.getStatus().isEmpty()) {
            throw new IllegalArgumentException("O status do empréstimo é obrigatório.");
        }

        if (emprestimo.getClienteID() <= 0) {
            throw new IllegalArgumentException("O ID do cliente associado ao empréstimo deve ser válido.");
        }

        if (emprestimo.getPrazoMeses() <= 0) {
            throw new IllegalArgumentException("O prazo em meses deve ser maior que zero.");
        }

        if (emprestimo.getValorParcelas() == null || emprestimo.getValorParcelas().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor das parcelas deve ser maior que zero.");
        }
    }
    private void VerificaExistenciaEmprestimo(Emprestimos emprestimo) {
        if (emprestimo.getValorEmprestimo() == null || emprestimo.getValorEmprestimo().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do empréstimo deve ser maior que zero.");
        }
    }

}
