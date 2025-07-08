package net.mux.twophasecommit.record.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sale_history")
public final class SaleHistory {

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

    public SaleHistory(final String itemName, final long price) {
        this.itemName = itemName;
        this.price = price;
    }

}