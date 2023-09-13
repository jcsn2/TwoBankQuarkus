package acc.br.repository;

import acc.br.model.HistoricoTransacoes;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

/**
 * Repositório para a entidade HistoricoTransacoes.
 */
@ApplicationScoped
public class HistoricoTransacoesRepository implements PanacheRepository<HistoricoTransacoes> {

}