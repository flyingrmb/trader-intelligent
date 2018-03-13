package org.actech.smart.trader.sync.parser.csrc;

import org.actech.smart.trader.sync.entity.StockClassification;
import org.actech.smart.trader.sync.parser.def.StockClassificationHtmlParser;
import org.actech.smart.trader.sync.resolver.CsrcStockHtmlResolver;
import org.actech.smart.trader.sync.resolver.StockHtmlResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by paul on 2018/3/12.
 */
@Component
public class CsrcStockClassificationHtmlParser extends StockClassificationHtmlParser {
    @Autowired
    protected void setHtmlResolver(CsrcStockHtmlResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    protected StockClassification merge(StockClassification newObj, StockClassification oldObj) {
        if (oldObj == null || newObj == null) return newObj;

        if (!newHasHigherVersion(newObj, oldObj)) return null;

        oldObj.setCode(newObj.getCode());
        oldObj.setName(newObj.getName());
        oldObj.setCsrcLevelOneCode(newObj.getCsrcLevelOneCode());
        oldObj.setCsrcLevelOneName(newObj.getCsrcLevelOneName());
        oldObj.setCsrcLevelTwoCode(newObj.getCsrcLevelTwoCode());
        oldObj.setCsrcLevelTwoName(newObj.getCsrcLevelTwoName());

        return oldObj;
    }

    private boolean newHasHigherVersion(StockClassification newObj, StockClassification oldObj) {
        if (newHasHigherVersion(newObj.getCode(), oldObj.getCode())) return true;
        if (newHasHigherVersion(newObj.getName(), oldObj.getName())) return true;
        if (newHasHigherVersion(newObj.getCsrcLevelOneCode(), oldObj.getCsrcLevelOneCode())) return true;
        if (newHasHigherVersion(newObj.getCsrcLevelOneName(), oldObj.getCsrcLevelOneName())) return true;
        if (newHasHigherVersion(newObj.getCsrcLevelTwoCode(), oldObj.getCsrcLevelTwoCode())) return true;
        if (newHasHigherVersion(newObj.getCsrcLevelTwoName(), oldObj.getCsrcLevelTwoName())) return true;

        return false;
    }
}
