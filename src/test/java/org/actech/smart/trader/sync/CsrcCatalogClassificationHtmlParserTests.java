package org.actech.smart.trader.sync;

import org.actech.smart.trader.Application;
import org.actech.smart.trader.UnitTestConfiguration;
import org.actech.smart.trader.sync.market.entity.CsrcIndustrialClassification;
import org.actech.smart.trader.sync.market.service.CsrcTrackSyncService;
import org.actech.smart.trader.sync.market.repository.CsrcIndustrialClassificationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

/**
 * Created by paul on 2018/3/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, UnitTestConfiguration.class})
@ActiveProfiles("dev")
public class CsrcCatalogClassificationHtmlParserTests {
    @Autowired
    private CsrcTrackSyncService parser;
    @Autowired
    private CsrcIndustrialClassificationRepository repository;

    @Test
    public void shouldParseIndustryHtml() throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:证监会行业市盈率.htm");

        File file = resource.getFile();

        // service.parser(file, "UTF-8");

        CsrcIndustrialClassification CsrcIndustrialClassification = repository.findById("Q83").get();
        assertThat(CsrcIndustrialClassification, is(notNullValue()));
    }
}
