package org.actech.smart.trader.core.parser;

import org.actech.smart.trader.core.trait.Cacheable;
import org.actech.smart.trader.core.net.DocumentCache;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by paul on 2018/3/11.
 */
public abstract class CacheableParser implements HtmlParser, Cacheable {
    @Autowired
    private DocumentCache cache;

    @Override
    public boolean shouldParse(String url) {
        return shouldParse(cache.get(url));
    }

    public abstract boolean shouldParse(Document document);

    @Override
    public void parse(String url, String dateStr) {
        parse(cache.get(url), dateStr);
    }

    public abstract void parse(Document document, String dateStr);
}
