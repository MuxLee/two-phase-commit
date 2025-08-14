package net.mux.twophasecommit.listener.provider;

public final class PrimaryDataSourceProvider extends DataSourceProvider {

    private PrimaryDataSourceProvider() {
        super("primaryDataSource");
    }

    public static DataSourceProvider getInstance() {
        return Singleton.INSTANCE;
    }

    private static final class Singleton {

        private static final PrimaryDataSourceProvider INSTANCE;

        static {
            INSTANCE = new PrimaryDataSourceProvider();
        }

    }

}