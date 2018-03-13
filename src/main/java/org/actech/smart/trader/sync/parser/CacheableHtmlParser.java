package org.actech.smart.trader.sync.parser;

import org.actech.smart.trader.core.common.LimitedSizeCache;
import org.actech.smart.trader.core.trait.Cacheable;
import org.actech.smart.trader.web.cache.DocumentUrlCache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * Created by paul on 2018/3/11.
 */
public abstract class CacheableHtmlParser implements HtmlParser, Cacheable {
    @Autowired
    private DocumentUrlCache cache;

    @Override
    public boolean containsData(String url) {
        return containsData(cache.get(url));
    }

    protected boolean containsData(Document document) {
        return true;
    }

    @Override
    public void parse(String url, String dateStr) {
        parse(cache.get(url), dateStr);
    }

    protected abstract void parse(Document document, String dateStr);
}
