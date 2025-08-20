package net.mux.twophasecommit.bank;

import jakarta.persistence.EntityManagerFactory;

import javax.sql.DataSource;

/**
 * 거래 이력과 관련된 {@code Bean} 이름이 작성된 상수명 모음입니다.
 *
 * <p>이 클래스에 작성된 상수명을 이용하여 {@code Non-XA} 데이터베이스
 * 연동, {@code XA} 데이터베이스 연동 시, 생성될 {@code Bean}의 이름을
 * 결정합니다.</p>
 *
 * <p>다음과 같이 사용될 수 있습니다.</p>
 * <blockquote>
 * <pre>@Bean(value = Banks.DATA_SOURCE)</pre>
 * </blockquote>
 *
 * <p>{@code Bean} 컨네이터로 부터 찾을 때도 사용될 수 있습니다.</p>
 * <blockquote>
 * <pre>applicationContext.getBean(Banks.DATA_SOURCE, DataSource.class)</pre>
 * </blockquote>
 */
public final class Banks {

    /**
     * {@link DataSource} {@code Bean}의 이름
     */
    public static final String DATA_SOURCE = "bankDataSource";

    /**
     * {@link EntityManagerFactory} {@code Bean}의 이름
     */
    public static final String ENTITY_MANAGER_FACTORY_BEAN = "bankEntityManagerFactoryBean";

    /**
     * {@code JPA} 저장소 스캔 패키지 범위
     */
    public static final String PACKAGE = "net.mux.twophasecommit.bank";

    /**
     * {@code Jakarta} 또는 {@code Spring}의
     * {@code TransactionManager} {@code Bean}의 이름
     */
    public static final String TRANSACTION_MANAGER = "bankTransactionManager";


}