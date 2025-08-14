package net.mux.twophasecommit.listener.strategy;

import jakarta.persistence.EntityManager;
import net.mux.twophasecommit.listener.provider.EntityManagerProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;

public abstract class TransactionalTestStrategy extends DatabaseTestStrategy {

    private final EntityManagerProvider entityManagerProvider;

    protected TransactionalTestStrategy(@NonNull final EntityManagerProvider entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    @Override
    protected void test(@NonNull final ApplicationContext applicationContext) {
        final var entityManager = this.entityManagerProvider.getBean(applicationContext);
        final var transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            this.testPersist(applicationContext, entityManager);
            transaction.commit();
        }
        catch (final Exception exception) {
            transaction.rollback();

            throw new RuntimeException(exception);
        }
        finally {
            entityManager.close();
        }
    }

    abstract void testPersist(
            @NonNull final ApplicationContext applicationContext,
            @NonNull final EntityManager entityManager
    );

}