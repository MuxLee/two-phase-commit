package net.mux.twophasecommit.bank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.lang.NonNull;

import java.util.UUID;

@Entity
@Table
public class PassHistory {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID passHistoryId;

    @Column(
            nullable = false,
            updatable = false
    )
    private long afterAmount;

    @Column(
            nullable = false,
            updatable = false
    )
    private long beforeAmount;

    @Column(
            nullable = false,
            updatable = false
    )
    private long incomeAmount;

    public PassHistory(final long afterAmount, final long beforeAmount, final long incomeAmount) {
        this.afterAmount = afterAmount;
        this.beforeAmount = beforeAmount;
        this.incomeAmount = incomeAmount;
    }

    protected PassHistory() {}

    @NonNull
    public long getIncomeAmount() {
        return this.incomeAmount;
    }

    @NonNull
    public long getBeforeAmount() {
        return this.beforeAmount;
    }

    @NonNull
    public long getAfterAmount() {
        return this.afterAmount;
    }

}