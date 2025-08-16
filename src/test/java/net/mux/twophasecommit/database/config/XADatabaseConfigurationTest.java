package net.mux.twophasecommit.database.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "xa")
@DisplayName(value = "[분산 트랜잭션] - 데이터베이스 연동")
@SpringBootTest
class XADatabaseConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @DisplayName(value = "DataSource Bean 타입 및 개수 테스트")
    @Test
    void testDynamicDataSourceBeanRegistration() {
        final var dataSourceMap = this.applicationContext.getBeansOfType(DataSource.class);

        assertEquals(2, dataSourceMap.size(), "생성된 DataSource Bean 없습니다.");
        assertAll(() -> dataSourceMap.values()
                .forEach(dataSource ->
                        assertInstanceOf(AtomikosDataSourceBean.class, dataSource, "생성된 Bean이 AtomikosDataSourceBean 인스턴스 타입이 아닙니다.")
                )
        );

    }

}