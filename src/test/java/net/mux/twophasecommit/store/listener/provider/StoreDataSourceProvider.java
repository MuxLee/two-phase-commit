package net.mux.twophasecommit.store.listener.provider;

import net.mux.twophasecommit.listener.provider.DataSourceProvider;
import net.mux.twophasecommit.store.Stores;

public final class StoreDataSourceProvider extends DataSourceProvider {

    private StoreDataSourceProvider() {
        super(Stores.DATA_SOURCE);
    }

    public static DataSourceProvider getInstance() {
        return Singleton.INSTANCE;
    }

    private static final class Singleton {

        private static final StoreDataSourceProvider INSTANCE;

        static {
            INSTANCE = new StoreDataSourceProvider();
        }

    }

}