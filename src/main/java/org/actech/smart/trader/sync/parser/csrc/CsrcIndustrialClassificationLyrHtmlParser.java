package org.actech.smart.trader.sync.parser.csrc;

import org.actech.smart.trader.sync.entity.CsrcIndustrialClassification;
import org.actech.smart.trader.sync.parser.stock.StockTrackHtmlParser;
import org.springframework.beans.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by paul on 2018/3/11.
 */
@Component
public class CsrcIndustrialClassificationLyrHtmlParser extends CsrcIndustrialClassificationHtmlParser {
    @Autowired
    public void setStockTrackParser(StockTrackHtmlParser stockTrackParser) {
        this.stockTrackParser = stockTrackParser;
    }

    @Override
    protected CsrcIndustrialClassification setIndex(CsrcIndustrialClassification classification) {
        classification.setLyr(classification.getIndex());
        return classification;
    }
}
