package net.mux.twophasecommit.database;

import org.springframework.lang.NonNull;

import java.util.Map;

public interface TransactionManagerFactory {

    @NonNull
    Map<String, ?> getTransactionManagers();

}