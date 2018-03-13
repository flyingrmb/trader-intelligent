package org.actech.smart.trader.sync.resolver;

import org.actech.smart.trader.sync.entity.CatalogClassification;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by paul on 2018/3/12.
 */
public abstract class CatalogHtmlResolver<T extends CatalogClassification> implements HtmlResolver {
    @Override
    public Elements navigatorToContents(Document document) {
        Elements elements = document.getElementsByClass("companyBox");
        assert(elements.size() == 1);

        Element element = elements.get(0);
        return element.getElementsByClass("list-div-table");
    }
}
