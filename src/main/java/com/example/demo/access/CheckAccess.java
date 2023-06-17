package com.example.demo.access;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation for marking parameter of the method which should be verified with {@link AccessService}
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckAccess {
    @AliasFor("context")
    CheckAccessType value() default CheckAccessType.NONE;

    @AliasFor("value")
    CheckAccessType context() default CheckAccessType.NONE;
}

