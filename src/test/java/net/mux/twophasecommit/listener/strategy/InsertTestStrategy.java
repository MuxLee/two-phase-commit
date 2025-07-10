package net.mux.twophasecommit.listener.strategy;

import jakarta.persistence.EntityManager;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.Set;

public final class InsertTestStrategy<T> extends DatabasePersistTestStrategy {

    private Set<T> entities;

    public InsertTestStrategy(@NonNull final Set<T> entities) {
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
    }

    public static <T> TestStrategy of(@NonNull final Set<T> queries) {
        return new InsertTestStrategy<>(queries);
    }

    @SafeVarargs
    public static <T> TestStrategy of(@NonNull final T... entities) {
        return of(Set.of(entities));
    }

}