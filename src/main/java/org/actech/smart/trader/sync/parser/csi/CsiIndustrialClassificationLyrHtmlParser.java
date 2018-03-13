package org.actech.smart.trader.sync.parser.csi;

import org.actech.smart.trader.sync.entity.CsiIndustrialClassification;
import org.springframework.beans.EntityUtils;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;

/**
 * Created by paul on 2018/3/12.
 */
@Component
public class CsiIndustrialClassificationLyrHtmlParser extends CsiIndustrialClassificationHtmlParser {
    @Override
    protected CsiIndustrialClassification setIndex(CsiIndustrialClassification classification) {
        classification.setLyr(classification.getIndex());
        return classification;
    }
}
