package org.actech.smart.trader.sync.stock.resolver;

import org.actech.smart.trader.core.resolver.HtmlResolver;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by paul on 2018/3/12.
 */
public abstract class StockHtmlResolver<T> implements HtmlResolver {
    @Override
    public Elements navigatorToContents(Document document) {
        if (document == null) return new Elements();

        Elements elements = document.getElementsByClass("table-bg");

        assert(elements.size() == 1);

        Element element = elements.get(0);
        elements = element.getElementsByTag("tbody");

        assert(elements.size() >= 1);

        element = elements.get(0);
        return element.getElementsByTag("tr");
    }
}
