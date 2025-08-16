package net.mux.twophasecommit.store.listener;

import net.mux.twophasecommit.listener.BeforeTestMethodListener;
import net.mux.twophasecommit.store.listener.provider.StoreDataSourceProvider;
import net.mux.twophasecommit.store.listener.provider.StoreEntityManagerProvider;
import net.mux.twophasecommit.listener.strategy.InsertTestStrategy;
import net.mux.twophasecommit.listener.strategy.SeveralTestStrategy;
import net.mux.twophasecommit.listener.strategy.TruncateTestStrategy;
import net.mux.twophasecommit.store.entity.SaleHistory;

import java.util.Collections;
import java.util.LinkedHashSet;

public final class SaleHistoryTestMethodListener extends BeforeTestMethodListener {

    private SaleHistoryTestMethodListener() {
        final var saleHistories = new LinkedHashSet<SaleHistory>(3);

        saleHistories.add(new SaleHistory("사과", 1000));
        saleHistories.add(new SaleHistory("딸기", 3000));
        saleHistories.add(new SaleHistory("수박", 8000));

        final var tableInsertTestStrategy = InsertTestStrategy.of(
                Collections.unmodifiableSet(saleHistories),
                StoreEntityManagerProvider.getInstance()
        );
        final var tableTruncateTestStrategy = TruncateTestStrategy.of(
                "sale_history",
                StoreDataSourceProvider.getInstance()
        );
        final var severalTestStrategy = SeveralTestStrategy.of(
                tableTruncateTestStrategy,
                tableInsertTestStrategy
        );

        super.setTestStrategy(severalTestStrategy);
    }

}