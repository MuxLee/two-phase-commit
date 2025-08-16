package net.mux.twophasecommit.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
public class SaleHistory {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID historyId;

    @Column(
            length = 100,
            nullable = false,
            updatable = false
    )
    private String itemName;

    @Column(
            nullable = false,
            updatable = false
    )
    private long price;

    @CreationTimestamp
    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime saleDateTime;

    public SaleHistory(@NonNull final String itemName, final long price) {
        this.itemName = itemName;
        this.price = price;
    }

    protected SaleHistory() {}

    @NonNull
    public LocalDateTime getSaleDateTime() {
        return this.saleDateTime;
    }

    @NonNull
    public long getPrice() {
        return this.price;
    }

    @NonNull
    public String getItemName() {
        return this.itemName;
    }

}