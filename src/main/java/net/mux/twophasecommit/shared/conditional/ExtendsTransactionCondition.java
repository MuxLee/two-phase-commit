package net.mux.twophasecommit.shared.conditional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

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

    @AliasFor(
            annotation = ConditionalOnProperty.class,
            attribute = "havingValue"
    )
    String havingValue() default "false";

    @AliasFor(
            annotation = ConditionalOnProperty.class,
            attribute = "matchIfMissing"
    )
    boolean matchIfMissing() default false;

    @AliasFor(
            annotation = ConditionalOnProperty.class,
            attribute = "value"
    )
    String value() default "app.xa-enabled";

}
