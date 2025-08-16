package net.mux.twophasecommit.database.config;

import org.hibernate.engine.transaction.jta.platform.internal.AtomikosJtaPlatform;

import java.util.Properties;

class XADatabaseConfiguration extends DatabaseConfiguration {

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