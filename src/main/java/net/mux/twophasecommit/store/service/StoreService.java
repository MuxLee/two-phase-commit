package net.mux.twophasecommit.store.service;

import net.mux.twophasecommit.store.entity.SaleHistory;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * 상품과 관련된 비즈니스 로직을 처리합니다.
 */
public interface StoreService {

    /**
     * 발생했던 모든 판매 기록을 가져옵니다.
     *
     * @return 모든 판매 기록
     */
    @NonNull
    List<SaleHistory> getSaleHistories();

    /**
     * 발생한 판매 기록을 남깁니다.
     *
     * @param itemName 상품 이름
     * @param price 판매 금액
     */
    void leaveSaleHistory(@NonNull final String itemName, final int price);

}