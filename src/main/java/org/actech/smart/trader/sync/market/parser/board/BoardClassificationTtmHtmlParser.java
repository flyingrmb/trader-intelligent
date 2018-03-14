package org.actech.smart.trader.sync.market.parser.board;

import org.actech.smart.trader.sync.market.entity.BoardClassification;
import org.actech.smart.trader.sync.market.parser.board.BoardClassificationHtmlParser;
import org.springframework.stereotype.Component;

/**
 * Created by paul on 2018/3/12.
 */
@Component
public class BoardClassificationTtmHtmlParser extends BoardClassificationHtmlParser {
    @Override
    protected BoardClassification setIndex(BoardClassification classification) {
        classification.setTtm(classification.getIndex());
        return classification;
    }
}
