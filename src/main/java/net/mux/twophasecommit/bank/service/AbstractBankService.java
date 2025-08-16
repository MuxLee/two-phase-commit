package net.mux.twophasecommit.bank.service;

import net.mux.twophasecommit.bank.entity.PassHistory;
import net.mux.twophasecommit.bank.repository.BankRepository;
import org.springframework.lang.NonNull;

import java.util.List;

abstract class AbstractBankService implements BankService {

    protected final BankRepository bankRepository;

    AbstractBankService(@NonNull final BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public List<PassHistory> getPassHistories() {
        return this.bankRepository.findAll();
    }

    @Override
    public void leavePassHistory(int price) {
        final long latestStoreAmount = this.bankRepository.getLatest()
                .getAfterAmount();
        final var passHistory = new PassHistory(latestStoreAmount + price, latestStoreAmount, price);

        this.bankRepository.save(passHistory);
    }

}