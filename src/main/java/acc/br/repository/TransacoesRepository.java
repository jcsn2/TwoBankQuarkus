package acc.br.repository;

import acc.br.model.Transacoes;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

/**
 * Repositório para a entidade Transacoes.
 */
@ApplicationScoped
public class TransacoesRepository implements PanacheRepository<Transacoes> {
    
}