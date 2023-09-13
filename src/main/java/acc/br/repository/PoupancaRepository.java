package acc.br.repository;

import acc.br.model.Poupanca;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

/**
 * Repositório para a entidade Poupanca.
 */
@ApplicationScoped
public class PoupancaRepository implements PanacheRepository<Poupanca> {
    
}