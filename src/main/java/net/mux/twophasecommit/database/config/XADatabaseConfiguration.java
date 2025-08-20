package net.mux.twophasecommit.database.config;

import org.hibernate.engine.transaction.jta.platform.internal.AtomikosJtaPlatform;

import java.util.Properties;

/**
 * XA가 활성화된 데이터베이스의 공통된 정보를 설정합니다.
 *
 * <p>XA가 활성화된 데이터베이스에 공통적으로 적용될 설정들을 작성합니다.
 * 기본 데이터베이스의 공통된 정보도 함께 설정됩니다.</p>
 *
 * @see DatabaseConfiguration
 */
class XADatabaseConfiguration extends DatabaseConfiguration {

    /**
     * {@inheritDoc}
     *
     * <p>XA가 활성화 되었기 때문에, {@code Hibernate}의 트랜잭션
     * 제공자는 {@code JTA(Java Transaction API)}로 지정됩니다.</p>
     *
     * @return {@code Hibernate} 공통 설정
     */
    @Override
    Properties jpaProperties() {
        final var jpaProperties = super.jpaProperties();
        final var jtaPlatformName = AtomikosJtaPlatform.class.getName();

        jpaProperties.put("hibernate.transaction.coordinator_class", "jta");
        jpaProperties.put("hibernate.transaction.jta.platform", jtaPlatformName);
        jpaProperties.put("jakarta.persistence.transactionType", "JTA");

        return jpaProperties;
    }

}