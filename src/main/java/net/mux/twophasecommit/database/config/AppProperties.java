package net.mux.twophasecommit.database.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@ConfigurationProperties(value = "app")
class AppProperties {

    private final Set<JpaDataSourceProperties> databases;

    private boolean xaEnabled;

    private AppProperties() {
        this.databases = new LinkedHashSet<>();
        this.xaEnabled = false;
    }

    @NonNull
    public Set<JpaDataSourceProperties> getDatabases() {
        return Collections.unmodifiableSet(this.databases);
    }

    @NonNull
    public boolean isXaEnabled() {
        return this.xaEnabled;
    }

    public void setDatabases(@NonNull final Set<JpaDataSourceProperties> databases) {
        this.databases.addAll(databases);
    }

    public void setXaEnabled(@NonNull final boolean xaEnabled) {
        this.xaEnabled = xaEnabled;
    }

}