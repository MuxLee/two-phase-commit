package net.mux.twophasecommit.listener.strategy;

import jakarta.persistence.EntityManager;
import net.mux.twophasecommit.listener.provider.EntityManagerProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.Set;

public final class InsertTestStrategy<T> extends TransactionalTestStrategy {

    private Set<T> entities;

    public InsertTestStrategy(
            @NonNull final Set<T> entities,
            @NonNull final EntityManagerProvider entityManagerProvider
    ) {
        super(entityManagerProvider);

        this.entities = Collections.unmodifiableSet(entities);
    }

    public void setEntities(@NonNull final Set<T> entities) {
        this.entities = Collections.unmodifiableSet(entities);
    }

    @Override
    protected void testPersist(
            @NonNull final ApplicationContext applicationContext,
            @NonNull final EntityManager entityManager
    ) {
        for (final var entity : this.entities) {
            entityManager.persist(entity);
        }

        entityManager.flush();
        entityManager.close();
    }

    public static <T> InsertTestStrategy<T> of(
            @NonNull final Set<T> queries,
            @NonNull final EntityManagerProvider entityManagerProvider
    ) {
        return new InsertTestStrategy<>(queries, entityManagerProvider);
    }

}