package org.actech.smart.trader.web.controller;

import org.actech.smart.trader.registry.service.ServiceRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by paul on 2018/3/14.
 */
@RestController
public class ServiceRegistryController {
    @Autowired
    private ServiceRegistryCenter registryCenter;

    @RequestMapping("service/help")
    public String getServiceDescription() {
        return registryCenter.dashboard();
    }
}
