package net.mux.twophasecommit.database.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(
    havingValue = "false",
    matchIfMissing = true,
    value = "app.xa-enabled"
)
class DatabaseConfiguration extends AbstractDatabaseConfiguration {

    @Override
    protected DataSource createDataSource(
            @NonNull final String dataSourceBeanName,
            @NonNull final DataSourceProperties dataSourceProperties
    ) {
        final var dataSource = new HikariDataSource();
        final var mysqlDataSource = new MysqlDataSource();

        mysqlDataSource.setPassword(dataSourceProperties.getPassword());
        mysqlDataSource.setUrl(dataSourceProperties.getUrl());
        mysqlDataSource.setUser(dataSourceProperties.getUsername());

        dataSource.setDataSource(mysqlDataSource);
        dataSource.setPoolName(dataSourceBeanName);

        return dataSource;
    }

}