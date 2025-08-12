package net.mux.twophasecommit.listener.strategy;

import jakarta.persistence.EntityManager;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;

public final class TruncateTestStrategy extends DatabasePersistTestStrategy {

    private final String tableName;

    public TruncateTestStrategy(@NonNull final String tableName) {
        this.tableName = tableName;
    }

    @Override
    protected void testPersist(
            @NonNull final ApplicationContext applicationContext,
            @NonNull final EntityManager entityManager
    ) {
        if (this.tableName == null) {
            throw new IllegalArgumentException("'tableName' must not be null.");
        }

        if (this.tableName.isBlank()) {
            throw new IllegalArgumentException("'tableName' must not be blank.");
        }

        final var sql = "TRUNCATE TABLE " + this.tableName;

        entityManager.createNativeQuery(sql)
                .executeUpdate();
    }

    public static TruncateTestStrategy of(@NonNull final String tableName) {
        return new TruncateTestStrategy(tableName);
    }

}