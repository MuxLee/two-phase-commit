package net.mux.twophasecommit.bank.service;

import net.mux.twophasecommit.bank.entity.PassHistory;
import net.mux.twophasecommit.bank.repository.PassHistoryRepository;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * 발생한 거래에 대한 기본 비즈니스 로직을 구성하여 제공합니다.
 *
 * @see BankService
 */
abstract class AbstractBankService implements BankService {

    /**
     * 거래 이력 저장소
     *
     * @see PassHistoryRepository
     */
    protected final PassHistoryRepository passHistoryRepository;

    /**
     * {@link AbstractBankService} 클래스의 생성자입니다.
     * {@link PassHistoryRepository}를 주입받아 객체를 생성하여 반환합니다.
     *
     * <ul>
     *     <li>{@code bankRepository}는 <code>null</code>일 수 없습니다.</li>
     * </ul>
     *
     * @param passHistoryRepository 거래 이력 저장소
     */
    protected AbstractBankService(@NonNull final PassHistoryRepository passHistoryRepository) {
        this.passHistoryRepository = passHistoryRepository;
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public List<PassHistory> getPassHistories() {
        return this.passHistoryRepository.findAll();
    }

    /**
     * {@inheritDoc}
     *
     * @param price 거래 금액
     */
    @Override
    public void leavePassHistory(int price) {
        final long latestStoreAmount = this.passHistoryRepository.getLatest()
                .getAfterAmount();
        final var passHistory = new PassHistory(latestStoreAmount + price, latestStoreAmount, price);

        this.passHistoryRepository.save(passHistory);
    }

}