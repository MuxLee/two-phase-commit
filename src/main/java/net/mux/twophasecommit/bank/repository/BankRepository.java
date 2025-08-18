package net.mux.twophasecommit.bank.repository;

import net.mux.twophasecommit.bank.entity.PassHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * 거래 기록에 대한 데이터베이스 질의를 처리하는 저장소입니다.
 */
@Repository
public interface BankRepository extends JpaRepository<PassHistory, UUID> {

    /**
     * 가장 마지막으로 발생한 거래 기록 1건을 조회합니다.
     *
     * @return 마지막으로 발생한 거래 기록 1건
     */
    @Query(value = "SELECT ph FROM PassHistory as ph ORDER BY ph.passHistoryId DESC LIMIT 1")
    PassHistory getLatest();

}