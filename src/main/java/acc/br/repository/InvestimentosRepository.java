package acc.br.repository;

import acc.br.model.Investimentos;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

/**
 * Repositório para a entidade Investimentos.
 */
@ApplicationScoped
public class InvestimentosRepository implements PanacheRepository<Investimentos> {
    
}