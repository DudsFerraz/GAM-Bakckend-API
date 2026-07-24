package br.org.gam.api.shared.persistence;

import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

public class DefaultBaseRepository<T extends SoftDeletable, ID> extends SimpleJpaRepository<T, ID>
                            implements BaseRepository<T, ID>, ApplicationContextAware {
    private ApplicationContext applicationContext;

    public DefaultBaseRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {

        super(entityInformation, entityManager);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private AuditorAware<UUID> getAuditorAware() {
        try {
            Object auditorAware = this.applicationContext
                    .getBeanProvider(ResolvableType.forClassWithGenerics(AuditorAware.class, UUID.class))
                    .getIfAvailable();
            if (auditorAware instanceof AuditorAware<?> typedAuditorAware) {
                return () -> typedAuditorAware.getCurrentAuditor().map(UUID.class::cast);
            }
            return () -> Optional.empty();
        } catch (Exception e) {
            return () -> Optional.empty();
        }
    }

    @Override
    @Transactional
    public void delete(T entity) {
        final Instant now = Instant.now();
        final UUID deletedBy = getAuditorAware().getCurrentAuditor().orElse(null);

        entity.setDeletedAt(now);
        entity.setDeletedBy(deletedBy);

        save(entity);
    }
}
