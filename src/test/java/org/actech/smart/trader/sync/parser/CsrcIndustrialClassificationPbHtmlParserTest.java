package org.actech.smart.trader.sync.parser;

import org.actech.smart.trader.Application;
import org.actech.smart.test.configuration.UnitTestConfiguration;
import org.actech.smart.trader.sync.market.entity.CsrcIndustrialClassification;
import org.actech.smart.trader.sync.market.parser.csrc.CsrcIndustrialClassificationPbHtmlParser;
import org.actech.smart.trader.sync.market.repository.CsrcIndustrialClassificationRepository;
import org.actech.smart.trader.sync.market.repository.StockClassificationRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by paul on 2018/3/11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, UnitTestConfiguration.class})
@ActiveProfiles("dev")
public class CsrcIndustrialClassificationPbHtmlParserTest {
    @Autowired
    private CsrcIndustrialClassificationPbHtmlParser parser;

    @Autowired
    private CsrcIndustrialClassificationRepository industrialClassificationRepository;

    @Autowired
    private StockClassificationRepository stockClassificationRepository;

    @Test
    public void trueIfDocumentContainsContent() throws Exception {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:证监会行业市净率.htm");
        Document document = Jsoup.parse(resource.getFile(), "UTF-8");
        assertTrue(parser.shouldParse(document));
    }

    @Test
    public void falseIfDocumentContainsNoContent() throws Exception {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:证监会行业市净率空.htm");
        Document document = Jsoup.parse(resource.getFile(), "UTF-8");
        assertFalse(parser.shouldParse(document));
    }

    @Test
    public void saveInfoWhenDocumentContainsContent() throws Exception {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:证监会行业市净率.htm");
        Document document = Jsoup.parse(resource.getFile(), "UTF-8");
        parser.parse(document, "2018-03-09");

        CsrcIndustrialClassification classification = industrialClassificationRepository.findByReleaseAndCode("2018-03-09", "A");
        assertThat(classification.getCode(), is("A"));
    }
}