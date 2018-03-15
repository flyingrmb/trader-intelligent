package org.actech.smart.trader.sync.stock.resolver;

import org.actech.smart.trader.core.util.NumericUtils;
import org.actech.smart.trader.sync.stock.entity.StockFundTrack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by paul on 2018/3/15.
 */
@Component
public class StockDetailHtmlResolver extends StockHtmlResolver<StockFundTrack> {
    private final Log logger = LogFactory.getLog(StockDetailHtmlResolver.class);

    public Elements navigatorToContents(Document document) {
        if (document == null) return new Elements();

        Element element = document.getElementById("rtp2");
        if (element == null) {
            logger.info("未找到股票相信信息table标签!");
            return null;
        }

        return element.getElementsByTag("td");
    }

    @Override
    public Collection<StockFundTrack> resolve(Document document) {
        Collection<StockFundTrack> result = new HashSet<StockFundTrack>();
        Elements elements = navigatorToContents(document);
        if (elements == null) return result;
        Assert.isTrue(elements.size() > 17, "The size of elements is not correct.");

        StockFundTrack stockFundTrack = new StockFundTrack();

        stockFundTrack.setEps(getValue(elements.get(0).text(), 0.00)); // 每股收益(Earning Per Share)
        stockFundTrack.setPeg(getValue(elements.get(1).text(), Double.MAX_VALUE)); // 动态市盈率
        stockFundTrack.setNavps(getValue(elements.get(2).text(), Double.MAX_VALUE)); // 每股净资产(Net Asset Value Per Share)

        stockFundTrack.setBr(getValue(elements.get(4).text(), 0.0001, 0.00)); // 企业营收(Enterprise Revenue)
        stockFundTrack.setBryoy(getValue(elements.get(5).text(), Double.MIN_VALUE)); // 营收同比(Enterprise Revenue Year-On-Year)
        stockFundTrack.setNp(getValue(elements.get(6).text(), 0.0001, 0.00)); // 净利润(Net Profit)
        stockFundTrack.setNpyoy(getValue(elements.get(7).text(), Double.MIN_VALUE)); // 净利润同比(Net Profit Year-On-Year)
        stockFundTrack.setGir(getValue(elements.get(8).text(), Double.MIN_VALUE)); // 毛利率(Gross Interest Rate)
        stockFundTrack.setNir(getValue(elements.get(9).text(), Double.MIN_VALUE)); // 净利率(Net Interest Rate)
        stockFundTrack.setRoe(getValue(elements.get(10).text(), Double.MIN_VALUE)); // 净资产收益率(Rate of Return on Common Stockholders’ Equity)
        stockFundTrack.setDr(getValue(elements.get(11).text(), Double.MAX_VALUE)); // 负债率(Debt Ratio)
        stockFundTrack.setTcs(getValue(elements.get(12).text(), 0.0001, 0.00)); // 总股本(Total Capital Stock)
        stockFundTrack.setTmv(getValue(elements.get(13).text(), 0.0001, 0.00)); // 总市值(Total Market Value)
        stockFundTrack.setCsc(getValue(elements.get(14).text(), 0.0001, 0.00)); // 流通股本(Capital Stock of Circulation)
        stockFundTrack.setMvc(getValue(elements.get(15).text(), 0.0001, 0.00)); // 流通市值（Market Value of Circulation)
        stockFundTrack.setUdpps(getValue(elements.get(16).text(), 0.00)); // 每股未分配利润（UnDistributed Profit Per Share)

        result.add(stockFundTrack);

        return result;
    }

    private Double getValue(String text, Double def) {
        if (text == null) return def;
        String[] pair = text.split("：");
        if (pair.length < 2) return def;

        String numericStr = pair[1].trim();
        return NumericUtils.parseDouble(numericStr, def);
    }

    private Double getValue(String text, Double ratio, Double def) {
        if (text == null) return def;
        String[] pair = text.split("：");
        if (pair.length < 2) return def;

        String numericStr = pair[1].trim();
        return NumericUtils.parseDouble(numericStr, ratio, def);
    }

    public static void main(String[] args) {
        System.out.println(10000 * 0.0001);
    }
}
