package net.mux.twophasecommit.trade.service;

import net.mux.twophasecommit.bank.service.BankService;
import net.mux.twophasecommit.store.service.StoreService;
import org.springframework.lang.NonNull;

/**
 * 거래에 대한 기본 비즈니스 로직을 구성하여 제공합니다.
 *
 * @see TradeService
 */
abstract class AbstractTradeService implements TradeService {

    /**
     * 거래 처리 서비스
     *
     * @see BankService
     */
    private final BankService bankService;

    /**
     * 상품 처리 서비스
     *
     * @see StoreService
     */
    private final StoreService storeService;

    /**
     * {@link AbstractTradeService} 클래스의 생성자입니다.
     * {@link BankService}, {@link StoreService}를 주입받아 객체를 생성하여 반환합니다.
     *
     * <ul>
     *     <li>{@code bankService}는 <code>null</code>일 수 없습니다.</li>
     *     <li>{@code storeService}는 <code>null</code>일 수 없습니다.</li>
     * </ul>
     *
     * @param bankService 거래 처리 서비스
     * @param storeService 상품 처리 서비스
     */
    AbstractTradeService(
            @NonNull final BankService bankService,
            @NonNull final StoreService storeService
    ) {
        this.bankService = bankService;
        this.storeService = storeService;
    }

    /**
     * {@inheritDoc}
     *
     * @param itemName 상품 이름
     * @param price 판매 금액
     */
    @Override
    @SuppressWarnings(value = "all")
    public void trade(final String itemName, final int price) {
        this.bankService.leavePassHistory(price);
        this.storeService.leaveSaleHistory(null, price);
    }

}