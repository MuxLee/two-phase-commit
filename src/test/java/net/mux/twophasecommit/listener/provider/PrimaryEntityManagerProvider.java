package net.mux.twophasecommit.listener.provider;

public final class PrimaryEntityManagerProvider extends EntityManagerProvider {

    private PrimaryEntityManagerProvider() {
        super("primaryEntityManagerFactoryBean");
    }

    public static EntityManagerProvider getInstance() {
        return Singleton.INSTANCE;
    }

    private static final class Singleton {

        private static final PrimaryEntityManagerProvider INSTANCE;

        static {
            INSTANCE = new PrimaryEntityManagerProvider();
        }

    }

}