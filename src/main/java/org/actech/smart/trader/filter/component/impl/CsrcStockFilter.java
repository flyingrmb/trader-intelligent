package org.actech.smart.trader.filter.component.impl;

import org.actech.smart.trader.filter.component.StockFilter;
import org.actech.smart.trader.filter.entity.Condition;
import org.actech.smart.trader.filter.entity.StockFacet;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by paul on 2018/3/18.
 */
@Component
public class CsrcStockFilter implements StockFilter {
    @Override
    public List<StockFacet> filter(List<Condition> conditions) {
        return null;
    }
}
