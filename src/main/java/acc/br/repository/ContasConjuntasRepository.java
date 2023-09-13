package acc.br.repository;

import acc.br.model.ContasConjuntas;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

/**
 * Repositório para a entidade Contas Conjunta.
 */
@ApplicationScoped
public class ContasConjuntasRepository implements PanacheRepository<ContasConjuntas> {

}