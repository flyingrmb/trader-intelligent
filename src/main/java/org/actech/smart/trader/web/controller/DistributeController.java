package org.actech.smart.trader.web.controller;

import org.actech.smart.trader.web.service.DistributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * Created by paul on 2018/3/14.
 */
@RestController
public class DistributeController {
    @Autowired
    private DistributeService distributeService;

    @RequestMapping("service/{serviceCode}")
    public String callServiceWithoutParameter(@PathVariable String serviceCode) {
        return callService(serviceCode, null);
    }

    @RequestMapping("service/{serviceCode}/param/{parameter}")
    public String callService(@PathVariable String serviceCode, @PathVariable String parameter) {
        return distributeService.distribute(serviceCode, parameter);
    }
}
