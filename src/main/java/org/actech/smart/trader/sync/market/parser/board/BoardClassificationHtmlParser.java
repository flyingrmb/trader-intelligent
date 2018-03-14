package org.actech.smart.trader.sync.market.parser.board;

import org.actech.smart.trader.sync.market.entity.BoardClassification;
import org.actech.smart.trader.sync.market.parser.CatalogParser;
import org.actech.smart.trader.sync.market.repository.BoardClassificationRepository;
import org.actech.smart.trader.sync.market.resolver.BoardHtmlResolver;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by paul on 2018/3/12.
 */
public abstract class BoardClassificationHtmlParser extends CatalogParser<BoardClassification> {
    @Autowired
    public void setIndustrialClassificationRepository(BoardClassificationRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setIndustrialClassificationHtmlResolver(BoardHtmlResolver htmlResolver) {
        this.industrialResolver = htmlResolver;
    }
}
