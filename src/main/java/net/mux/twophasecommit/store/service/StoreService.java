package net.mux.twophasecommit.store.service;

import net.mux.twophasecommit.store.entity.SaleHistory;

import java.util.List;

public interface StoreService {

    List<SaleHistory> getSaleHistories();

    void leaveSaleHistory(final String itemName, final int price);

}