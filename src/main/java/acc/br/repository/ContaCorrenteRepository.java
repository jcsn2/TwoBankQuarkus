package acc.br.repository;

import acc.br.model.ContaCorrente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

/**
 * Repositório para a entidade ContaCorrente.
 */
@ApplicationScoped
public class ContaCorrenteRepository implements PanacheRepository<ContaCorrente> {
    
}