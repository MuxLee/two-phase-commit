package net.mux.twophasecommit.listener.strategy;

import org.springframework.lang.NonNull;
import org.springframework.test.context.TestContext;

public final class EmptyTestStrategy implements TestStrategy {

    private EmptyTestStrategy() {}

    @Override
    public void test(@NonNull final TestContext testContext) {}

    public static TestStrategy getInstance() {
        return EmptyTestStrategyHolder.INSTANCE;
    }

    private static final class EmptyTestStrategyHolder {

        private static final EmptyTestStrategy INSTANCE = new EmptyTestStrategy();

    }

}