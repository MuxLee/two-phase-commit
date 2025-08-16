package net.mux.twophasecommit.database.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import net.mux.twophasecommit.bank.Banks;
import net.mux.twophasecommit.shared.conditional.ExtendsTransactionCondition;
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
        basePackages = Banks.PACKAGE,
        entityManagerFactoryRef = Banks.ENTITY_MANAGER_FACTORY_BEAN,
        transactionManagerRef = Banks.TRANSACTION_MANAGER
)
@ExtendsTransactionCondition(matchIfMissing = true)
class BankDatabaseConfiguration extends DatabaseConfiguration {

    private final JpaDataSourceProperties bankProperties;

    BankDatabaseConfiguration(final AppProperties appProperties) {
        this.bankProperties = appProperties.getBank();
    }

    @Bean(value = Banks.DATA_SOURCE)
    DataSource dataSource() {
        final var dataSource = new HikariDataSource();
        final var mysqlDataSource = new MysqlDataSource();

        dataSource.setDataSource(mysqlDataSource);
        mysqlDataSource.setPassword(this.bankProperties.getPassword());
        mysqlDataSource.setUrl(this.bankProperties.getUrl());
        mysqlDataSource.setUser(this.bankProperties.getUsername());

        return dataSource;
    }

    @Bean(value = Banks.ENTITY_MANAGER_FACTORY_BEAN)
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            @Qualifier(value = Banks.DATA_SOURCE) final DataSource dataSource
    ) {
        final var entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        final var jpaProperties = super.jpaProperties();
        final var jpaVendorAdapter = new HibernateJpaVendorAdapter();

        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan(this.bankProperties.getScanningPackages());

        return entityManagerFactoryBean;
    }

    @Bean(value = Banks.TRANSACTION_MANAGER)
    JpaTransactionManager transactionManager(
            @Qualifier(value = Banks.ENTITY_MANAGER_FACTORY_BEAN) final EntityManagerFactory entityManagerFactory
    ) {
        final var jpaTransactionManager = new JpaTransactionManager();

        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);

        return jpaTransactionManager;
    }
    
}