package net.mux.twophasecommit.trade.service;

import net.mux.twophasecommit.bank.service.BankService;
import net.mux.twophasecommit.store.service.StoreService;
import org.springframework.lang.NonNull;

abstract class AbstractTradeService implements TradeService {

    private final BankService bankService;
    private final StoreService storeService;

    AbstractTradeService(
            @NonNull final BankService bankService,
            @NonNull final StoreService storeService
    ) {
        this.bankService = bankService;
        this.storeService = storeService;
    }

    @Override
    @SuppressWarnings(value = "all")
    public void trade(final String itemName, final int price) {
        this.bankService.leavePassHistory(price);
        this.storeService.leaveSaleHistory(null, price);
    }

}