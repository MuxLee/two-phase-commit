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
        basePackages = "net.mux.twophasecommit.store",
        entityManagerFactoryRef = "primaryEntityManagerFactoryBean"
)
class PrimaryXADatabaseConfiguration extends XADatabaseConfiguration {

    private final JpaDataSourceProperties primaryProperties;

    PrimaryXADatabaseConfiguration(@NonNull final AppProperties appProperties) {
        this.primaryProperties = appProperties.getPrimary();
    }

    @Bean
    @NonNull
    DataSource primaryDataSource() throws SQLException {
        final var dataSourceBean = new AtomikosDataSourceBean();
        final var mysqlXaDataSource = new MysqlXADataSource();

        mysqlXaDataSource.setPassword(this.primaryProperties.getPassword());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setUrl(this.primaryProperties.getUrl());
        mysqlXaDataSource.setUser(this.primaryProperties.getUsername());

        dataSourceBean.setUniqueResourceName(this.primaryProperties.getName() + "-datasource");
        dataSourceBean.setXaDataSource(mysqlXaDataSource);

        return dataSourceBean;
    }

    @Bean
    @NonNull
    LocalContainerEntityManagerFactoryBean primaryEntityManagerFactoryBean(
            @NonNull final DataSource primaryDataSource
    ) {
        final var entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        final var jpaVendorAdapter = new HibernateJpaVendorAdapter();
        final var jpaProperties = this.jpaProperties();

        entityManagerFactoryBean.setDataSource(primaryDataSource);
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan(this.primaryProperties.getScanningPackages());
        jpaVendorAdapter.setGenerateDdl(true);

        return entityManagerFactoryBean;
    }

}