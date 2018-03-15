package org.actech.smart.trader.filter.service.impl;

import org.actech.smart.trader.filter.service.FinancialFilterService;
import org.actech.smart.trader.registry.annotation.Registry;
import org.actech.smart.trader.registry.annotation.ServicePoint;
import org.springframework.stereotype.Service;

/**
 * Created by paul on 2018/3/14.
 */
@Service
@Registry
public class FinancialFilterServiceImpl implements FinancialFilterService {
    @Override
    @ServicePoint(name="基于市盈率筛选股票", example = "参数：[catalog]_[lry]_[]_[]")
    public String filterUsingFinancialIndex(String param) {
        return null;
    }
}
