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

/**
 * 거래 이력을 저장할 데이터베이스 정보를 설정합니다.
 *
 * <p>데이터베이스와의 연결과, 실제 테이블과 매칭되는 엔티티들을 관리할
 * 엔티티 매니저, {@code ACID}를 보장하기 위한 트랜잭션 매니저를 설정합니다.</p>
 *
 * <p>XA가 비활성화되었을 때 설정되며, 이 트랜잭션은 다른 데이터베이스와
 * 호환되지 않습니다.</p>
 *
 * @see AppProperties
 * @see ExtendsTransactionCondition
 */
@Configuration
@EnableJpaRepositories(
        basePackages = Banks.PACKAGE,
        entityManagerFactoryRef = Banks.ENTITY_MANAGER_FACTORY_BEAN,
        transactionManagerRef = Banks.TRANSACTION_MANAGER
)
@ExtendsTransactionCondition(matchIfMissing = true)
class BankDatabaseConfiguration extends DatabaseConfiguration {

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
    BankDatabaseConfiguration(final AppProperties appProperties) {
        this.bankProperties = appProperties.getBank();
    }

    /**
     * 데이터베이스 연동 정보를 담은 {@code Bean}을 생성합니다.
     *
     * @return 데이터베이스 연동 정보
     */
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

    /**
     * 실제 데이터베이스의 테이블과 매칭되는 엔티티들을
     * 관리하는 {@code Bean}을 생성합니다.
     *
     * @param dataSource 데이터베이스 연동 정보
     * @return 엔티티 매니저
     */
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

    /**
     * {@code ACID} 보장을 위한 트랜잭션을 관리하는 {@code Bean}을 생성합니다.
     *
     * @param entityManagerFactory 엔티티 매니저
     * @return 트랜잭션 매니저
     */
    @Bean(value = Banks.TRANSACTION_MANAGER)
    JpaTransactionManager transactionManager(
            @Qualifier(value = Banks.ENTITY_MANAGER_FACTORY_BEAN) final EntityManagerFactory entityManagerFactory
    ) {
        final var jpaTransactionManager = new JpaTransactionManager();

        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);

        return jpaTransactionManager;
    }
    
}