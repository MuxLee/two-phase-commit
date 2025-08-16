package net.mux.twophasecommit.database.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import net.mux.twophasecommit.shared.conditional.ExtendsTransactionCondition;
import net.mux.twophasecommit.store.Stores;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = Stores.PACKAGE,
        entityManagerFactoryRef = Stores.ENTITY_MANAGER_FACTORY_BEAN,
        transactionManagerRef = Stores.TRANSACTION_MANAGER
)
@ExtendsTransactionCondition(matchIfMissing = true)
class StoreDatabaseConfiguration extends DatabaseConfiguration {

    private final JpaDataSourceProperties storeProperties;

    StoreDatabaseConfiguration(final AppProperties appProperties) {
        this.storeProperties = appProperties.getStore();
    }

    @Bean(value = Stores.DATA_SOURCE)
    DataSource dataSource() {
        final var dataSource = new HikariDataSource();
        final var mysqlDataSource = new MysqlDataSource();

        dataSource.setDataSource(mysqlDataSource);
        mysqlDataSource.setPassword(this.storeProperties.getPassword());
        mysqlDataSource.setUrl(this.storeProperties.getUrl());
        mysqlDataSource.setUser(this.storeProperties.getUsername());

        return dataSource;
    }

    @Bean(value = Stores.ENTITY_MANAGER_FACTORY_BEAN)
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            @Qualifier(value = Stores.DATA_SOURCE) final DataSource dataSource
    ) {
        final var entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        final var jpaProperties = super.jpaProperties();
        final var jpaVendorAdapter = new HibernateJpaVendorAdapter();

        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan(this.storeProperties.getScanningPackages());

        return entityManagerFactoryBean;
    }

    @Bean(value = Stores.TRANSACTION_MANAGER)
    JpaTransactionManager transactionManager(
            @Qualifier(value = Stores.ENTITY_MANAGER_FACTORY_BEAN) final EntityManagerFactory entityManagerFactory
    ) {
        final var jpaTransactionManager = new JpaTransactionManager();

        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);

        return jpaTransactionManager;
    }

}