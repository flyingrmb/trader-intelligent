package org.actech.smart.trader.sync.parser.csrc;

import org.actech.smart.trader.sync.entity.CsrcIndustrialClassification;
import org.actech.smart.trader.sync.parser.def.CatalogClassificationHtmlParser;
import org.actech.smart.trader.sync.repositories.CsrcIndustrialClassificationRepository;
import org.actech.smart.trader.sync.resolver.CsrcIndustrialHtmlResolver;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by paul on 2018/3/12.
 */
public abstract class CsrcIndustrialClassificationHtmlParser extends CatalogClassificationHtmlParser<CsrcIndustrialClassification> {
    @Autowired
    public void setIndustrialClassificationRepository(CsrcIndustrialClassificationRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setStockClassificationHtmlParser(CsrcStockClassificationHtmlParser parser) {
        this.stockParser = parser;
    }

    @Autowired
    public void setIndustrialClassificationHtmlResolver(CsrcIndustrialHtmlResolver htmlResolver) {
        this.resolver = htmlResolver;
    }
}
