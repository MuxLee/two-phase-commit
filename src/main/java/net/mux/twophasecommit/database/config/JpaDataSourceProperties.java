package net.mux.twophasecommit.database.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.lang.NonNull;

class JpaDataSourceProperties extends DataSourceProperties {

    private String[] scanningPackages;

    private JpaDataSourceProperties() {
        this.scanningPackages = new String[0];
    }

    @NonNull
    public String[] getScanningPackages() {
        return this.scanningPackages;
    }

    public void setScanningPackages(@NonNull final String[] scanningPackages) {
        this.scanningPackages = scanningPackages;
    }

}