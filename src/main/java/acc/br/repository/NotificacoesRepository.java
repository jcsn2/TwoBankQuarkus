package acc.br.repository;

import acc.br.model.Notificacoes;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

/**
 * Repositório para a entidade Notificacoes.
 */
@ApplicationScoped
public class NotificacoesRepository implements PanacheRepository<Notificacoes> {
    
}