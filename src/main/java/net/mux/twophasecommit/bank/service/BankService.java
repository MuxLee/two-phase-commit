package net.mux.twophasecommit.bank.service;

import net.mux.twophasecommit.bank.entity.PassHistory;

import java.util.List;

public interface BankService {

    List<PassHistory> getPassHistories();

    void leavePassHistory(final int price);

}