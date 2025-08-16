package net.mux.twophasecommit.bank.service;

import net.mux.twophasecommit.bank.repository.BankRepository;
import net.mux.twophasecommit.shared.conditional.ExtendsTransactionCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@ExtendsTransactionCondition(havingValue = "true")
@Service
class XABankService extends AbstractBankService {

    XABankService(final BankRepository bankRepository) {
        super(bankRepository);
    }

    @Override
    @Transactional
    public void leavePassHistory(final int price) {
        super.leavePassHistory(price);
    }

}