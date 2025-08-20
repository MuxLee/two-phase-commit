package net.mux.twophasecommit.database.config;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;

import java.util.Properties;

/**
 * 데이터베이스의 공통된 정보를 설정합니다.
 *
 * <p>데이터베이스에 공통적으로 적용될 설정들을 작성합니다.</p>
 */
class DatabaseConfiguration {

    /**
     * {@code JPA} 구현체 {@code Hibernate}에 적용될
     * 공통 설정을 반환합니다.
     *
     * @return {@code Hibernate} 공통 설정
     */
    Properties jpaProperties() {
        final var jpaProperties = new Properties();
        final var namingStrategyName = CamelCaseToUnderscoresNamingStrategy.class.getName();

        jpaProperties.setProperty("hibernate.format_sql", "true");
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", "create");
        jpaProperties.setProperty("hibernate.physical_naming_strategy", namingStrategyName);
        jpaProperties.setProperty("hibernate.show_sql", "true");

        return jpaProperties;
    }

}