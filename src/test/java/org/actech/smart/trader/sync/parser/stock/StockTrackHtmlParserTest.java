package org.actech.smart.trader.sync.parser.stock;

import org.actech.smart.trader.Application;
import org.actech.smart.trader.UnitTestConfiguration;
import org.actech.smart.trader.sync.entity.StockClassification;
import org.actech.smart.trader.sync.entity.StockFundTrack;
import org.actech.smart.trader.sync.parser.stock.StockTrackHtmlParser;
import org.actech.smart.trader.sync.repositories.StockClassificationRepository;
import org.actech.smart.trader.sync.repositories.StockFundTrackRepository;
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

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.junit.Assert.*;
/**
 * Created by paul on 2018/3/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, UnitTestConfiguration.class})
@ActiveProfiles("dev")
public class StockTrackHtmlParserTest {
    @Autowired
    private StockTrackHtmlParser parser;

    @Autowired
    private StockFundTrackRepository repository;

    @Test
    public void shouldParseStockTrack() throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:个股市盈率.htm");
        Document document = Jsoup.parse(resource.getFile(), "UTF-8");

        parser.parse(document, "2018-03-13");


        StockFundTrack stockFundTrack = repository.findByReleaseAndCode("2018-03-13", "300331");
        assertThat(stockFundTrack, is(notNullValue()));
    }
}