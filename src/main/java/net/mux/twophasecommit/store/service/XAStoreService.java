package net.mux.twophasecommit.store.service;

import net.mux.twophasecommit.shared.conditional.ExtendsTransactionCondition;
import net.mux.twophasecommit.store.repository.SaleHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@ExtendsTransactionCondition(havingValue = "true")
@Service
class XAStoreService extends AbstractStoreService {

    XAStoreService(final SaleHistoryRepository saleHistoryRepository) {
        super(saleHistoryRepository);
    }

    @Override
    @Transactional
    public void leaveSaleHistory(final String itemName, final int price) {
        super.leaveSaleHistory(itemName, price);
    }

}