package org.actech.smart.trader.sync.parser.board;

import org.actech.smart.trader.sync.entity.BoardClassification;
import org.actech.smart.trader.sync.entity.CsrcIndustrialClassification;
import org.springframework.beans.EntityUtils;
import org.springframework.stereotype.Component;

/**
 * Created by paul on 2018/3/12.
 */
@Component
public class BoardClassificationPbHtmlParser extends BoardClassificationHtmlParser {
    @Override
    protected BoardClassification setIndex(BoardClassification classification) {
        classification.setPb(classification.getIndex());
        return classification;
    }
}
