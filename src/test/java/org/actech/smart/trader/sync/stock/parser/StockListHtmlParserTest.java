package org.actech.smart.trader.sync.stock.parser;

import org.actech.smart.trader.Application;
import org.actech.smart.test.configuration.UnitTestConfiguration;
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

import static org.junit.Assert.*;

/**
 * Created by paul on 2018/3/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, UnitTestConfiguration.class})
@ActiveProfiles("dev")
public class StockListHtmlParserTest {
    @Autowired
    private StockListHtmlParser parser;

    @Test
    public void shouldParse() throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:股票代码查询一览表.htm");
        Document document = Jsoup.parse(resource.getFile(), "GB2312");
        assertTrue(parser.shouldParse(document));
    }
}