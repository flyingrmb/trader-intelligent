package org.actech.smart.trader.sync.resolver;

import org.actech.smart.trader.Application;
import org.actech.smart.trader.UnitTestConfiguration;
import org.actech.smart.trader.sync.market.entity.BoardClassification;
import org.actech.smart.trader.sync.market.resolver.BoardHtmlResolver;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by paul on 2018/3/12.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, UnitTestConfiguration.class})
@ActiveProfiles("dev")
public class BoardHtmlResolverTest {
    @Autowired
    private BoardHtmlResolver resolver;

    @Test
    public void iteratorToContents() throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:主板行业市盈率.htm");

        Document document = Jsoup.parse(resource.getFile(), "UTF-8");
        Elements elements = resolver.navigatorToContents(document);

        assertTrue(elements.size() > 0);
    }

    @Test
    public void resolveBoardClassification() throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:主板行业市盈率.htm");

        Document document = Jsoup.parse(resource.getFile(), "UTF-8");

        Collection<BoardClassification> result = resolver.resolve(document);
        assertTrue(result.size() > 0);
    }

}