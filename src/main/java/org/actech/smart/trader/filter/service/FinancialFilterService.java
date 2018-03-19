package org.actech.smart.trader.filter.service;

import java.io.IOException;

/**
 * Created by paul on 2018/3/14.
 */
public interface FinancialFilterService {
    String filterUsingFinancialIndex(String param) throws IOException;
}
