package net.mux.twophasecommit.listener.provider;

import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;

import javax.sql.DataSource;

public class DataSourceProvider implements BeanProvider<DataSource> {

    private final String dataSourceName;

    public DataSourceProvider(@NonNull final String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    @NonNull
    @Override
    public DataSource getBean(@NonNull final ApplicationContext applicationContext) {
        return applicationContext.getBean(this.dataSourceName, DataSource.class);
    }

}