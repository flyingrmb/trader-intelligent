package org.actech.smart.trader.registry.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by paul on 2018/3/14.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Registry {
}
