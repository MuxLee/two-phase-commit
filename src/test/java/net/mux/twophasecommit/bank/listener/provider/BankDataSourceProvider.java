package net.mux.twophasecommit.bank.listener.provider;

import net.mux.twophasecommit.bank.Banks;
import net.mux.twophasecommit.listener.provider.DataSourceProvider;

public final class BankDataSourceProvider extends DataSourceProvider {

    private BankDataSourceProvider() {
        super(Banks.DATA_SOURCE);
    }

    public static DataSourceProvider getInstance() {
        return Singleton.INSTANCE;
    }

    private static final class Singleton {

        private static final BankDataSourceProvider INSTANCE;

        static {
            INSTANCE = new BankDataSourceProvider();
        }

    }

}