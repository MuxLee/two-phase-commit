package net.mux.twophasecommit.bank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

/**
 * 새로운 거래가 발생할 경우 들어온 금액, 거래 전 금액, 거래 후 금액을
 * 기록합니다.
 *
 * <p>이 클래스를 구성하는 방법은 다음과 같습니다.</p>
 *
 * <p>들어온 금액이 {@code 5000}, 거래 전 금액이 {@code 1000}
 * 라고 가정한다면, 거래 후의 금액은 {@code 6000} 입니다.
 * 이를 이용하여 객체를 생성한다면 다음과 같이 생성할 수 있습니다.</p>
 * <blockquote>
 * <pre>final PassHistory passHistory = new PassHistory(6000, 5000, 1000);</pre>
 * </blockquote>
 */
@Entity
@Table
public class PassHistory {

    /**
     * 발생한 거래 이력의 고유 번호(값)입니다.
     *
     * <ul>
     * <li>{@link PassHistory} 엔티티의 기본키입니다.</li>
     * </ul>
     *
     * @see UUID
     */
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID passHistoryId;

    /**
     * 거래 이후의 최종 금액입니다.
     *
     * <ul>
     * <li>{@code null} 데이터를 넣을 수 없습니다.
     * <li>변경이 불가능합니다.
     */
    @Column(
            nullable = false,
            updatable = false
    )
    private long afterAmount;

    /**
     * 거래 이전의 기존 금액입니다.
     *
     * <ul>
     * <li>{@code null} 데이터를 넣을 수 없습니다.</li>
     * <li>변경이 불가능합니다.</li>
     * </ul>
     */
    @Column(
            nullable = false,
            updatable = false
    )
    private long beforeAmount;

    /**
     * 발생한 거래의 입금된 금액입니다.
     *
     * <ul>
     * <li>{@code null} 데이터를 넣을 수 없습니다.</li>
     * <li>변경이 불가능합니다.</li>
     * </ul>
     */
    @Column(
            nullable = false,
            updatable = false
    )
    private long incomeAmount;

    /**
     * {@link PassHistory} 클래스의 생성자입니다.
     * 금액 정보들을 받아 객체를 생성하여 반환합니다.
     *
     * @param afterAmount  거래 이후의 최종 금액
     * @param beforeAmount 거래 이전의 기존 금액
     * @param incomeAmount 발생한 거래의 입금 금액
     */
    public PassHistory(final long afterAmount, final long beforeAmount, final long incomeAmount) {
        this.afterAmount = afterAmount;
        this.beforeAmount = beforeAmount;
        this.incomeAmount = incomeAmount;
    }

    /**
     * {@link PassHistory} 클래스의 생성자입니다.
     * {@code JPA}에 의해 호출되며 Proxy 객체 생성을 위해
     * 선언된 기본 생성자입니다.
     */
    protected PassHistory() {}

    /**
     * 거래 이후의 최종 금액을 반환합니다.
     *
     * @return 최종 금액
     */
    public long getAfterAmount() {
        return this.afterAmount;
    }

    /**
     * 거래 이전의 기존 금액을 반환합니다.
     *
     * @return 기존 금액
     */
    public long getBeforeAmount() {
        return this.beforeAmount;
    }

    /**
     * 발생된 거래의 입금 금액을 반환합니다.
     *
     * @return 입금 금액
     */
    public long getIncomeAmount() {
        return this.incomeAmount;
    }

}