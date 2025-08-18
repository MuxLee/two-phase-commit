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

/**
 * 거래 이력을 저장할 데이터베이스 정보를 설정합니다.
 *
 * <p>데이터베이스와의 연결과, 실제 테이블과 매칭되는 엔티티들을 관리할
 * 엔티티 매니저를 설정합니다.</p>
 *
 * <p>XA가 활성화되었을 때 설정됩니다.</p>
 *
 * @see AppProperties
 * @see ExtendsTransactionCondition
 */
@Configuration
@EnableJpaRepositories(
        basePackages = Banks.PACKAGE,
        entityManagerFactoryRef = Banks.ENTITY_MANAGER_FACTORY_BEAN
)
@ExtendsTransactionCondition(havingValue = "true")
class BankXADatabaseConfiguration extends XADatabaseConfiguration {

    /**
     * 거래 이력을 저장하는 데이터베이스 정보
     *
     * @see AppProperties#bank
     */
    private final ExtendsDataSourceProperties bankProperties;

    /**
     * {@link BankDatabaseConfiguration}의 생성자입니다.
     *
     * @param appProperties 데이터베이스 연동 정보
     */
    BankXADatabaseConfiguration(final AppProperties appProperties) {
        this.bankProperties = appProperties.getBank();
    }

    /**
     * 데이터베이스 연동 정보를 담은 {@code Bean}을 생성합니다.
     *
     * @return 데이터베이스 연동 정보
     */
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

    /**
     * 실제 데이터베이스의 테이블과 매칭되는 엔티티들을
     * 관리하는 {@code Bean}을 생성합니다.
     *
     * @param dataSource 데이터베이스 연동 정보
     * @return 엔티티 매니저
     */
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