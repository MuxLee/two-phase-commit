package net.mux.twophasecommit.listener.strategy;

import org.springframework.lang.NonNull;
import org.springframework.test.context.TestContext;

public interface TestStrategy {

    void test(@NonNull final TestContext testContext);

}