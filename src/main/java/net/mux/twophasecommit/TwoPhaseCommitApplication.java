package net.mux.twophasecommit;

import com.atomikos.spring.AtomikosAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 분산 트랜잭션 통합을 위한 2PC(Two-Phase-Commit) 애플리케이션입니다.
 *
 * <p>다중 데이터베이스 설정과 분산 트랜잭션 통합을 위한
 * 자동 데이터베이스 설정 및 트랜잭션 설정을 모두 해제합니다.</p>
 * <ul>
 *     <li>{@link AtomikosAutoConfiguration}</li>
 *     <li>{@link DataSourceAutoConfiguration}</li>
 *     <li>{@link HibernateJpaAutoConfiguration}</li>
 *     <li>{@link JpaRepositoriesAutoConfiguration}</li>
 * </ul>
 */
@ConfigurationPropertiesScan(value = "net.mux.twophasecommit.database.config")
@EnableConfigurationProperties
@SpringBootApplication(
    exclude = {
        AtomikosAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class
    }
)
class TwoPhaseCommitApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwoPhaseCommitApplication.class, args);
    }

}
