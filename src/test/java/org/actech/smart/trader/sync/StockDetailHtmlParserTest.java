package org.actech.smart.trader.sync;

import org.actech.smart.trader.Application;
import org.actech.smart.trader.UnitTestConfiguration;
import org.actech.smart.trader.sync.entity.StockClassification;
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

/**
 * Created by paul on 2018/3/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, UnitTestConfiguration.class})
@ActiveProfiles("dev")
public class StockDetailHtmlParserTest {
    @Autowired
    private StockDetailHtmlParser stockDetailHtmlParser;

    @Test
    public void shouldParseStockDetail() throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:上海能源.htm");
        File file = resource.getFile();

        StockClassification stockClassification = new StockClassification();
        stockDetailHtmlParser.parse(file, "GB2312", stockClassification);
        // assert(stockClassification.getDratio() != 0);
    }

}