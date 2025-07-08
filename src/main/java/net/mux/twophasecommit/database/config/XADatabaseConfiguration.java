package net.mux.twophasecommit.database.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ConditionalOnProperty(
    havingValue = "true",
    value = "app.xa-enabled"
)
class XADatabaseConfiguration extends AbstractDatabaseConfiguration {

    @Override
    protected DataSource createDataSource(
            @NonNull final String dataSourceBeanName,
            @NonNull final DataSourceProperties dataSourceProperties
    ) {
        try {
            final var dataSourceBean = new AtomikosDataSourceBean();
            final var mysqlXaDataSource = new MysqlXADataSource();

            mysqlXaDataSource.setPassword(dataSourceProperties.getPassword());
            mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
            mysqlXaDataSource.setUrl(dataSourceProperties.getUrl());
            mysqlXaDataSource.setUser(dataSourceProperties.getUsername());

            dataSourceBean.setUniqueResourceName(dataSourceBeanName);
            dataSourceBean.setXaDataSource(mysqlXaDataSource);

            return dataSourceBean;
        }
        catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

}