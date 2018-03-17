package org.actech.smart.trader.core.net;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.actech.smart.trader.core.util.LimitedSizeCache;
import org.actech.smart.trader.registry.annotation.Registry;
import org.actech.smart.trader.registry.annotation.ServicePoint;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by paul on 2018/3/13.
 */
@Component
@Registry
public class NetworkResourceCache {
    private final Log logger = LogFactory.getLog(getClass());
    private final static int maxRetryTime = 3; // max retry time when network is bad.

    @Autowired
    private Environment environment;

    private final LimitedSizeCache cache = new LimitedSizeCache(100);

    @PostConstruct
    protected void afterPropertiesSet() {

    }

    @ServicePoint(name="清空下载的缓存页面")
    public String clear(String param) {
        cache.clear();
        return "SUCCESS";
    }

    public <T> T get(String url, Class<T> type) {
        Assert.notNull(type, "type should not be null.");

        if (!containsProfile("dev") && cache.get(url, type) == null)
            cache.add(url, downloadResource(url, 0, type));

        return (T)cache.get(url, type);
    }

    private <T> T downloadResource(String url, int retryTime, Class<T> type) {


        logger.info("下载数据，url=" + url + ", retryTime=" + retryTime);
        if (retryTime >= maxRetryTime) {
            logger.warn("网络信号弱，无法下载网页内容，url=" + url);
            return null;
        }
        try {
            if (type == Document.class)
                return (T)Jsoup.connect(url).get();

            return (T) getPageWithHtmlUnit(url);
        } catch (Throwable e) {
            retryTime++;
            return downloadResource(url, retryTime, type);
        }
    }

    private boolean containsProfile(String profile) {
        String[] profiles = environment.getActiveProfiles();
        if (profiles == null) return false;

        for (String configProfile : profiles) {
            if (profile.equals(configProfile)) return true;
        }
        return false;
    }

    private HtmlPage getPageWithHtmlUnit(String url) throws IOException {
        WebClient webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setDownloadImages(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setRedirectEnabled(false);
        webClient.getOptions().setAppletEnabled(false);
        webClient.getOptions().setActiveXNative(false);
        return webClient.getPage(url);
    }
}
