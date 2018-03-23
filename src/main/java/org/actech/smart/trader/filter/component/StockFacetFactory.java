package org.actech.smart.trader.filter.component;

import org.actech.smart.trader.filter.entity.StockFacet;

import java.util.List;

/**
 * Created by paul on 2018/3/18.
 */
public interface StockFacetFactory {
    List<StockFacet> createStockFacet(String type);
}
