package net.mux.twophasecommit.database.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.lang.NonNull;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(
    havingValue = "false",
    matchIfMissing = true,
    value = "app.xa-enabled"
)
@EnableJpaRepositories(
        basePackages = "net.mux.twophasecommit.record",
        entityManagerFactoryRef = "secondaryEntityManagerFactoryBean",
        transactionManagerRef = "secondaryTransactionManager"
)
class SecondaryDatabaseConfiguration {

    private final JpaDataSourceProperties secondaryProperties;

    SecondaryDatabaseConfiguration(@NonNull final AppProperties appProperties) {
        this.secondaryProperties = appProperties.getSecondary();
    }

    @Bean
    @NonNull
    DataSource secondaryDataSource() {
        final var dataSource = new HikariDataSource();
        final var mysqlDataSource = new MysqlDataSource();

        dataSource.setDataSource(mysqlDataSource);
        mysqlDataSource.setPassword(this.secondaryProperties.getPassword());
        mysqlDataSource.setUrl(this.secondaryProperties.getUrl());
        mysqlDataSource.setUser(this.secondaryProperties.getUsername());

        return dataSource;
    }

    @Bean
    @NonNull
    LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactoryBean(
            @NonNull final DataSource secondaryDataSource
    ) {
        final var entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        final var jpaVendorAdapter = new HibernateJpaVendorAdapter();

        entityManagerFactoryBean.setDataSource(secondaryDataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan(this.secondaryProperties.getScanningPackages());
        jpaVendorAdapter.setDatabase(Database.MYSQL);
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setShowSql(true);

        return entityManagerFactoryBean;
    }

    @Bean
    @NonNull
    JpaTransactionManager secondaryTransactionManager(
            @NonNull final EntityManagerFactory secondaryEntityManagerFactoryBean
    ) {
        final var jpaTransactionManager = new JpaTransactionManager();

        jpaTransactionManager.setEntityManagerFactory(secondaryEntityManagerFactoryBean);

        return jpaTransactionManager;
    }

}