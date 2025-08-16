package net.mux.twophasecommit.database.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import net.mux.twophasecommit.bank.Banks;
import net.mux.twophasecommit.shared.conditional.ExtendsTransactionCondition;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@EnableJpaRepositories(
        basePackages = Banks.PACKAGE,
        entityManagerFactoryRef = Banks.ENTITY_MANAGER_FACTORY_BEAN
)
@ExtendsTransactionCondition(havingValue = "true")
class BankXADatabaseConfiguration extends XADatabaseConfiguration {

    private final JpaDataSourceProperties bankProperties;

    BankXADatabaseConfiguration(final AppProperties appProperties) {
        this.bankProperties = appProperties.getBank();
    }

    @Bean(value = Banks.DATA_SOURCE)
    DataSource dataSource() throws SQLException {
        final var dataSourceBean = new AtomikosDataSourceBean();
        final var mysqlXaDataSource = new MysqlXADataSource();

        mysqlXaDataSource.setPassword(this.bankProperties.getPassword());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setUrl(this.bankProperties.getUrl());
        mysqlXaDataSource.setUser(this.bankProperties.getUsername());

        dataSourceBean.setUniqueResourceName(this.bankProperties.getName() + "-datasource");
        dataSourceBean.setXaDataSource(mysqlXaDataSource);

        return dataSourceBean;
    }

    @Bean(value = Banks.ENTITY_MANAGER_FACTORY_BEAN)
    LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier(value = Banks.DATA_SOURCE) final DataSource dataSource
    ) {
        final var entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        final var jpaProperties = this.jpaProperties();
        final var jpaVendorAdapter = new HibernateJpaVendorAdapter();

        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan(this.bankProperties.getScanningPackages());

        return entityManagerFactoryBean;
    }

}