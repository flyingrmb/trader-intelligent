package org.actech.smart.trader.sync.stock.parser;

import org.actech.smart.trader.sync.market.entity.StockClassification;
import org.actech.smart.trader.sync.market.resolver.CsiStockHtmlResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by paul on 2018/3/12.
 */
@Component
public class CsiStockClassificationHtmlParser extends StockClassificationHtmlParser {
    @Autowired
    protected void setHtmlResolver(CsiStockHtmlResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    protected StockClassification merge(StockClassification newObj, StockClassification oldObj) {
        if (oldObj == null || newObj == null) return newObj;

        if (!newHasHigherVersion(newObj, oldObj)) return null;

        oldObj.setCode(newObj.getCode());
        oldObj.setName(newObj.getName());

        oldObj.setCsiLevelOneCode(newObj.getCsiLevelOneCode());
        oldObj.setCsiLevelOneName(newObj.getCsiLevelOneName());
        oldObj.setCsiLevelTwoCode(newObj.getCsiLevelTwoCode());
        oldObj.setCsiLevelTwoName(newObj.getCsiLevelTwoName());
        oldObj.setCsiLevelThreeCode(newObj.getCsiLevelThreeCode());
        oldObj.setCsiLevelThreeName(newObj.getCsiLevelThreeName());
        oldObj.setCsiLevelFourCode(newObj.getCsiLevelFourCode());
        oldObj.setCsiLevelFourName(newObj.getCsiLevelFourName());

        return oldObj;
    }

    private boolean newHasHigherVersion(StockClassification newObj, StockClassification oldObj) {
        if (newHasHigherVersion(newObj.getCode(), oldObj.getCode())) return true;
        if (newHasHigherVersion(newObj.getName(), oldObj.getName())) return true;
        if (newHasHigherVersion(newObj.getCsiLevelOneCode(), oldObj.getCsiLevelOneCode())) return true;
        if (newHasHigherVersion(newObj.getCsiLevelOneName(), oldObj.getCsiLevelOneName())) return true;
        if (newHasHigherVersion(newObj.getCsiLevelTwoCode(), oldObj.getCsiLevelTwoCode())) return true;
        if (newHasHigherVersion(newObj.getCsiLevelTwoName(), oldObj.getCsiLevelTwoName())) return true;
        if (newHasHigherVersion(newObj.getCsiLevelThreeCode(), oldObj.getCsiLevelThreeCode())) return true;
        if (newHasHigherVersion(newObj.getCsiLevelThreeName(), oldObj.getCsiLevelThreeName())) return true;
        if (newHasHigherVersion(newObj.getCsiLevelFourCode(), oldObj.getCsiLevelFourCode())) return true;
        if (newHasHigherVersion(newObj.getCsiLevelFourName(), oldObj.getCsiLevelFourName())) return true;

        return false;
    }
}
