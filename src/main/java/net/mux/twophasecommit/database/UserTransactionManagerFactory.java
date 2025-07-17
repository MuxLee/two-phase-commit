package net.mux.twophasecommit.database;

import jakarta.transaction.UserTransaction;
import org.springframework.lang.NonNull;

import java.util.Map;

public class UserTransactionManagerFactory implements TransactionManagerFactory {

    private UserTransaction userTransaction;
    private String userTransactionManagerName;

    public UserTransactionManagerFactory(
            @NonNull final UserTransaction userTransaction,
            @NonNull final String userTransactionManagerName
    ) {
        this.userTransaction = userTransaction;
        this.userTransactionManagerName = userTransactionManagerName;
    }

    @NonNull
    @Override
    public Map<String, ?> getTransactionManagers() {
        if (this.userTransaction == null) {
            throw new IllegalArgumentException("'userTransaction' must not be null");
        }

        if (this.userTransactionManagerName == null) {
            throw new IllegalArgumentException("'userTransactionManagerName' must not be null");
        }

        if (this.userTransactionManagerName.isBlank()) {
            throw new IllegalArgumentException("'userTransactionManagerName' must not be blank");
        }

        return Map.of(this.userTransactionManagerName, this.userTransaction);
    }

    public void setUserTransaction(@NonNull final UserTransaction userTransaction) {
        this.userTransaction = userTransaction;
    }

    public void setUserTransactionManagerName(@NonNull final String userTransactionManagerName) {
        this.userTransactionManagerName = userTransactionManagerName;
    }

}