package acc.br.repository;

import acc.br.model.ParametrosConfiguracao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

/**
 * Repositório para a entidade ParametrosConfiguracao.
 */
@ApplicationScoped
public class ParametrosConfiguracaoRepository implements PanacheRepository<ParametrosConfiguracao> {
    
}