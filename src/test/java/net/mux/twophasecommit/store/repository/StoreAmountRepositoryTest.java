package net.mux.twophasecommit.store.repository;

import net.mux.twophasecommit.store.listener.StoreTestMethodListener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "database")
@SpringBootTest
@TestExecutionListeners(
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
        value = StoreTestMethodListener.class
)
class StoreAmountRepositoryTest {

    @Autowired
    private StoreAmountRepository storeAmountRepository;

    @DisplayName(value = "상점 금액 전체 조회 테스트")
    @Test
    void selectedAllTest() {
        final var storeAmounts = this.storeAmountRepository.findAll();

        assertEquals(3, storeAmounts.size());
    }

}