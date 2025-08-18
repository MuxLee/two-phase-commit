package net.mux.twophasecommit.database.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.lang.NonNull;

/**
 * 확장된 데이터베이스 정보를 설정합니다.
 *
 * <p>{@code Spring}에서 제공하는 기본 데이터베이스 정보
 * 외에 별도로 필요한 다른 설정들을 작성할 수 있습니다.</p>
 */
public class ExtendsDataSourceProperties extends DataSourceProperties {

    /**
     * {@code JPA}에서 관리할 엔티티들의 패키지 범위
     */
    private String[] scanningPackages;

    /**
     * {@link ExtendsDataSourceProperties} 클래스의 기본 생성자입니다.
     */
    private ExtendsDataSourceProperties() {
        this.scanningPackages = new String[0];
    }

    /**
     * 엔티티 패키지 범위를 반환합니다.
     *
     * @return 엔티티 패키지 범위
     */
    @NonNull
    public String[] getScanningPackages() {
        return this.scanningPackages;
    }

    /**
     * 관리할 엔티티 패키지 범위를 설정합니다.
     *
     * @param scanningPackages 엔티티 패키지 범위
     */
    public void setScanningPackages(@NonNull final String[] scanningPackages) {
        this.scanningPackages = scanningPackages;
    }

}