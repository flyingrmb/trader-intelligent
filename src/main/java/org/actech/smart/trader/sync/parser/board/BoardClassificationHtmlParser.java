package org.actech.smart.trader.sync.parser.board;

import org.actech.smart.trader.sync.entity.BoardClassification;
import org.actech.smart.trader.sync.parser.CatalogParser;
import org.actech.smart.trader.sync.repositories.BoardClassificationRepository;
import org.actech.smart.trader.sync.resolver.BoardHtmlResolver;
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
