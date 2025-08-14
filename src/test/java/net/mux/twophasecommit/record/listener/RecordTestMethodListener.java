package net.mux.twophasecommit.record.listener;

import net.mux.twophasecommit.listener.BeforeTestMethodListener;
import net.mux.twophasecommit.listener.provider.PrimaryEntityManagerProvider;
import net.mux.twophasecommit.listener.provider.SecondaryDataSourceProvider;
import net.mux.twophasecommit.listener.provider.SecondaryEntityManagerProvider;
import net.mux.twophasecommit.listener.strategy.SeveralTestStrategy;
import net.mux.twophasecommit.listener.strategy.InsertTestStrategy;
import net.mux.twophasecommit.listener.strategy.TruncateTestStrategy;
import net.mux.twophasecommit.record.entity.SaleHistory;

import java.util.Set;

public final class RecordTestMethodListener extends BeforeTestMethodListener {

    private RecordTestMethodListener() {
        final var tableInsertTestStrategy = InsertTestStrategy.of(
                Set.of(
                        new SaleHistory("딸기", 1000),
                        new SaleHistory("사과", 1200),
                        new SaleHistory("수박", 9900)
                ),
                SecondaryEntityManagerProvider.getInstance()
        );
        final var tableTruncateTestStrategy = TruncateTestStrategy.of(
                "record.sale_history",
                SecondaryDataSourceProvider.getInstance()
        );
        final var severalTestStrategy = SeveralTestStrategy.of(
                tableTruncateTestStrategy,
                tableInsertTestStrategy
        );

        super.setTestStrategy(severalTestStrategy);
    }

}