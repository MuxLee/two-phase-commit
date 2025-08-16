package net.mux.twophasecommit.database.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import net.mux.twophasecommit.shared.conditional.ExtendsTransactionCondition;
import net.mux.twophasecommit.store.Stores;
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
        basePackages = Stores.PACKAGE,
        entityManagerFactoryRef = Stores.ENTITY_MANAGER_FACTORY_BEAN
)
@ExtendsTransactionCondition(havingValue = "true")
class StoreXADatabaseConfiguration extends XADatabaseConfiguration {

    private final JpaDataSourceProperties storeProperties;

    StoreXADatabaseConfiguration(final AppProperties appProperties) {
        this.storeProperties = appProperties.getStore();
    }

    @Bean(value = Stores.DATA_SOURCE)
    DataSource dataSource() throws SQLException {
        final var dataSourceBean = new AtomikosDataSourceBean();
        final var mysqlXaDataSource = new MysqlXADataSource();

        mysqlXaDataSource.setPassword(this.storeProperties.getPassword());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setUrl(this.storeProperties.getUrl());
        mysqlXaDataSource.setUser(this.storeProperties.getUsername());

        dataSourceBean.setUniqueResourceName(this.storeProperties.getName() + "-datasource");
        dataSourceBean.setXaDataSource(mysqlXaDataSource);

        return dataSourceBean;
    }

    @Bean(value = Stores.ENTITY_MANAGER_FACTORY_BEAN)
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            @Qualifier(value = Stores.DATA_SOURCE) final DataSource dataSource
    ) {
        final var entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        final var jpaProperties = this.jpaProperties();
        final var jpaVendorAdapter = new HibernateJpaVendorAdapter();

        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan(this.storeProperties.getScanningPackages());

        return entityManagerFactoryBean;
    }

}