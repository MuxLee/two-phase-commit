package net.mux.twophasecommit.store.repository;

import net.mux.twophasecommit.store.listener.SaleHistoryTestMethodListener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles(value = "database")
@DisplayName(value = "[개별 트랜잭션] - 판매내역")
@SpringBootTest
@TestExecutionListeners(
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
        value = SaleHistoryTestMethodListener.class
)
class SaleHistoryRepositoryTest {

    @Autowired
    private SaleHistoryRepository saleHistoryRepository;

    @DisplayName(value = "전체 조회 테스트")
    @Test
    void selectAllTest() {
        final var saleHistories = this.saleHistoryRepository.findAll();

        assertEquals(3, saleHistories.size());
    }

}