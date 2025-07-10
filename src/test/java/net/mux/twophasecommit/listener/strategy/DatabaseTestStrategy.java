package net.mux.twophasecommit.listener.strategy;

import jakarta.persistence.EntityManager;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.test.context.TestContext;

public abstract class DatabaseTestStrategy implements TestStrategy {

    @Override
    public void test(@NonNull final TestContext testContext) {
        final var applicationContext = testContext.getApplicationContext();
        final var entityManager = applicationContext.getBean(EntityManager.class);

        this.test(applicationContext, entityManager);
    }

    abstract protected void test(
            @NonNull final ApplicationContext applicationContext,
            @NonNull final EntityManager entityManager
    );

}