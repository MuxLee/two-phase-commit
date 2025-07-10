package net.mux.twophasecommit.listener.strategy;

import jakarta.persistence.EntityManager;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;

public final class TruncateTestStrategy extends DatabasePersistTestStrategy {

    private String tableName;

    public TruncateTestStrategy(@NonNull final String tableName) {
        this.tableName = tableName;
    }

    public void setTableName(@NonNull final String tableName) {
        this.tableName = tableName;
    }

    @Override
    protected void testPersist(
            @NonNull final ApplicationContext applicationContext,
            @NonNull final EntityManager entityManager
    ) {
        if (this.tableName.isBlank()) {
            throw new IllegalArgumentException("초기화할 테이블 정보가 입력되지 않았습니다.");
        }

        final var sql = "TRUNCATE TABLE " + this.tableName;
        entityManager.createNativeQuery(sql)
                .executeUpdate();
    }

    public static TestStrategy of(@NonNull final String tableName) {
        return new TruncateTestStrategy(tableName);
    }

}