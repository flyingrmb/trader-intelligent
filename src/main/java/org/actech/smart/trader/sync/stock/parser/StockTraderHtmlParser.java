package org.actech.smart.trader.sync.stock.parser;

import org.actech.smart.trader.core.parser.CacheableParser;
import org.actech.smart.trader.registry.annotation.ServicePoint;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

/**
 * Created by paul on 2018/3/15.
 */
@Service
public class StockTraderHtmlParser extends CacheableParser {
    @Override
    public boolean shouldParse(Document document) {
        return false;
    }

    @Override
    public void parse(Document document, String dateStr) {

    }
}
