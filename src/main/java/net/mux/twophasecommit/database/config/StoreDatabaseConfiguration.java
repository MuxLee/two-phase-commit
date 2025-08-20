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

/**
 * 판매 이력을 저장할 데이터베이스 정보를 설정합니다.
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
        basePackages = Stores.PACKAGE,
        entityManagerFactoryRef = Stores.ENTITY_MANAGER_FACTORY_BEAN,
        transactionManagerRef = Stores.TRANSACTION_MANAGER
)
@ExtendsTransactionCondition(matchIfMissing = true)
class StoreDatabaseConfiguration extends DatabaseConfiguration {

    /**
     * 판매 이력을 저장하는 데이터베이스 정보
     *
     * @see AppProperties#store
     */
    private final ExtendsDataSourceProperties storeProperties;

    /**
     * {@link StoreDatabaseConfiguration}의 생성자입니다.
     *
     * @param appProperties 데이터베이스 연동 정보
     */
    StoreDatabaseConfiguration(final AppProperties appProperties) {
        this.storeProperties = appProperties.getStore();
    }

    /**
     * 데이터베이스 연동 정보를 담은 {@code Bean}을 생성합니다.
     *
     * @return 데이터베이스 연동 정보
     */
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

    /**
     * 실제 데이터베이스의 테이블과 매칭되는 엔티티들을
     * 관리하는 {@code Bean}을 생성합니다.
     *
     * @param dataSource 데이터베이스 연동 정보
     * @return 엔티티 매니저
     */
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

    /**
     * {@code ACID} 보장을 위한 트랜잭션을 관리하는 {@code Bean}을 생성합니다.
     *
     * @param entityManagerFactory 엔티티 매니저
     * @return 트랜잭션 매니저
     */
    @Bean(value = Stores.TRANSACTION_MANAGER)
    JpaTransactionManager transactionManager(
            @Qualifier(value = Stores.ENTITY_MANAGER_FACTORY_BEAN) final EntityManagerFactory entityManagerFactory
    ) {
        final var jpaTransactionManager = new JpaTransactionManager();

        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);

        return jpaTransactionManager;
    }

}