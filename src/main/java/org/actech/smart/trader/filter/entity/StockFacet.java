package org.actech.smart.trader.filter.entity;

import lombok.Data;
import org.actech.smart.trader.core.entity.Signature;
import org.actech.smart.trader.sync.market.entity.StockClassification;
import org.actech.smart.trader.sync.stock.entity.StockFundTrack;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by paul on 2018/3/18.
 */
@Data
public class StockFacet {
    private StockClassification stockClassification;
    private Signature industryClassification;
    private StockFundTrack stockFundTrack;

    public boolean filter(List<Condition> conditions) {
        if (conditions == null) return false;
        if (conditions.size() == 0) return false;

        for (Condition it : conditions) {
            Field field = ReflectionUtils.findField(stockFundTrack.getClass(), it.getIndex());
            ReflectionUtils.makeAccessible(field);
            Double stockValue = (Double)ReflectionUtils.getField(field, stockFundTrack);

            field = ReflectionUtils.findField(industryClassification.getClass(), it.getIndex());
            ReflectionUtils.makeAccessible(field);
            Double industryValule = (Double)ReflectionUtils.getField(field, industryClassification);

            if (stockValue == null) return false;
            if (industryValule == null) return false;

            if (it.getRelative() > 0 && stockValue/industryValule > it.getRelative()) {
                return false;
            }

            if (it.getRelative() < 0 && stockValue/industryValule < -it.getRelative()) {
                return false;
            }
        }

        return true;
    }

    public String serialize() {
        String template = "【分类】：[{0}, {1}, {2}, {3}]，【基本面】：[市盈率：({4},{5})/{6}, 股息率：{7}/{8}, 滚动市盈率：{9}/{10} ,市净率：{11}/{12}]";
        return MessageFormat.format(template, stockClassification.getName(),
                stockClassification.getCode(),
                industryClassification.getName(),
                industryClassification.getCode(),
                format(stockFundTrack.getLyr()),
                format(stockFundTrack.getPeg()),
                format(industryClassification.getLyr()),
                format(stockFundTrack.getDyr()),
                format(industryClassification.getDyr()),
                format(stockFundTrack.getTtm()),
                format(industryClassification.getTtm()),
                format(stockFundTrack.getPb()),
                format(industryClassification.getPb()));
    }

    private String format(Double value) {
        if (value == null) return "-";
        if (value == Double.MAX_VALUE) return "-";
        if (value == Double.MIN_VALUE) return "-";
        return value.toString();
    }
}
