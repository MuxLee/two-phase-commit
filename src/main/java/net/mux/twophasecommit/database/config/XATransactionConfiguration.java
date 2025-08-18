package net.mux.twophasecommit.database.config;

import com.atomikos.icatch.jta.UserTransactionManager;
import jakarta.transaction.SystemException;
import net.mux.twophasecommit.shared.conditional.ExtendsTransactionCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.jta.JtaTransactionManager;

/**
 * XA 데이터베이스의 공통 트랜잭션 정보를 설정합니다.
 *
 * <p>XA가 활성화되었을 때 설정되며, XA가 비활성화되었을 때와 달리
 * 여러 데이터베이스가 하나의 트랜잭션 정보를 바라보도록 설정됩니다.</p>
 *
 * @see AppProperties
 * @see ExtendsTransactionCondition
 */
@Configuration
@ExtendsTransactionCondition(havingValue = "true")
class XATransactionConfiguration {

    /**
     * {@code Atomikos} 기반의 통합 트랜잭션 매니저 {@code Bean}을
     * 생성합니다.
     *
     * <p>통합된 트랜잭션들의 유효시간, 강제종료 등의 여부를 설정합니다.</p>
     *
     * @return 통합 트랜잭션 매니저
     * @throws SystemException 트랜잭션 타임아웃이 0초보다 작게 설정될 경우 발생되는 예외입니다.
     */
    @Bean
    UserTransactionManager userTransactionManager() throws SystemException {
        final var userTransactionManager = new UserTransactionManager();

        userTransactionManager.setForceShutdown(true);
        userTransactionManager.setTransactionTimeout(500);

        return userTransactionManager;
    }

    /**
     * Java 통합 트랜잭션 매니저 {@code Bean}을 생성합니다.
     *
     * <p>통합 트랜잭션 구현체를 받고, 트랜잭션의 공통적인 정보들을
     * 설정합니다.</p>
     *
     * @param userTransactionManager {@code Atomikos} 통합 트랜잭션
     * @return Java 통합 트랜잭션 매니저
     */
    @Bean
    JtaTransactionManager transactionManager(final UserTransactionManager userTransactionManager) {
        final var jtaTransactionManager = new JtaTransactionManager();

        jtaTransactionManager.setTransactionManager(userTransactionManager);
        jtaTransactionManager.setUserTransaction(userTransactionManager);
        jtaTransactionManager.setAllowCustomIsolationLevels(true);

        return jtaTransactionManager;
    }

}