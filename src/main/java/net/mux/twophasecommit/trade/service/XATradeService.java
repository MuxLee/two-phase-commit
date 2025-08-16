package net.mux.twophasecommit.trade.service;

import net.mux.twophasecommit.bank.service.BankService;
import net.mux.twophasecommit.shared.conditional.ExtendsTransactionCondition;
import net.mux.twophasecommit.store.service.StoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@ExtendsTransactionCondition(havingValue = "true")
@Service
class XATradeService extends AbstractTradeService {

    @SuppressWarnings(value = "all")
    XATradeService(final BankService bankService, final StoreService storeService) {
        super(bankService, storeService);
    }

    @Override
    @Transactional
    public void trade(final String itemName, final int price) {
        super.trade(itemName, price);
    }

}