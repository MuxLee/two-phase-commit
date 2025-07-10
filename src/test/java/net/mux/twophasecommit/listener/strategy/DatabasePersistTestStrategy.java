package net.mux.twophasecommit.listener.strategy;

import jakarta.persistence.EntityManager;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;

public abstract class DatabasePersistTestStrategy extends DatabaseTestStrategy {

    @Override
    protected void test(
            @NonNull final ApplicationContext applicationContext,
            @NonNull final EntityManager entityManager
    ) {
        final var transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            this.testPersist(applicationContext, entityManager);
            transaction.commit();
        }
        catch (Exception exception) {
            transaction.rollback();

            throw new RuntimeException(exception);
        }
    }

    abstract void testPersist(
            @NonNull final ApplicationContext applicationContext,
            @NonNull final EntityManager entityManager
    );

}