package org.actech.smart.trader.sync.parser.def;

import org.actech.smart.trader.Application;
import org.actech.smart.trader.UnitTestConfiguration;
import org.actech.smart.trader.sync.entity.StockClassification;
import org.actech.smart.trader.sync.repositories.StockClassificationRepository;
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

import java.io.IOException;
import java.util.Optional;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

import static org.hamcrest.core.Is.is;

/**
 * Created by paul on 2018/3/12.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, UnitTestConfiguration.class})
@ActiveProfiles("dev")
public class StockClassificationHtmlParserTest {
    @Autowired
    private StockClassificationHtmlParser parser;

    @Autowired
    private StockClassificationRepository repository;

    @Test
    public void shouldParseStockClassification() throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:个股市盈率.htm");
        Document document = Jsoup.parse(resource.getFile(), "UTF-8");

        parser.parse(document, "");


        Optional<StockClassification> optional = repository.findById("300331");
        assertTrue(optional.isPresent());

        StockClassification classification = optional.get();
        assertThat(classification, is(notNullValue()));
    }
}