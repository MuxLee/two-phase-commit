package net.mux.twophasecommit.trade.service;

import net.mux.twophasecommit.bank.service.BankService;
import net.mux.twophasecommit.shared.conditional.ExtendsTransactionCondition;
import net.mux.twophasecommit.store.service.StoreService;
import org.springframework.stereotype.Service;

@ExtendsTransactionCondition(matchIfMissing = true)
@Service
class NonXATradeService extends AbstractTradeService {

    @SuppressWarnings(value = "all")
    NonXATradeService(final BankService bankService, final StoreService storeService) {
        super(bankService, storeService);
    }

}