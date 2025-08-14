package net.mux.twophasecommit.listener.strategy;

import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.test.context.TestContext;

public abstract class DatabaseTestStrategy implements TestStrategy {

    @Override
    public void test(@NonNull final TestContext testContext) {
        final var applicationContext = testContext.getApplicationContext();

        this.test(applicationContext);
    }

    abstract protected void test(@NonNull final ApplicationContext applicationContext);

}