package org.actech.smart.trader.registry.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by paul on 2018/3/14.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ServicePoint {
    String code() default "";
    String name() default "";
    boolean async() default false;
    String[] example() default "";
}
