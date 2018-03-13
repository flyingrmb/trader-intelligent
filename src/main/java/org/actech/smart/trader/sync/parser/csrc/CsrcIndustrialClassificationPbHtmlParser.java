package org.actech.smart.trader.sync.parser.csrc;

import org.actech.smart.trader.sync.entity.CsrcIndustrialClassification;
import org.springframework.stereotype.Component;

/**
 * Created by paul on 2018/3/11.
 */
@Component
public class CsrcIndustrialClassificationPbHtmlParser extends CsrcIndustrialClassificationHtmlParser {
    @Override
    protected CsrcIndustrialClassification merge(CsrcIndustrialClassification newObj, CsrcIndustrialClassification oldObj) {
        if (isEqual(newObj, oldObj)) return null;
        newObj = merge0(newObj, oldObj);

        newObj.setPb(newObj.getIndex());
        return newObj;
    }

    @Override
    protected boolean isEqual(CsrcIndustrialClassification newObj, CsrcIndustrialClassification oldObj) {
        boolean equal = super.isEqual(newObj, oldObj);
        if (!equal) return false;

        return newObj.getIndex() == oldObj.getPb();
    }
}
