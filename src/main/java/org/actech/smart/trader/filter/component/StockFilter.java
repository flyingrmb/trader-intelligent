package org.actech.smart.trader.filter.component;

import org.actech.smart.trader.filter.entity.Condition;
import org.actech.smart.trader.filter.entity.StockFacet;

import java.util.List;

/**
 * Created by paul on 2018/3/18.
 */
public interface StockFilter {
    List<StockFacet> filter(List<Condition> conditions);
}
