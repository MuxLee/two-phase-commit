package net.mux.twophasecommit.store.service;

import net.mux.twophasecommit.shared.conditional.ExtendsTransactionCondition;
import net.mux.twophasecommit.store.Stores;
import net.mux.twophasecommit.store.repository.SaleHistoryRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 상품과 관련된 비즈니스 로직을 처리합니다.
 *
 * <p>{@code Non-XA} 데이터베이스 연동되었을 때 활성화됩니다.</p>
 *
 * @see AbstractStoreService
 * @see ExtendsTransactionCondition
 */
@ExtendsTransactionCondition(matchIfMissing = true)
@Service
class NonXAStoreService extends AbstractStoreService {

    /**
     * {@link NonXAStoreService} 클래스의 생성자입니다.
     * {@link SaleHistoryRepository}를 주입받아 객체를 생성하여 반환합니다.
     *
     * <ul>
     *     <li>{@code saleHistoryRepository}는 <code>null</code>일 수 없습니다.</li>
     * </ul>
     *
     * @param saleHistoryRepository 거래 이력 저장소
     */
    NonXAStoreService(final SaleHistoryRepository saleHistoryRepository) {
        super(saleHistoryRepository);
    }

    /**
     * {@inheritDoc}
     *
     * @param itemName 상품 이름
     * @param price 판매 금액
     */
    @Override
    @Transactional(value = Stores.TRANSACTION_MANAGER)
    public void leaveSaleHistory(@NonNull final String itemName, final int price) {
        super.leaveSaleHistory(itemName, price);
    }

}