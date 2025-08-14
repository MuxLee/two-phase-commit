package net.mux.twophasecommit.store.repository;

import net.mux.twophasecommit.store.entity.StoreAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StoreAmountRepository extends JpaRepository<StoreAmount, UUID> {}