package net.mux.twophasecommit.database.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;

import java.util.*;

@ConfigurationProperties(value = "app")
class AppProperties {

    private final Set<DataSourceProperties> databases;

    private AppProperties() {
        this.databases = new LinkedHashSet<>();
    }

    @NonNull
    public Set<DataSourceProperties> getDatabases() {
        return Collections.unmodifiableSet(this.databases);
    }

    public void setDatabases(@NonNull final Set<DataSourceProperties> databases) {
        this.databases.addAll(databases);
    }

}