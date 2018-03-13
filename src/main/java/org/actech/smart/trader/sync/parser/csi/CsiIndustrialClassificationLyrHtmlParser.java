package org.actech.smart.trader.sync.parser.csi;

import org.actech.smart.trader.sync.entity.CsiIndustrialClassification;
import org.springframework.stereotype.Component;

/**
 * Created by paul on 2018/3/12.
 */
@Component
public class CsiIndustrialClassificationLyrHtmlParser extends CsiIndustrialClassificationHtmlParser {
    @Override
    protected CsiIndustrialClassification merge(CsiIndustrialClassification newObj, CsiIndustrialClassification oldObj) {
        if (isEqual(newObj, oldObj)) return null;
        newObj = merge0(newObj, oldObj);

        newObj.setLyr(newObj.getIndex());
        return newObj;
    }

    @Override
    protected boolean isEqual(CsiIndustrialClassification newObj, CsiIndustrialClassification oldObj) {
        boolean equal = super.isEqual(newObj, oldObj);
        if (!equal) return false;

        return newObj.getIndex() == oldObj.getLyr();
    }
}
