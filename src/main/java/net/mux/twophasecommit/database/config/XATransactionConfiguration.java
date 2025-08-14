package net.mux.twophasecommit.database.config;

import com.atomikos.icatch.jta.UserTransactionManager;
import jakarta.transaction.SystemException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
@ConditionalOnProperty(
        havingValue = "true",
        value = "app.xa-enabled"
)
class XATransactionConfiguration {

    @Bean
    UserTransactionManager userTransactionManager() throws SystemException {
        final var userTransactionManager = new UserTransactionManager();

        userTransactionManager.setForceShutdown(true);
        userTransactionManager.setTransactionTimeout(500);

        return userTransactionManager;
    }

    @Bean
    @NonNull
    JtaTransactionManager transactionManager(@NonNull final UserTransactionManager userTransactionManager) {
        final var jtaTransactionManager = new JtaTransactionManager();

        jtaTransactionManager.setTransactionManager(userTransactionManager);
        jtaTransactionManager.setUserTransaction(userTransactionManager);
        jtaTransactionManager.setAllowCustomIsolationLevels(true);

        return jtaTransactionManager;
    }

}