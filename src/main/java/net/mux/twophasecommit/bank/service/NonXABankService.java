package net.mux.twophasecommit.bank.service;

import net.mux.twophasecommit.bank.Banks;
import net.mux.twophasecommit.bank.repository.PassHistoryRepository;
import net.mux.twophasecommit.shared.conditional.ExtendsTransactionCondition;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 발생한 거래에 대한 비즈니스 로직을 처리합니다.
 *
 * <p>{@code Non-XA} 데이터베이스 연동되었을 때 활성화됩니다.</p>
 *
 * @see AbstractBankService
 * @see ExtendsTransactionCondition
 */
@ExtendsTransactionCondition(matchIfMissing = true)
@Service
class NonXABankService extends AbstractBankService {

    /**
     * {@link NonXABankService} 클래스의 생성자입니다.
     * {@link PassHistoryRepository}를 주입받아 객체를 생성하여 반환합니다.
     *
     * <ul>
     *     <li>{@code bankRepository}는 <code>null</code>일 수 없습니다.</li>
     * </ul>
     *
     * @param passHistoryRepository 거래 이력 저장소
     */
    NonXABankService(@NonNull final PassHistoryRepository passHistoryRepository) {
        super(passHistoryRepository);
    }

    /**
     * {@inheritDoc}
     *
     * @param price 거래 금액
     */
    @Override
    @Transactional(value = Banks.TRANSACTION_MANAGER)
    public void leavePassHistory(final int price) {
        super.leavePassHistory(price);
    }

}