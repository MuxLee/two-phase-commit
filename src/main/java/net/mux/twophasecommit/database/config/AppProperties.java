package net.mux.twophasecommit.database.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.lang.NonNull;

/**
 * 2PC 프로젝트의 데이터베이스 연동 정보 설정을 제공합니다.
 *
 * <p>{@code application.properties} 또는
 * {@code application.yml}을 통하여 서로 다른 두
 * 데이터베이스 정보와 {@code XA} 활성화 여부를 설정할 수 있습니다.</p>
 *
 * <p>다음과 같이 설정할 수 있습니다.</p>
 * <blockquote><pre>
 * # application.properties
 * app.xa-enabled=true
 *
 * #application.yml
 * app:
 *   xa-enabled: true
 * </pre></blockquote>
 */
@ConfigurationProperties(value = "app")
public class AppProperties {

    /**
     * 거래 이력을 저장할 데이터베이스 정보
     *
     * @see ExtendsDataSourceProperties
     */
    @NestedConfigurationProperty
    private ExtendsDataSourceProperties bank;

    /**
     * 판매 이력을 저장할 데이터베이스 정보
     *
     * @see ExtendsDataSourceProperties
     */
    @NestedConfigurationProperty
    private ExtendsDataSourceProperties store;

    /**
     * {@code XA} 활성화 여부
     *
     * <p>XA가 활성화 된다면 분산 트랜잭션(Distribute Transaction)
     * 설정을 통해 서로 다른 데이터베이스들의 트랜잭션이 통합되도록 설정됩니다.</p>
     *
     * <p>XA가 비활성화 된다면 서로 다른 데이터베이스들의 트랜잭션이 개별적으로
     * 동작하도록 설정됩니다.</p>
     *
     * @see <a href="https://ko.wikipedia.org/wiki/X/Open_XA">Extends Architecture(XA)</a>
     */
    private boolean xaEnabled;

    /**
     * {@link AppProperties} 클래스의 기본 생성자입니다.
     */
    private AppProperties() {
        this.xaEnabled = false;
    }

    /**
     * 거래 이력을 저장하는 데이터베이스 정보를 반환합니다.
     *
     * @return 거래 이력 담당 데이터베이스 정보
     */
    @NonNull
    public ExtendsDataSourceProperties getStore() {
        return this.store;
    }

    /**
     * 판매 이력을 저장하는 데이터베이스 정보를 반환합니다.
     *
     * @return 판매 이력 담당 데이터베이스 정보
     */
    @NonNull
    public ExtendsDataSourceProperties getBank() {
        return this.bank;
    }

    /**
     * XA 활성화 여부를 반환합니다.
     *
     * @return XA 활성화 여부
     */
    @NonNull
    public boolean isXaEnabled() {
        return this.xaEnabled;
    }

    /**
     * 거래 이력을 저장하는 데이터베이스 정보를 설정합니다.
     *
     * @param bank 거래 이력 담당 데이터베이스 정보
     */
    public void setBank(@NonNull final ExtendsDataSourceProperties bank) {
        this.bank = bank;
    }

    /**
     * 판매 이력을 저장하는 데이터베이스 정보를 설정합니다.
     *
     * @param store 판매 이력 담당 데이터베이스 정보
     */
    public void setStore(@NonNull final ExtendsDataSourceProperties store) {
        this.store = store;
    }

    /**
     * XA 활성화 여부를 설정합니다.
     *
     * @param xaEnabled XA 활성화 여부
     */
    public void setXaEnabled(@NonNull final boolean xaEnabled) {
        this.xaEnabled = xaEnabled;
    }

}