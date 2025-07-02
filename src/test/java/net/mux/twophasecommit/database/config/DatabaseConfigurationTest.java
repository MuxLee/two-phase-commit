package net.mux.twophasecommit.database.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.util.Collections;

@ActiveProfiles(value = "database")
@SpringBootTest
class DatabaseConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @DisplayName(value = "동적 데이터베이스 Bean 생성 테스트")
    @Test
    void testDynamicDataSourceBeanRegistration() {
        final var environment = this.applicationContext.getEnvironment();
        final var database = Binder.get(environment)
                .bind("app.databases", Bindable.listOf(DataSourceProperties.class))
                .orElseGet(Collections::emptyList);

        Assertions.assertEquals(2, database.size(), "데이터베이스가 존재하지 않습니다.");

        final var dataSourceMap = this.applicationContext.getBeansOfType(DataSource.class);

        Assertions.assertEquals(2, dataSourceMap.size(), "생성된 DataSource Bean 없습니다.");

    }

}