package org.actech.smart.trader.web.cache;

import org.actech.smart.trader.core.common.LimitedSizeCache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by paul on 2018/3/13.
 */
@Component
public class DocumentUrlCache {
    private final Log logger = LogFactory.getLog(getClass());
    private final static int maxRetryTime = 3; // max retry time when network is bad.

    @Autowired
    private Environment environment;

    private LimitedSizeCache<String, Document> cache = new LimitedSizeCache<String, Document>(100);

    public Document get(String url) {
        if (cache.get(url) == null) {
            cache.add(url, downloadWithRetryStrategy(url, 0));
        }
        return cache.get(url);
    }

    private Document downloadWithRetryStrategy(String url, int retryTime) {
        if (containsProfile("dev")) return null;

        logger.info("下载数据，url=" + url + ", retryTime=" + retryTime);
        if (retryTime >= maxRetryTime) {
            logger.warn("网络信号弱，无法下载网页内容，url=" + url);
            return null;
        }
        try {
            return Jsoup.connect(url).get();
        } catch (Exception e) {
            retryTime++;
            return downloadWithRetryStrategy(url, retryTime);
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
}
