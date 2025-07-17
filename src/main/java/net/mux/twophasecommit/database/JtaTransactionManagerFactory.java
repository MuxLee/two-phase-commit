package net.mux.twophasecommit.database;

import com.atomikos.icatch.jta.UserTransactionManager;
import jakarta.transaction.TransactionManager;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.jta.JtaTransactionManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JtaTransactionManagerFactory implements TransactionManagerFactory {

    private final String transactionManagerName;
    private final JtaTransactionManager jtaTransactionManager;
    private final TransactionManagerFactory transactionManagerFactory;

    public JtaTransactionManagerFactory(
            @NonNull final String transactionManagerName,
            @NonNull final JtaTransactionManager jtaTransactionManager
    ) {
        this(transactionManagerName, jtaTransactionManager, null);
    }

    public JtaTransactionManagerFactory(
            @NonNull final String transactionManagerName,
            @NonNull final JtaTransactionManager jtaTransactionManager,
            @Nullable final TransactionManagerFactory transactionManagerFactory
    ) {
        this.transactionManagerName = transactionManagerName;
        this.jtaTransactionManager = jtaTransactionManager;
        this.transactionManagerFactory = transactionManagerFactory;
    }

    @NonNull
    @Override
    public Map<String, ?> getTransactionManagers() {
        final var transactionManagers = new HashMap<String, Object>();

        if (this.transactionManagerFactory != null) {
            final var transactionManagersEntry = this.transactionManagerFactory.getTransactionManagers()
                    .entrySet();

            for (final var transactionManagerEntry : transactionManagersEntry) {
                final var transactionManager = transactionManagerEntry.getValue();
                final var transactionManagerName = transactionManagerEntry.getKey();

                if (transactionManager instanceof UserTransactionManager userTransactionManager) {
                    this.jtaTransactionManager.setUserTransaction(userTransactionManager);
                    this.jtaTransactionManager.setUserTransactionName(transactionManagerName);
                }
                else if (transactionManager instanceof TransactionManager jakartaTransactionManager) {
                    this.jtaTransactionManager.setTransactionManager(jakartaTransactionManager);
                    this.jtaTransactionManager.setTransactionManagerName(transactionManagerName);
                }

                transactionManagers.put(transactionManagerName, transactionManager);
            }
        }

        transactionManagers.put(this.transactionManagerName, this.jtaTransactionManager);

        return Collections.unmodifiableMap(transactionManagers);
    }

}