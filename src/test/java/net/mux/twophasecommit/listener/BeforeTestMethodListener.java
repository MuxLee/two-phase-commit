package net.mux.twophasecommit.listener;

import net.mux.twophasecommit.listener.strategy.EmptyTestStrategy;
import net.mux.twophasecommit.listener.strategy.TestStrategy;
import org.springframework.lang.NonNull;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public abstract class BeforeTestMethodListener extends AbstractTestExecutionListener {

    private TestStrategy testStrategy;

    protected BeforeTestMethodListener() {
        this(EmptyTestStrategy.getInstance());
    }

    protected BeforeTestMethodListener(@NonNull final TestStrategy testStrategy) {
        this.testStrategy = testStrategy;
    }

    @Override
    public void beforeTestMethod(@NonNull final TestContext testContext) {
        this.testStrategy.test(testContext);
    }

    public void setTestStrategy(@NonNull final TestStrategy testStrategy) {
        this.testStrategy = testStrategy;
    }

}