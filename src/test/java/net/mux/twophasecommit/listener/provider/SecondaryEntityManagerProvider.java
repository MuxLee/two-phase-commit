package net.mux.twophasecommit.listener.provider;

public final class SecondaryEntityManagerProvider extends EntityManagerProvider {

    private SecondaryEntityManagerProvider() {
        super("secondaryEntityManagerFactoryBean");
    }

    public static EntityManagerProvider getInstance() {
        return Singleton.INSTANCE;
    }

    private static final class Singleton {

        private static final SecondaryEntityManagerProvider INSTANCE;

        static {
            INSTANCE = new SecondaryEntityManagerProvider();
        }

    }

}