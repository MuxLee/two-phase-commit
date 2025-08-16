package net.mux.twophasecommit.store.service;

import net.mux.twophasecommit.shared.conditional.ExtendsTransactionCondition;
import net.mux.twophasecommit.store.Stores;
import net.mux.twophasecommit.store.repository.SaleHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@ExtendsTransactionCondition(matchIfMissing = true)
@Service
class NonXAStoreService extends AbstractStoreService {

    NonXAStoreService(final SaleHistoryRepository saleHistoryRepository) {
        super(saleHistoryRepository);
    }

    @Override
    @Transactional(value = Stores.TRANSACTION_MANAGER)
    public void leaveSaleHistory(final String itemName, final int price) {
        super.leaveSaleHistory(itemName, price);
    }

}