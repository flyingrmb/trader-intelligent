package org.actech.smart.trader.sync.parser.csi;

import org.actech.smart.trader.sync.entity.CsiIndustrialClassification;
import org.springframework.beans.EntityUtils;
import org.springframework.stereotype.Component;

/**
 * Created by paul on 2018/3/12.
 */
@Component
public class CsiIndustrialClassificationTtmHtmlParser extends CsiIndustrialClassificationHtmlParser {
    @Override
    protected CsiIndustrialClassification setIndex(CsiIndustrialClassification classification) {
        classification.setTtm(classification.getIndex());
        return classification;
    }
}
