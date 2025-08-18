package net.mux.twophasecommit.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import net.mux.twophasecommit.bank.entity.PassHistory;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 판매된 상품의 이름과 판매 금액을 기록합니다.
 */
@Entity
@Table
public class SaleHistory {

    /**
     * 판매 이력의 고유 번호(값)입니다.
     *
     * <ul>
     * <li>{@link PassHistory} 엔티티의 기본키입니다.</li>
     * </ul>
     *
     * @see UUID
     */
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID saleHistoryId;

    /**
     * 판매된 상품의 이름입니다.
     *
     * <ul>
     *     <li>최대 100글자까지 입력할 수 있습니다.</li>
     *     <li>{@code null} 데이터를 넣을 수 없습니다.</li>
     *     <li>변경이 불가능합니다.</li>
     * </ul>
     */
    @Column(
            length = 100,
            nullable = false,
            updatable = false
    )
    private String itemName;

    /**
     * 상품이 판매된 금액입니다.
     *
     * <ul>
     *     <li>{@code null} 데이터를 넣을 수 없습니다.</li>
     *     <li>변경이 불가능합니다.</li>
     * </ul>
     */
    @Column(
            nullable = false,
            updatable = false
    )
    private long price;

    /**
     * 판매된 시간입니다.
     *
     * <ul>
     *     <li>{@code null} 데이터를 넣을 수 없습니다.</li>
     *     <li>변경이 불가능합니다.</li>
     * </ul>
     */
    @CreationTimestamp
    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime saleDateTime;

    /**
     * {@link SaleHistory} 클래스의 생성자입니다.
     * 상품 이름과 금액을 받아 객체를 생성하여 반환합니다.
     *
     * @param itemName 상품 이름
     * @param price 판매 금액
     */
    public SaleHistory(@NonNull final String itemName, final long price) {
        this.itemName = itemName;
        this.price = price;
    }

    /**
     * {@link SaleHistory} 클래스의 생성자입니다.
     * {@code JPA}에 의해 호출되며 Proxy 객체 생성을 위해
     * 선언된 기본 생성자입니다.
     */
    protected SaleHistory() {}

    /**
     * 판매된 상품 이름을 반환합니다.
     *
     * @return 상품 이름
     */
    @NonNull
    public String getItemName() {
        return this.itemName;
    }

    /**
     * 판매된 상품의 금액을 반환합니다.
     *
     * @return 판매 금액
     */
    public long getPrice() {
        return this.price;
    }

    /**
     * 상품이 판매된 시간을 반환합니다.
     *
     * @return 판매된 시간
     */
    @NonNull
    public LocalDateTime getSaleDateTime() {
        return this.saleDateTime;
    }

}