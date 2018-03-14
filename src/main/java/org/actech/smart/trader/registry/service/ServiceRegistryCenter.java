package org.actech.smart.trader.registry.service;

/**
 * Created by paul on 2018/3/14.
 */
public interface ServiceRegistryCenter {
    String dashboard();
    Object call(String serviceName, String parameter);
}
