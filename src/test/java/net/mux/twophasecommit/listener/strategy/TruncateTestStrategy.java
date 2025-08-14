package net.mux.twophasecommit.listener.strategy;

import net.mux.twophasecommit.listener.provider.DataSourceProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;

import java.sql.SQLException;

public final class TruncateTestStrategy extends DatabaseTestStrategy {

    private final DataSourceProvider dataSourceProvider;
    private final String tableName;

    public TruncateTestStrategy(
            @NonNull final String tableName,
            @NonNull final DataSourceProvider dataSourceProvider
    ) {
        this.dataSourceProvider = dataSourceProvider;
        this.tableName = tableName;
    }

    @Override
    protected void test(@NonNull final ApplicationContext applicationContext) {
        final var dataSource = this.dataSourceProvider.getBean(applicationContext);

        try (final var connection = dataSource.getConnection()) {
            final var statement = connection.createStatement();

            statement.execute("TRUNCATE TABLE " + tableName);
        }
        catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static TruncateTestStrategy of(
            @NonNull final String tableName,
            @NonNull final DataSourceProvider dataSourceProvider
    ) {
        return new TruncateTestStrategy(tableName, dataSourceProvider);
    }

}