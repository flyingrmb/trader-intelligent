package org.actech.smart.trader.sync.market.parser.csi;

import org.actech.smart.trader.sync.market.entity.CsiIndustrialClassification;
import org.actech.smart.trader.sync.market.parser.CatalogParser;
import org.actech.smart.trader.sync.stock.parser.CsiStockClassificationHtmlParser;
import org.actech.smart.trader.sync.market.repository.CsiIndustrialClassificationRepository;
import org.actech.smart.trader.sync.market.resolver.CsiIndustrialHtmlResolver;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by paul on 2018/3/12.
 */
public abstract class CsiIndustrialClassificationHtmlParser extends CatalogParser<CsiIndustrialClassification> {
    @Autowired
    public void setIndustrialClassificationRepository(CsiIndustrialClassificationRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setStockClassificationHtmlParser(CsiStockClassificationHtmlParser parser) {
        this.stockClassificationParser = parser;
    }

    @Autowired
    public void setIndustrialClassificationHtmlResolver(CsiIndustrialHtmlResolver htmlResolver) {
        this.industrialResolver = htmlResolver;
    }
}
