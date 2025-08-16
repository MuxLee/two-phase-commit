package net.mux.twophasecommit.bank.listener;

import net.mux.twophasecommit.listener.BeforeTestMethodListener;
import net.mux.twophasecommit.bank.listener.provider.BankDataSourceProvider;
import net.mux.twophasecommit.bank.listener.provider.BankEntityManagerProvider;
import net.mux.twophasecommit.listener.strategy.InsertTestStrategy;
import net.mux.twophasecommit.listener.strategy.SeveralTestStrategy;
import net.mux.twophasecommit.listener.strategy.TruncateTestStrategy;
import net.mux.twophasecommit.bank.entity.PassHistory;

import java.util.Collections;
import java.util.LinkedHashSet;

public final class PassHistoryTestMethodListener extends BeforeTestMethodListener {

    private PassHistoryTestMethodListener() {
        final var passHistories = new LinkedHashSet<PassHistory>(3);

        passHistories.add(new PassHistory(1000, 0, 1000));
        passHistories.add(new PassHistory(4000, 1000, 3000));
        passHistories.add(new PassHistory(12000, 4000, 8000));

        final var tableInsertTestStrategy = InsertTestStrategy.of(
                Collections.unmodifiableSet(passHistories),
                BankEntityManagerProvider.getInstance()
        );
        final var tableTruncateTestStrategy = TruncateTestStrategy.of(
                "pass_history",
                BankDataSourceProvider.getInstance()
        );
        final var severalTestStrategy = SeveralTestStrategy.of(
                tableTruncateTestStrategy,
                tableInsertTestStrategy
        );

        super.setTestStrategy(severalTestStrategy);
    }

}