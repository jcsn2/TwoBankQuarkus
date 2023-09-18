package acc.br.repository;

import acc.br.model.AlertasGastosExcessivos;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

/**
 * Repositório para a entidade AlertasGastosExcessivos.
 */
@ApplicationScoped
public class AlertasGastosExcessivosRepository implements PanacheRepository<AlertasGastosExcessivos> {

}