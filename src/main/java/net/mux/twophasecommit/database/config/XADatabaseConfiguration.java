package net.mux.twophasecommit.database.config;

import org.hibernate.engine.transaction.jta.platform.internal.AtomikosJtaPlatform;

import java.util.Properties;

class XADatabaseConfiguration {

    Properties jpaProperties() {
        final var jpaProperties = new Properties();
        final var jtaPlatformName = AtomikosJtaPlatform.class.getName();

        jpaProperties.setProperty("hibernate.format_sql", "true");
        jpaProperties.setProperty("hibernate.show_sql", "true");
        jpaProperties.put("hibernate.transaction.coordinator_class", "jta");
        jpaProperties.put("hibernate.transaction.jta.platform", jtaPlatformName);
        jpaProperties.put("jakarta.persistence.transactionType", "JTA");

        return jpaProperties;
    }

}