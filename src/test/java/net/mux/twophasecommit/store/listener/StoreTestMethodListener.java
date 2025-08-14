package net.mux.twophasecommit.store.listener;

import net.mux.twophasecommit.listener.BeforeTestMethodListener;
import net.mux.twophasecommit.listener.provider.PrimaryDataSourceProvider;
import net.mux.twophasecommit.listener.provider.PrimaryEntityManagerProvider;
import net.mux.twophasecommit.listener.strategy.InsertTestStrategy;
import net.mux.twophasecommit.listener.strategy.SeveralTestStrategy;
import net.mux.twophasecommit.listener.strategy.TruncateTestStrategy;
import net.mux.twophasecommit.store.entity.StoreAmount;

import java.util.Set;

public final class StoreTestMethodListener extends BeforeTestMethodListener {

    private StoreTestMethodListener() {
        final var tableInsertTestStrategy = InsertTestStrategy.of(
                Set.of(
                        new StoreAmount(10000, 0, 10000),
                        new StoreAmount(15000, 10000, 5000),
                        new StoreAmount(17000, 15000, 2000)
                ),
                PrimaryEntityManagerProvider.getInstance()
        );
        final var tableTruncateTestStrategy = TruncateTestStrategy.of(
                "store.store_amount",
                PrimaryDataSourceProvider.getInstance()
        );
        final var severalTestStrategy = SeveralTestStrategy.of(
                tableTruncateTestStrategy,
                tableInsertTestStrategy
        );

        super.setTestStrategy(severalTestStrategy);
    }

}