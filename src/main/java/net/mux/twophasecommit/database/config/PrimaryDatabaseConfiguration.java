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
        basePackages = "net.mux.twophasecommit.store",
        entityManagerFactoryRef = "primaryEntityManagerFactoryBean",
        transactionManagerRef = "primaryTransactionManager"
)
class PrimaryDatabaseConfiguration {

    private final JpaDataSourceProperties primaryProperties;

    PrimaryDatabaseConfiguration(@NonNull final AppProperties appProperties) {
        this.primaryProperties = appProperties.getPrimary();
    }

    @Bean
    @NonNull
    DataSource primaryDataSource() {
        final var dataSource = new HikariDataSource();
        final var mysqlDataSource = new MysqlDataSource();

        dataSource.setDataSource(mysqlDataSource);
        mysqlDataSource.setPassword(this.primaryProperties.getPassword());
        mysqlDataSource.setUrl(this.primaryProperties.getUrl());
        mysqlDataSource.setUser(this.primaryProperties.getUsername());

        return dataSource;
    }

    @Bean
    @NonNull
    LocalContainerEntityManagerFactoryBean primaryEntityManagerFactoryBean(
            @NonNull final DataSource primaryDataSource
    ) {
        final var entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        final var jpaVendorAdapter = new HibernateJpaVendorAdapter();

        entityManagerFactoryBean.setDataSource(primaryDataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan(this.primaryProperties.getScanningPackages());
        jpaVendorAdapter.setDatabase(Database.MYSQL);
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setShowSql(true);

        return entityManagerFactoryBean;
    }

    @Bean
    @NonNull
    JpaTransactionManager primaryTransactionManager(
            @NonNull final EntityManagerFactory primaryEntityManagerFactoryBean
    ) {
        final var jpaTransactionManager = new JpaTransactionManager();

        jpaTransactionManager.setEntityManagerFactory(primaryEntityManagerFactoryBean);

        return jpaTransactionManager;
    }
    
}