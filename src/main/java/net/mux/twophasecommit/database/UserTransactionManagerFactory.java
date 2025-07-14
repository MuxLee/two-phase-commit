package net.mux.twophasecommit.database;

import jakarta.transaction.UserTransaction;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserTransactionManagerFactory implements TransactionManagerFactory {

    private final Map<String, UserTransaction> transactionManagers;

    public UserTransactionManagerFactory() {
        this.transactionManagers = new LimitedHashMap<>(1);
    }

    @NonNull
    @Override
    public Map<String, ?> getTransactionManagers() {
        if (this.transactionManagers.isEmpty()) {
            throw new IllegalStateException("No transactionManager found.");
        }

        return Collections.unmodifiableMap(this.transactionManagers);
    }

    public void setTransactionManager(
            @NonNull final String userTransactionName,
            @NonNull final UserTransaction userTransaction
    ) {
        if (userTransactionName == null) {
            throw new IllegalArgumentException("'userTransactionName' must not be null.");
        }

        if (userTransactionName.isBlank()) {
            throw new IllegalArgumentException("'userTransactionName' must not be blank.");
        }

        if (userTransaction == null) {
            throw new IllegalArgumentException("'userTransaction' must not be null.");
        }

        if (!this.transactionManagers.isEmpty()) {
            this.transactionManagers.clear();
        }

        this.transactionManagers.put(userTransactionName, userTransaction);
    }

    private static class LimitedHashMap<K, V> extends HashMap<K, V> {

        private final int limit;

        private LimitedHashMap(final int limit) {
            this.limit = limit;
        }

        @Override
        public V put(final K key, final V value) {
            if (super.containsKey(key) || this.isOverLimit()) {
                return super.put(key, value);
            }

            throw new IllegalStateException("It exceeded the set limit size of " + this.limit + ".");
        }

        public boolean isOverLimit() {
            return this.size() >= this.limit;
        }

    }

}