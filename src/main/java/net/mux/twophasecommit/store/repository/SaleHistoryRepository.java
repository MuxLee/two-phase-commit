package net.mux.twophasecommit.store.repository;

import net.mux.twophasecommit.store.entity.SaleHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * 판매 기록에 대한 데이터베이스 질의를 처리하는 저장소입니다.
 */
@Repository
public interface SaleHistoryRepository extends JpaRepository<SaleHistory, UUID> {}