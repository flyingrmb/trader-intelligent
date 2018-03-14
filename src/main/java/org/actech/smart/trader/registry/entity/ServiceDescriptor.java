package org.actech.smart.trader.registry.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.actech.smart.trader.registry.annotation.ServicePoint;

import java.lang.reflect.Method;

/**
 * Created by paul on 2018/3/14.
 */
@NoArgsConstructor
@Data
public class ServiceDescriptor {
    private String serviceCode;

    private ServicePoint servicePoint;

    private Object bean;
    private Method method;
}
