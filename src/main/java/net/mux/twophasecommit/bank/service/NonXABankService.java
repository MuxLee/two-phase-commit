package net.mux.twophasecommit.bank.service;

import net.mux.twophasecommit.bank.Banks;
import net.mux.twophasecommit.bank.repository.BankRepository;
import net.mux.twophasecommit.shared.conditional.ExtendsTransactionCondition;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@ExtendsTransactionCondition(matchIfMissing = true)
@Service
class NonXABankService extends AbstractBankService {

    NonXABankService(@NonNull final BankRepository bankRepository) {
        super(bankRepository);
    }

    @Override
    @Transactional(value = Banks.TRANSACTION_MANAGER)
    public void leavePassHistory(final int price) {
        super.leavePassHistory(price);
    }

}