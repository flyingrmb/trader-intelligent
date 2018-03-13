package org.actech.smart.trader.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

/**
 * Created by paul on 2018/3/10.
 */
public class JSoupTests {
    @Test
    public void shouldLoadResourceFromClassPath() throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:证监会行业市盈率.htm");
        assertThat(resource, is(notNullValue()));

        File file = resource.getFile();
        assertThat(file, is(notNullValue()));
    }

    @Test
    public void generateDocumentByResourceFile() throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:证监会行业市盈率.htm");

        File file = resource.getFile();
        Document document = Jsoup.parse(file, "UTF-8");
        assertThat(document, is(notNullValue()));
    }
}
