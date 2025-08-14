package net.mux.twophasecommit.database.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.lang.NonNull;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ConditionalOnProperty(
        havingValue = "true",
        value = "app.xa-enabled"
)
@EnableJpaRepositories(
        basePackages = "net.mux.twophasecommit.record",
        entityManagerFactoryRef = "secondaryEntityManagerFactoryBean"
)
class SecondaryXADatabaseConfiguration extends XADatabaseConfiguration {

    private final JpaDataSourceProperties secondaryProperties;

    SecondaryXADatabaseConfiguration(@NonNull final AppProperties appProperties) {
        this.secondaryProperties = appProperties.getSecondary();
    }

    @Bean
    @NonNull
    DataSource secondaryDataSource() throws SQLException {
        final var dataSourceBean = new AtomikosDataSourceBean();
        final var mysqlXaDataSource = new MysqlXADataSource();

        mysqlXaDataSource.setPassword(this.secondaryProperties.getPassword());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setUrl(this.secondaryProperties.getUrl());
        mysqlXaDataSource.setUser(this.secondaryProperties.getUsername());

        dataSourceBean.setUniqueResourceName(this.secondaryProperties.getName() + "-datasource");
        dataSourceBean.setXaDataSource(mysqlXaDataSource);

        return dataSourceBean;
    }

    @Bean
    @NonNull
    LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactoryBean(
            @NonNull final DataSource secondaryDataSource
    ) {
        final var entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        final var jpaVendorAdapter = new HibernateJpaVendorAdapter();
        final var jpaProperties = this.jpaProperties();

        entityManagerFactoryBean.setDataSource(secondaryDataSource);
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan(this.secondaryProperties.getScanningPackages());
        jpaVendorAdapter.setGenerateDdl(true);

        return entityManagerFactoryBean;
    }

}