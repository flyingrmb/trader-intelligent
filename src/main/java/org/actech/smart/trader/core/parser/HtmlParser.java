package org.actech.smart.trader.core.parser;

/**
 * Created by paul on 2018/3/11.
 */
public interface HtmlParser {
    boolean shouldParse(String url);
    void parse(String url, String dateStr);
}
