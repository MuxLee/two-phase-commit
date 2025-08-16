package net.mux.twophasecommit.trade.service;

import net.mux.twophasecommit.bank.listener.PassHistoryTestMethodListener;
import net.mux.twophasecommit.bank.service.BankService;
import net.mux.twophasecommit.store.listener.SaleHistoryTestMethodListener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles(value = "xa")
@DisplayName(value = "[분산 트랜잭션] - 거래")
@SpringBootTest
@TestExecutionListeners(
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
        value = {
                SaleHistoryTestMethodListener.class,
                PassHistoryTestMethodListener.class
        }
)
class XATradeServiceTest {

    @Autowired
    @SuppressWarnings(value = "all")
    private BankService bankService;

    @Autowired
    @SuppressWarnings(value = "all")
    private TradeService tradeService;

    @DisplayName(value = "품목 거래 및 결제 테스트")
    @Test
    void tradeTest() {
        final int price = 50000;

        try {
            this.tradeService.trade(null, price);
        }
        catch (Exception ignored) {}
        finally {
            assertEquals(3, this.bankService.getPassHistories().size());
        }
    }

}