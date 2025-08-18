package net.mux.twophasecommit.bank.service;

import net.mux.twophasecommit.bank.entity.PassHistory;

import java.util.List;

/**
 * 발생한 거래에 대한 비즈니스 로직을 처리합니다.
 */
public interface BankService {

    /**
     * 발생했던 모든 거래 이력들을 가져옵니다.
     *
     * @return 모든 거래 이력
     */
    List<PassHistory> getPassHistories();

    /**
     * 발생한 거래의 기록을 남깁니다.
     *
     * @param price 거래 금액
     */
    void leavePassHistory(final int price);

}