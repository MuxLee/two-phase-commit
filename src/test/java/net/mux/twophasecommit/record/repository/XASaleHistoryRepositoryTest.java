package net.mux.twophasecommit.record.repository;

import net.mux.twophasecommit.record.listener.RecordTestMethodListener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles(value = "xa-database")
@SpringBootTest
@TestExecutionListeners(
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
        value = RecordTestMethodListener.class
)
class XASaleHistoryRepositoryTest {

    @Autowired
    private SaleHistoryRepository saleHistoryRepository;

    @DisplayName(value = "판매 내역 전체 조회 테스트")
    @Test
    void selectAllTest() {
        final var saleHistories = this.saleHistoryRepository.findAll();

        assertEquals(3, saleHistories.size());
    }

}