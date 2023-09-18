package acc.br.repository;

import acc.br.model.Contas;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

/**
 * Repositório para a entidade Contas.
 */
@ApplicationScoped
public class ContasRepository implements PanacheRepository<Contas> {

}