package org.actech.smart.trader.sync.parser.board;

import org.actech.smart.trader.sync.entity.BoardClassification;
import org.actech.smart.trader.sync.entity.CsiIndustrialClassification;
import org.springframework.stereotype.Component;

/**
 * Created by paul on 2018/3/12.
 */
@Component
public class BoardClassificationDyrHtmlParser extends BoardClassificationHtmlParser {
    @Override
    protected BoardClassification merge(BoardClassification newObj, BoardClassification oldObj) {
        if (isEqual(newObj, oldObj)) return null;
        newObj = merge0(newObj, oldObj);

        newObj.setDyr(newObj.getIndex());
        return newObj;
    }

    @Override
    protected boolean isEqual(BoardClassification newObj, BoardClassification oldObj) {
        boolean equal = super.isEqual(newObj, oldObj);
        if (!equal) return false;

        return newObj.getIndex() == oldObj.getDyr();
    }
}
