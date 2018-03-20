package org.actech.smart.trader.registry.service;

import org.actech.smart.test.configuration.UnitTestConfiguration;
import org.actech.smart.trader.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by paul on 2018/3/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, UnitTestConfiguration.class})
@ActiveProfiles("dev")
public class ServiceRegistryCenterTest {
    @Autowired
    private ServiceRegistryCenter service;

    @Test
    public void shouldCatchAllRegisteredService() {
        String description = service.dashboard();
        System.out.println(description);
    }
}