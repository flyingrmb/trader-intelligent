package org.actech.smart.trader.filter.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.actech.smart.trader.core.util.NumericUtils;
import org.actech.smart.trader.filter.component.impl.CsiStockFilter;
import org.actech.smart.trader.filter.component.impl.CsrcStockFilter;
import org.actech.smart.trader.filter.entity.Condition;
import org.actech.smart.trader.filter.entity.StockFacet;
import org.actech.smart.trader.filter.service.FinancialFilterService;
import org.actech.smart.trader.registry.annotation.Registry;
import org.actech.smart.trader.registry.annotation.ServicePoint;
import org.actech.smart.trader.sync.market.entity.StockClassification;
import org.actech.smart.trader.sync.market.repository.StockClassificationRepository;
import org.actech.smart.trader.sync.stock.entity.StockFundTrack;
import org.actech.smart.trader.sync.stock.repository.StockFundTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by paul on 2018/3/14.
 */
@Service
@Registry
public class FinancialFilterServiceImpl implements FinancialFilterService {
    @Autowired
    private CsiStockFilter csiStockFilter;
    @Autowired
    private CsrcStockFilter csrcStockFilter;

    @Override
    @ServicePoint(code="filter0", name="根据市场平均值过滤股票",
            example = "格式:service/filter0=[category]+[参数]\r\n" +
                    "\t\t\t category: csi/csrc\r\n" +
                    "\t\t\t 参数格式：type_value\r\n" +
                    "\t\t\t type: lyr(最新市盈率)/ttm(滚动市盈率)/pb(市净率)/dyr(股息率)\r\n" +
                    "\t\t\t value: 个股数值和行业均值差值的最大值")
    public String filterUsingFinancialIndex(String param) throws IOException {
        Assert.hasText(param, "input parameter should not be null.");

        String[] paramSlice = param.split("\\+");
        Assert.isTrue(paramSlice.length > 1, "Parameter format is not valid.");
        String type = paramSlice[0].trim();
        Assert.isTrue("csi".equals(type) || "csrc".equals(type), "Type should be csi or csrc.");

        List<Condition> filters = createFilters(Arrays.copyOfRange(paramSlice, 1, paramSlice.length));

        List<StockFacet> result = new ArrayList<StockFacet>();

        if ("csi".equals(type))
            result = csiStockFilter.filter(filters);

        if ("csrc".equals(type))
            result = csrcStockFilter.filter(filters);

        return serialize(result);
    }

    private List<Condition> createFilters(String[] paramSlice) {
        Assert.notNull(paramSlice, "input value should not be null");
        Assert.isTrue(paramSlice.length > 0, "input value should not be null");

        List<Condition> conditions = new ArrayList<Condition>();
        for (String slice : paramSlice) {
            String[] items = slice.split("_");
            Assert.isTrue(items.length == 2, "input parameter is not valid.");
            Condition condition = new Condition(items[0], NumericUtils.parseDouble(items[1]));

            conditions.add(condition);
        }

        return conditions;
    }


    private String serialize(List<StockFacet> stockFacets) throws IOException {
        /*ObjectMapper mapper = new ObjectMapper();

        StringWriter sw = new StringWriter();
        mapper.writerWithDefaultPrettyPrinter().writeValue(sw ,stockFacets);

        return sw.toString();*/
        StringBuilder sb = new StringBuilder();
        stockFacets.forEach(it -> sb.append(it.serialize() + "\r\n"));

        return sb.toString();
    }

}
