package org.actech.smart.trader.sync.market.parser.board;

import org.actech.smart.trader.sync.market.entity.BoardClassification;
import org.springframework.stereotype.Component;

/**
 * Created by paul on 2018/3/12.
 */
@Component
public class BoardClassificationDyrHtmlParser extends BoardClassificationHtmlParser {
    @Override
    protected BoardClassification setIndex(BoardClassification classification) {
        classification.setDyr(classification.getIndex());
        return classification;
    }
}
