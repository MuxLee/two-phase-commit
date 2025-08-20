package net.mux.twophasecommit.trade.service;

/**
 * 거래에 대한 비즈니스 로직을 처리합니다.
 */
public interface TradeService {

    /**
     * 거래 상품과 금액 지불을 통한 거래를 진행합니다.
     *
     * @param itemName 상품 이름
     * @param price 판매 금액
     */
    void trade(final String itemName, final int price);

}