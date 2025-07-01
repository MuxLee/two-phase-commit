package net.mux.twophasecommit.database.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;

@Configuration
class DatabaseConfiguration implements BeanDefinitionRegistryPostProcessor {

    private static final String DATASOURCE_BEAN_SUFFIX;
    private static final Logger LOGGER;

    static {
        DATASOURCE_BEAN_SUFFIX = "DataSource";
        LOGGER = LoggerFactory.getLogger(DatabaseConfiguration.class);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull final BeanDefinitionRegistry beanDefinitionRegistry)
            throws BeansException {}

    @Override
    public void postProcessBeanFactory(final @NonNull ConfigurableListableBeanFactory configurableListableBeanFactory)
            throws BeansException {

        try {
            final var environment = configurableListableBeanFactory.getBean(Environment.class);
            final var databases = Binder.get(environment)
                    .bind("app.databases", Bindable.listOf(DataSourceProperties.class))
                    .orElseGet(Collections::emptyList);

            for (final var database: databases) {
                final var dataSourceBeanName = database.getName() + DATASOURCE_BEAN_SUFFIX;
                final var dataSource = this.createDataSource(dataSourceBeanName, database);

                configurableListableBeanFactory.registerSingleton(dataSourceBeanName, dataSource);
            }
        }
        catch (NoSuchBeanDefinitionException ignored) {
            LOGGER.warn("AppProperties bean을 찾을 수 없습니다");
        }
        catch (SQLException exception) {
            LOGGER.error(exception.getMessage(), exception);
        }
    }

    private DataSource createDataSource(
            @NonNull final String dataSourceBeanName,
            @NonNull final DataSourceProperties dataSourceProperties
    ) throws SQLException {
        final var dataSourceBean = new AtomikosDataSourceBean();
        final var mysqlXaDataSource = new MysqlXADataSource();

        mysqlXaDataSource.setPassword(dataSourceProperties.getPassword());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setUrl(dataSourceProperties.getUrl());
        mysqlXaDataSource.setUser(dataSourceProperties.getUsername());

        dataSourceBean.setUniqueResourceName(dataSourceBeanName);
        dataSourceBean.setXaDataSource(mysqlXaDataSource);

        return dataSourceBean;
    }

}