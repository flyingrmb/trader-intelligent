package org.actech.smart.trader.sync.stock.parser;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HTMLParser;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.actech.smart.trader.Application;
import org.actech.smart.test.configuration.UnitTestConfiguration;
import org.actech.smart.trader.sync.market.entity.StockClassification;
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
import java.net.URL;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;
/**
 * Created by paul on 2018/3/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, UnitTestConfiguration.class})
@ActiveProfiles("dev")
public class StockTraderHtmlParserTest {
    @Autowired
    private StockTraderHtmlParser parser;

    @Test
    public void navigateToContent() throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:个股行情_网易财经.htm");

        String url = "http://quotes.money.163.com/trade/lsjysj_603088.html?year=2018&season=1";

        String content = new String(readAllBytes(get(resource.getURI())));
        StringWebResponse response = new StringWebResponse(new String(content), new URL(url));

        WebClient webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setDownloadImages(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setRedirectEnabled(false);
        webClient.getOptions().setAppletEnabled(false);
        webClient.getOptions().setActiveXNative(false);


        HtmlPage page =  HTMLParser.parseHtml(response, webClient.getCurrentWindow());


        assertThat(parser.shouldParse(page), is(true));
    }

    @Test
    public void downloadHtmlContentAndRunScript() throws IOException {
        try(final WebClient webClient = new WebClient()) {
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            final HtmlPage page = webClient.getPage("http://quotes.money.163.com/trade/lsjysj_603088.html?year=2018&season=1");
            System.out.println(page);
        }
    }

    @Test
    public void shouldParseContent() throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:个股行情_网易财经.htm");

        String url = "http://quotes.money.163.com/trade/lsjysj_603088.html?year=2018&season=1";

        String content = new String(readAllBytes(get(resource.getURI())));
        StringWebResponse response = new StringWebResponse(new String(content), new URL(url));

        WebClient webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setDownloadImages(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setRedirectEnabled(false);
        webClient.getOptions().setAppletEnabled(false);
        webClient.getOptions().setActiveXNative(false);


        StockClassification classification = new StockClassification();
        classification.setCode("603088");
        classification.setName("宁波精达");
        HtmlPage page =  HTMLParser.parseHtml(response, webClient.getCurrentWindow());
        parser.parse(page, classification);
    }

}