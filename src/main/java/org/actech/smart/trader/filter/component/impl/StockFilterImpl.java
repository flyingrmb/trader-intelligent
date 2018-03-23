package org.actech.smart.trader.filter.component.impl;

import org.actech.smart.trader.filter.component.StockFilter;
import org.actech.smart.trader.filter.entity.Condition;
import org.actech.smart.trader.filter.entity.StockFacet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paul on 2018/3/18.
 */
@Component
public class StockFilterImpl implements StockFilter {
    @Autowired
    private StockFacetFactoryImpl stockFacetFactoryImpl;

    @Override
    public List<StockFacet> filter(String type, List<Condition> conditions) {
        List<StockFacet> result = new ArrayList<StockFacet>();

        stockFacetFactoryImpl.createStockFacet(type).forEach(it -> {
            if (it.filter(conditions)) result.add(it);
        });

        return result;
    }
}
