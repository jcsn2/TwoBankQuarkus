package acc.br.repository;

import java.util.List;

import acc.br.model.Emprestimos;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

/**
 * Repositório para a entidade Emprestimos.
 */
@ApplicationScoped
public class EmprestimosRepository implements PanacheRepository<Emprestimos> {

    /**
     * Encontra empréstimos por ID do cliente e status.
     *
     * @param clienteID O ID do cliente.
     * @param status O status do empréstimo.
     * @return Uma lista de empréstimos que correspondem aos critérios.
     */
    public List<Emprestimos> findByClienteIDAndStatus(int clienteID, String status) {
        return list("clienteID = ?1 and status = ?2", clienteID, status);
    }
    
    /**
     * Salva um empréstimo no banco de dados.
     *
     * @param emprestimo O empréstimo a ser salvo.
     * @return O empréstimo salvo.
     */
    public Emprestimos salvarEmprestimo(Emprestimos emprestimo) {
        persist(emprestimo);
        return emprestimo;
    }
    
}