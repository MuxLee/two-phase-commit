package net.mux.twophasecommit.listener.strategy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.test.context.TestContext;

public abstract class DatabaseTestStrategy implements TestStrategy {

    private String entityManagerFactoryBeanName;

    public void setEntityManagerFactoryBeanName(@NonNull final String entityManagerFactoryBeanName) {
        this.entityManagerFactoryBeanName = entityManagerFactoryBeanName;
    }

    @Override
    public void test(@NonNull final TestContext testContext) {
        if (this.entityManagerFactoryBeanName == null) {
            throw new IllegalArgumentException("'entityManagerFactoryBeanName' must not be null.");
        }

        if (this.entityManagerFactoryBeanName.isBlank()) {
            throw new IllegalArgumentException("'entityManagerFactoryBeanName' must not be blank.");
        }

        final var applicationContext = testContext.getApplicationContext();
        final var entityManagerFactory = applicationContext.getBean(
                this.entityManagerFactoryBeanName,
                EntityManagerFactory.class
        );
        final var entityManager = entityManagerFactory.createEntityManager();

        this.test(applicationContext, entityManager);
    }

    abstract protected void test(
            @NonNull final ApplicationContext applicationContext,
            @NonNull final EntityManager entityManager
    );

}