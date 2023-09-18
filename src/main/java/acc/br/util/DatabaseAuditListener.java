package acc.br.util;

import io.quarkus.runtime.StartupEvent;

import java.time.LocalDateTime;

import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;

import acc.br.model.AuditoriaBancoDados;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class DatabaseAuditListener implements PostInsertEventListener, PostUpdateEventListener, PostDeleteEventListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    EntityManager entityManager;

    @Override
    public void onPostInsert(PostInsertEvent event) {
        createAuditRecord(event.getEntity(), "INSERT");
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        createAuditRecord(event.getEntity(), "UPDATE");
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        createAuditRecord(event.getEntity(), "DELETE");
    }

    private void createAuditRecord(Object entity, String action) {
        AuditoriaBancoDados auditoriaBd = new AuditoriaBancoDados();
        auditoriaBd.setNomeEntidade(entity.getClass().getName());
        auditoriaBd.setAcao(action);
        auditoriaBd.setDataHora(LocalDateTime.now());

        entityManager.persist(auditoriaBd);
    }

    // Método de inicialização
    @Transactional
    void onStart(@Observes StartupEvent event) {
        // Inicialização do ouvinte de eventos
    }

	@Override
	public boolean requiresPostCommitHandling(EntityPersister persister) {
		return false;
	}

	@Override
	public boolean requiresPostCommitHanding(EntityPersister persister) {
		return false;
	}
}

