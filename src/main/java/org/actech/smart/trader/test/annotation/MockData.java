package org.actech.smart.trader.test.annotation;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by paul on 2018/3/18.
 */
public @interface MockData {
    String file();
    Class<? extends CrudRepository> repository();
    // Class<?> entity();
}
