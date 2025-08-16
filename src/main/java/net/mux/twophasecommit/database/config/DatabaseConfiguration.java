package net.mux.twophasecommit.database.config;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;

import java.util.Properties;

class DatabaseConfiguration {

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
