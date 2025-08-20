package net.mux.twophasecommit.store.service;

import net.mux.twophasecommit.store.entity.SaleHistory;
import net.mux.twophasecommit.store.repository.SaleHistoryRepository;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * 상품과 관련된 기본 비즈니스 로직을 구성하여 제공합니다.
 *
 * @see StoreService
 */
abstract class AbstractStoreService implements StoreService {

    /**
     * 판매 기록 저장소
     *
     * @see SaleHistoryRepository
     */
    private final SaleHistoryRepository saleHistoryRepository;

    /**
     * {@link AbstractStoreService} 클래스의 생성자입니다.
     * {@link SaleHistoryRepository}를 주입받아 객체를 생성하여 반환합니다.
     *
     * <ul>
     *     <li>{@code saleHistoryRepository}는 <code>null</code>일 수 없습니다.</li>
     * </ul>
     *
     * @param saleHistoryRepository 거래 이력 저장소
     */
    protected AbstractStoreService(@NonNull final SaleHistoryRepository saleHistoryRepository) {
        this.saleHistoryRepository = saleHistoryRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @return 모든 판매 기록
     */
    @NonNull
    @Override
    public List<SaleHistory> getSaleHistories() {
        return this.saleHistoryRepository.findAll();
    }

    /**
     * {@inheritDoc}
     *
     * @param itemName 상품 이름
     * @param price 판매 금액
     */
    @Override
    public void leaveSaleHistory(@NonNull final String itemName, final int price) {
        final var saleHistory = new SaleHistory(itemName, price);

        this.saleHistoryRepository.save(saleHistory);
    }

}