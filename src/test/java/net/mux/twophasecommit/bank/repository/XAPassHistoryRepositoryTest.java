package net.mux.twophasecommit.bank.repository;

import net.mux.twophasecommit.bank.listener.PassHistoryTestMethodListener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles(value = "xa-database")
@DisplayName(value = "[분산 트랜잭션] - 상점금액")
@SpringBootTest
@TestExecutionListeners(
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
        value = PassHistoryTestMethodListener.class
)
final class XAPassHistoryRepositoryTest {

    @Autowired
    private BankRepository storeAmountRepository;

    @DisplayName(value = "상점 금액 전체 조회 테스트")
    @Test
    void selectedAllTest() {
        final var storeAmounts = this.storeAmountRepository.findAll();

        assertEquals(3, storeAmounts.size());
    }

}