package net.mux.twophasecommit.database.config;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "database")
@SpringBootTest
class DatabaseConfigurationTest {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private ApplicationContext applicationContext;

    @DisplayName(value = "동적 DataSource Bean 생성 테스트")
    @SuppressWarnings(value = "resource")
    @Test
    void testDynamicDataSourceBeanRegistration() {
        final var databases = this.appProperties.getDatabases();
        final var dataSourceMap = this.applicationContext.getBeansOfType(DataSource.class);

        assertEquals(databases.size(), dataSourceMap.size(), "생성된 DataSource Bean 없습니다.");
        assertAll(() -> dataSourceMap.values()
                .forEach(dataSource ->
                        assertInstanceOf(HikariDataSource.class, dataSource, "생성된 Bean이 HikariDataSource 인스턴스 타입이 아닙니다.")
                )
        );
    }

}