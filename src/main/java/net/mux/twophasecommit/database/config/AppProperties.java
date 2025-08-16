package net.mux.twophasecommit.database.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.lang.NonNull;

@ConfigurationProperties(value = "app")
public class AppProperties {

    @NestedConfigurationProperty
    private JpaDataSourceProperties bank;

    @NestedConfigurationProperty
    private JpaDataSourceProperties store;

    private boolean xaEnabled;

    private AppProperties() {
        this.xaEnabled = false;
    }

    @NonNull
    public JpaDataSourceProperties getStore() {
        return this.store;
    }

    @NonNull
    public JpaDataSourceProperties getBank() {
        return this.bank;
    }

    @NonNull
    public boolean isXaEnabled() {
        return this.xaEnabled;
    }

    public void setXaEnabled(@NonNull final boolean xaEnabled) {
        this.xaEnabled = xaEnabled;
    }

    public void setBank(@NonNull final JpaDataSourceProperties bank) {
        this.bank = bank;
    }

    public void setStore(@NonNull final JpaDataSourceProperties store) {
        this.store = store;
    }

}