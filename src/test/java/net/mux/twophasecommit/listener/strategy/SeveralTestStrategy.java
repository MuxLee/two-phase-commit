package net.mux.twophasecommit.listener.strategy;

import org.springframework.lang.NonNull;
import org.springframework.test.context.TestContext;

import java.util.HashSet;
import java.util.Set;

public final class SeveralTestStrategy implements TestStrategy {

    private final Set<TestStrategy> testStrategies;

    public SeveralTestStrategy() {
        this.testStrategies = new HashSet<>(0);
    }

    public void registerTestStrategy(@NonNull final TestStrategy testStrategy) {
        this.testStrategies.add(testStrategy);
    }

    public void registerTestStrategy(@NonNull final TestStrategy... testStrategies) {
        for (final TestStrategy testStrategy : testStrategies) {
            this.registerTestStrategy(testStrategy);
        }
    }

    @Override
    public void test(@NonNull final TestContext testContext) {
        for (final TestStrategy testStrategy : this.testStrategies) {
            testStrategy.test(testContext);
        }
    }

    public static TestStrategy of(@NonNull final TestStrategy... testStrategies) {
        final var severalTestStrategy = new SeveralTestStrategy();

        severalTestStrategy.registerTestStrategy(testStrategies);

        return severalTestStrategy;
    }

}