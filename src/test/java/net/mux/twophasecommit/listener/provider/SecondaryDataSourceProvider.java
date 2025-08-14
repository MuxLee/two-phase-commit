package net.mux.twophasecommit.listener.provider;

public final class SecondaryDataSourceProvider extends DataSourceProvider {

    private SecondaryDataSourceProvider() {
        super("secondaryDataSource");
    }

    public static DataSourceProvider getInstance() {
        return Singleton.INSTANCE;
    }

    private static final class Singleton {

        private static final SecondaryDataSourceProvider INSTANCE;

        static {
            INSTANCE = new SecondaryDataSourceProvider();
        }

    }

}