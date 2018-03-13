package org.actech.smart.trader.sync.parser.csrc;

import org.actech.smart.trader.sync.entity.CsrcIndustrialClassification;
import org.springframework.beans.EntityUtils;
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
