package net.mux.twophasecommit.database.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@ConfigurationProperties(value = "app")
public class AppProperties {

    private JpaDataSourceProperties primary;

    private JpaDataSourceProperties secondary;

    private boolean xaEnabled;

    private AppProperties() {
        this.xaEnabled = false;
    }

    @NonNull
    public JpaDataSourceProperties getSecondary() {
        return this.secondary;
    }

    @NonNull
    public JpaDataSourceProperties getPrimary() {
        return this.primary;
    }

    @NonNull
    public boolean isXaEnabled() {
        return this.xaEnabled;
    }

    public void setXaEnabled(@NonNull final boolean xaEnabled) {
        this.xaEnabled = xaEnabled;
    }

    public void setPrimary(@NonNull final JpaDataSourceProperties primary) {
        this.primary = primary;
    }

    public void setSecondary(@NonNull final JpaDataSourceProperties secondary) {
        this.secondary = secondary;
    }

}