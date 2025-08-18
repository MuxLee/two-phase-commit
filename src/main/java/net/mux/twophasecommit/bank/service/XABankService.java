package net.mux.twophasecommit.bank.service;

import net.mux.twophasecommit.bank.repository.BankRepository;
import net.mux.twophasecommit.shared.conditional.ExtendsTransactionCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 발생한 거래에 대한 비즈니스 로직을 처리합니다.
 *
 * <p>{@code XA} 데이터베이스 연동되었을 때 활성화됩니다.</p>
 *
 * @see AbstractBankService
 * @see ExtendsTransactionCondition
 */
@ExtendsTransactionCondition(havingValue = "true")
@Service
class XABankService extends AbstractBankService {

    /**
     * {@link XABankService} 클래스의 생성자입니다.
     * {@link BankRepository}를 주입받아 객체를 생성하여 반환합니다.
     *
     * <ul>
     * <li>{@code bankRepository}는 <code>null</code>일 수 없습니다.</li>
     * </ul>
     *
     * @param bankRepository 거래 이력 저장소
     */
    XABankService(final BankRepository bankRepository) {
        super(bankRepository);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void leavePassHistory(final int price) {
        super.leavePassHistory(price);
    }

}