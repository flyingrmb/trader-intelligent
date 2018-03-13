package org.actech.smart.trader.config;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.map.repository.config.EnableMapRepositories;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by paul on 2018/3/9.
 */
public class MapRepositoriesConfigurationExtensionIntegrationTests {
    @Test
    public void registersDefaultTemplateIfReferenceNotCustomized() {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        assertThat(Arrays.asList(context.getBeanDefinitionNames()), hasItem("mapKeyValueTemplate"));
    }

    @Configuration
    @EnableMapRepositories
    static class Config {}
}
