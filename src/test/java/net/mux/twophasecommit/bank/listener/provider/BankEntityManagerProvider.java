package net.mux.twophasecommit.bank.listener.provider;

import net.mux.twophasecommit.bank.Banks;
import net.mux.twophasecommit.listener.provider.EntityManagerProvider;

public final class BankEntityManagerProvider extends EntityManagerProvider {

    private BankEntityManagerProvider() {
        super(Banks.ENTITY_MANAGER_FACTORY_BEAN);
    }

    public static EntityManagerProvider getInstance() {
        return Singleton.INSTANCE;
    }

    private static final class Singleton {

        private static final BankEntityManagerProvider INSTANCE;

        static {
            INSTANCE = new BankEntityManagerProvider();
        }

    }

}