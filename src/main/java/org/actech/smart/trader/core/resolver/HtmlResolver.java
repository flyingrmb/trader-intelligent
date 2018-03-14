package org.actech.smart.trader.core.resolver;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Collection;

/**
 * Created by paul on 2018/3/12.
 */
public interface HtmlResolver<T> {
    Elements navigatorToContents(Document document);
    Collection<T> resolve(Document document);
}
