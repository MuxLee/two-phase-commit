package net.mux.twophasecommit.store.listener.provider;

import net.mux.twophasecommit.listener.provider.EntityManagerProvider;
import net.mux.twophasecommit.store.Stores;

public final class StoreEntityManagerProvider extends EntityManagerProvider {

    private StoreEntityManagerProvider() {
        super(Stores.ENTITY_MANAGER_FACTORY_BEAN);
    }

    public static EntityManagerProvider getInstance() {
        return Singleton.INSTANCE;
    }

    private static final class Singleton {

        private static final StoreEntityManagerProvider INSTANCE;

        static {
            INSTANCE = new StoreEntityManagerProvider();
        }

    }

}