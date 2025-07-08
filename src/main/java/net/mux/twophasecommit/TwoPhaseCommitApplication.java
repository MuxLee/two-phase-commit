package net.mux.twophasecommit;

import com.atomikos.spring.AtomikosAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

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
