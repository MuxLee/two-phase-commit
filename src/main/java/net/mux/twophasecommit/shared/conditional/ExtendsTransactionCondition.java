package net.mux.twophasecommit.shared.conditional;

import net.mux.twophasecommit.database.config.AppProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * XA 활성화 조건적 어노테이션입니다.
 *
 * <p>기본적으로 XA 비활성화({@code "false"})가 설정되어 있습니다.
 *
 * <p>만약, XA를 활성화할 필요가 있다면
 * {@link ExtendsTransactionCondition#havingValue()}를
 * {@code "true"}로 설정합니다.</p>
 *
 * <p>{@link ExtendsTransactionCondition#value()}에 작성되어있는
 * 프로퍼티 요소는 {@link AppProperties#xaEnabled} 요소를 가리키므로
 * 해당 값의 수정을 권유하지 않습니다.</p>
 *
 * 이 어노테이션은 다음 조건적 어노테이션과 동일합니다.
 * <blockquote><pre>
 * &#64;ConditionalOnProperty(
 *     havingValue = "false",
 *     matchIfMissing = false,
 *     value = "app.xa-enabled"
 * )
 * </pre></blockquote>
 */
@ConditionalOnProperty
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(
        value = {
            ElementType.TYPE,
            ElementType.METHOD
        }
)
public @interface ExtendsTransactionCondition {

    /**
     * XA 활성화 조건({@link AppProperties#xaEnabled})값과
     * 매칭되는 값을 설정합니다.
     *
     * @return 기본값은 {@code false} 입니다.
     */
    @AliasFor(
            annotation = ConditionalOnProperty.class,
            attribute = "havingValue"
    )
    String havingValue() default "false";

    /**
     * XA 활성화 조건값과 매칭되는 값이 존재하지 때,
     * 이 어노테이션의 적용 여부를 설정합니다.
     *
     * @return 기본값은 {@code false} 입니다.
     */
    @AliasFor(
            annotation = ConditionalOnProperty.class,
            attribute = "matchIfMissing"
    )
    boolean matchIfMissing() default false;

    /**
     * XA 활성화 조건값을 매칭할 프로퍼티를 설정합니다.
     *
     * <p><i>이 설정은 커스터마이징이 아닌 경우, 별도의 수정을
     * 권유하지 않습니다.</i></p>
     *
     * @return 기본값은 {@code "app.xa-enabled"} 입니다.
     */
    @AliasFor(
            annotation = ConditionalOnProperty.class,
            attribute = "value"
    )
    String value() default "app.xa-enabled";

}