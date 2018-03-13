package org.actech.smart.trader.sync.parser;

/**
 * Created by paul on 2018/3/11.
 */
public interface HtmlParser {
    boolean containsData(String url);
    void parse(String url, String dateStr);
}
