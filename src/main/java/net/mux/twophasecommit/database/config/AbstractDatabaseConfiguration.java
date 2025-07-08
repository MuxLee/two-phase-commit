package net.mux.twophasecommit.database.config;

import com.atomikos.icatch.jta.UserTransactionManager;
import jakarta.transaction.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;

abstract class AbstractDatabaseConfiguration implements ApplicationListener<ApplicationReadyEvent> {

    protected final Logger logger;

    protected AbstractDatabaseConfiguration() {
        this.logger = LoggerFactory.getLogger(AbstractDatabaseConfiguration.class);
    }

    @Override
    public void onApplicationEvent(@NonNull final ApplicationReadyEvent applicationReadyEvent) {
        final var applicationContext = applicationReadyEvent.getApplicationContext();
        final var listableBeanFactory = applicationContext.getBeanFactory();

        try {
            this.registerDatabaseBean(applicationContext, listableBeanFactory);
            this.registerTransactionBean(listableBeanFactory);
        }
        catch (NoSuchBeanDefinitionException ignored) {
            logger.warn("AppProperties bean을 찾을 수 없습니다");
        }
        catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
        }
    }

    abstract protected DataSource createDataSource(
        @NonNull final String dataSourceBeanName,
        @NonNull final DataSourceProperties dataSourceProperties
    );

    private void registerDatabaseBean(
            @NonNull final ConfigurableApplicationContext applicationContext,
            @NonNull final ConfigurableListableBeanFactory listableBeanFactory
    ) throws NoSuchBeanDefinitionException {
        final var appProperties = applicationContext.getBean(AppProperties.class);
        final var databases = appProperties.getDatabases();

        for (final var database: databases) {
            final var databaseName = database.getName();
            final var dataSourceBeanName = databaseName + "DataSource";
            final var dataSource = this.createDataSource(dataSourceBeanName, database);

            listableBeanFactory.registerSingleton(dataSourceBeanName, dataSource);

            final var entityManagerFactoryBeanName = databaseName + "EntityManagerFactory";
            final var entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
            final var jpaVendorAdapter = new HibernateJpaVendorAdapter();
            final var scanningPackages = database.getScanningPackages();

            entityManagerFactoryBean.setDataSource(dataSource);
            entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
            entityManagerFactoryBean.setPackagesToScan(scanningPackages);
            jpaVendorAdapter.setGenerateDdl(true);
            listableBeanFactory.registerSingleton(entityManagerFactoryBeanName, entityManagerFactoryBean);
        }
    }

    private void registerTransactionBean(@NonNull final ConfigurableListableBeanFactory listableBeanFactory)
            throws SystemException {
        final var userTransactionManager = new UserTransactionManager();

        userTransactionManager.setForceShutdown(true);
        userTransactionManager.setTransactionTimeout(500);
        listableBeanFactory.registerSingleton("userTransactionManager", userTransactionManager);

        final var jtaTransactionManager = new JtaTransactionManager();

        jtaTransactionManager.setTransactionManager(userTransactionManager);
        jtaTransactionManager.setUserTransaction(userTransactionManager);
        listableBeanFactory.registerSingleton("jtaTransactionManager", jtaTransactionManager);
    }

}