package org.actech.smart.trader.registry.service.impl;

import org.actech.smart.trader.registry.service.ServiceEncoder;
import org.springframework.stereotype.Service;
import sun.jvm.hotspot.utilities.Assert;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by paul on 2018/3/14.
 */
@Service
public class ServiceEncoderImpl implements ServiceEncoder {
    private Set<String> registered = new HashSet<String>();

    @Override
    public synchronized String register(String serviceCode) {
        String validServiceCode = serviceCode;
        if (alreadyRegistered(serviceCode))
            validServiceCode = generateServiceCode();

        register0(validServiceCode);
        return validServiceCode;
    }

    private boolean alreadyRegistered(String serviceCode) {
        if (serviceCode == null || serviceCode.trim().length() == 0)
            return true;

        return registered.contains(serviceCode);
    }

    private boolean[] ascii = new boolean[128];
    private int iterator = 0;

    @PostConstruct
    public void afterPropertiesSet() {
        for (int i=48; i<58; i++) {
            ascii[i] = true;
        }

        for (int i=65; i<91; i++) {
            ascii[i] = true;
        }

        for (int i=97; i<123; i++) {
            ascii[i] = true;
        }
    }

    private String generateServiceCode() {
        iterator++;

        while (iterator < ascii.length) {
            if (ascii[iterator] && !registered.contains(Character.toString((char)iterator)))
                return Character.toString((char)iterator);

            iterator++;
        }

        Assert.that(false, "服务编码注册已满，请扩充服务编码！");
        return "Nothing.";
    }

    private void register0(String serviceCode) {
        registered.add(serviceCode);
    }
}
