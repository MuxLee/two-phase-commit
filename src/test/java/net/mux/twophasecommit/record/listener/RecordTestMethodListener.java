package net.mux.twophasecommit.record.listener;

import net.mux.twophasecommit.listener.BeforeTestMethodListener;
import net.mux.twophasecommit.listener.strategy.SeveralTestStrategy;
import net.mux.twophasecommit.listener.strategy.InsertTestStrategy;
import net.mux.twophasecommit.listener.strategy.TruncateTestStrategy;
import net.mux.twophasecommit.record.entity.SaleHistory;

public final class RecordTestMethodListener extends BeforeTestMethodListener {

    private RecordTestMethodListener() {
        final var tableInsertTestStrategy = InsertTestStrategy.of(
                new SaleHistory("딸기", 1000),
                new SaleHistory("사과", 1200),
                new SaleHistory("수박", 9900)
        );
        final var tableTruncateTestStrategy = TruncateTestStrategy.of("record.sale_history");
        final var severalTestStrategy = SeveralTestStrategy.of(
                tableTruncateTestStrategy,
                tableInsertTestStrategy
        );

        tableInsertTestStrategy.setEntityManagerFactoryBeanName("secondaryEntityManagerFactoryBean");
        tableTruncateTestStrategy.setEntityManagerFactoryBeanName("secondaryEntityManagerFactoryBean");

        super.setTestStrategy(severalTestStrategy);
    }

}