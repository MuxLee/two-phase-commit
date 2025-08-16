package net.mux.twophasecommit.bank.repository;

import net.mux.twophasecommit.bank.entity.PassHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BankRepository extends JpaRepository<PassHistory, UUID> {

    @Query(value = "SELECT ph FROM PassHistory as ph ORDER BY ph.passHistoryId DESC LIMIT 1")
    PassHistory getLatest();

}