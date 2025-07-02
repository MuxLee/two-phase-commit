package net.mux.twophasecommit.database.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;

import javax.sql.DataSource;

abstract class AbstractDatabaseConfiguration implements ApplicationListener<ApplicationReadyEvent> {

    protected static final Logger LOGGER;

    private static final String DATASOURCE_BEAN_SUFFIX;

    static {
        DATASOURCE_BEAN_SUFFIX = "DataSource";
        LOGGER = LoggerFactory.getLogger(AbstractDatabaseConfiguration.class);
    }

    @Override
    public void onApplicationEvent(@NonNull final ApplicationReadyEvent applicationReadyEvent) {
        final var applicationContext = applicationReadyEvent.getApplicationContext();

        try {
            final var appProperties = applicationContext.getBean(AppProperties.class);
            final var beanFactory = applicationReadyEvent.getApplicationContext()
                    .getBeanFactory();
            final var databases = appProperties.getDatabases();

            for (final var database: databases) {
                final var dataSourceBeanName = database.getName() + DATASOURCE_BEAN_SUFFIX;
                final var dataSource = this.createDataSource(dataSourceBeanName, database);

                beanFactory.registerSingleton(dataSourceBeanName, dataSource);
            }
        }
        catch (NoSuchBeanDefinitionException ignored) {
            LOGGER.warn("AppProperties bean을 찾을 수 없습니다");
        }
        catch (Exception exception) {
            LOGGER.error(exception.getMessage(), exception);
        }
    }

    abstract protected DataSource createDataSource(
        @NonNull final String dataSourceBeanName,
        @NonNull final DataSourceProperties dataSourceProperties
    );

}