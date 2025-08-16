package net.mux.twophasecommit.store.service;

import net.mux.twophasecommit.store.entity.SaleHistory;
import net.mux.twophasecommit.store.repository.SaleHistoryRepository;
import org.springframework.lang.NonNull;

import java.util.List;

abstract class AbstractStoreService implements StoreService {

    private final SaleHistoryRepository saleHistoryRepository;

    public AbstractStoreService(@NonNull final SaleHistoryRepository saleHistoryRepository) {
        this.saleHistoryRepository = saleHistoryRepository;
    }

    @Override
    public List<SaleHistory> getSaleHistories() {
        return this.saleHistoryRepository.findAll();
    }

    @Override
    public void leaveSaleHistory(final String itemName, final int price) {
        final var saleHistory = new SaleHistory(itemName, price);

        this.saleHistoryRepository.save(saleHistory);
    }

}