package net.mux.twophasecommit.trade.service;

import net.mux.twophasecommit.bank.service.BankService;
import net.mux.twophasecommit.shared.conditional.ExtendsTransactionCondition;
import net.mux.twophasecommit.store.service.StoreService;
import org.springframework.stereotype.Service;

/**
 * 거래 대한 비즈니스 로직을 처리합니다.
 *
 * <p>{@code Non-XA} 데이터베이스 연동되었을 때 활성화됩니다.</p>
 *
 * @see AbstractTradeService
 * @see ExtendsTransactionCondition
 */
@ExtendsTransactionCondition(matchIfMissing = true)
@Service
class NonXATradeService extends AbstractTradeService {

    /**
     * {@link NonXATradeService} 클래스의 생성자입니다.
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
    @SuppressWarnings(value = "all")
    NonXATradeService(final BankService bankService, final StoreService storeService) {
        super(bankService, storeService);
    }

}