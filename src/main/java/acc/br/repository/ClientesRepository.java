package acc.br.repository;

import acc.br.model.Clientes;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

/**
 * Repositório para a entidade Clientes.
 */
@ApplicationScoped
public class ClientesRepository implements PanacheRepository<Clientes> {

}