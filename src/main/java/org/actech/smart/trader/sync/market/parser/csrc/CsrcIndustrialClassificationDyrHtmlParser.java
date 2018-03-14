package org.actech.smart.trader.sync.market.parser.csrc;

import org.actech.smart.trader.sync.market.entity.CsrcIndustrialClassification;
import org.springframework.stereotype.Component;
/**
 * Created by paul on 2018/3/11.
 */
@Component
public class CsrcIndustrialClassificationDyrHtmlParser extends CsrcIndustrialClassificationHtmlParser {
    @Override
    protected CsrcIndustrialClassification setIndex(CsrcIndustrialClassification classification) {
        classification.setDyr(classification.getIndex());
        return classification;
    }
}
