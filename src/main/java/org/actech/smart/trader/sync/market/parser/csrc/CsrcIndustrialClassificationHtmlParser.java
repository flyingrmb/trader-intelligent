package org.actech.smart.trader.sync.market.parser.csrc;

import org.actech.smart.trader.sync.market.entity.CsrcIndustrialClassification;
import org.actech.smart.trader.sync.market.parser.CatalogParser;
import org.actech.smart.trader.sync.stock.parser.CsrcStockClassificationHtmlParser;
import org.actech.smart.trader.sync.market.repository.CsrcIndustrialClassificationRepository;
import org.actech.smart.trader.sync.market.resolver.CsrcIndustrialHtmlResolver;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by paul on 2018/3/12.
 */
public abstract class CsrcIndustrialClassificationHtmlParser extends CatalogParser<CsrcIndustrialClassification> {
    @Autowired
    public void setIndustrialClassificationRepository(CsrcIndustrialClassificationRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setStockClassificationHtmlParser(CsrcStockClassificationHtmlParser parser) {
        this.stockClassificationParser = parser;
    }

    @Autowired
    public void setIndustrialClassificationHtmlResolver(CsrcIndustrialHtmlResolver industrialResolver) {
        this.industrialResolver = industrialResolver;
    }
}
