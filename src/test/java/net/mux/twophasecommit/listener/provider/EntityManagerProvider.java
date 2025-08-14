package net.mux.twophasecommit.listener.provider;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;

public class EntityManagerProvider implements BeanProvider<EntityManager> {

    private final String entityManagerFactoryName;

    protected EntityManagerProvider(@NonNull final String entityManagerFactoryName) {
        this.entityManagerFactoryName = entityManagerFactoryName;
    }

    @NonNull
    public EntityManager getBean(@NonNull final ApplicationContext applicationContext) {
        return applicationContext.getBean(this.entityManagerFactoryName, EntityManagerFactory.class)
                .createEntityManager();
    };

}