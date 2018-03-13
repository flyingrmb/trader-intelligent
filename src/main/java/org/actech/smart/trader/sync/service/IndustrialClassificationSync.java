package org.actech.smart.trader.sync.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.actech.smart.trader.core.common.DateIterator;
import org.actech.smart.trader.sync.parser.HtmlParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Created by paul on 2018/3/11.
 */
public abstract class IndustrialClassificationSync implements SyncService {
    protected final Log logger = LogFactory.getLog(getClass());
    protected final String baseUrl = "http://www.csindex.com.cn/zh-CN/downloads/industry-price-earnings-ratio";

    protected final int tolerateNotFindingDate = 10; // 连续无记录的天数超过该阈值，则停止遍历

    private Collection<Domain> domains = new ArrayList<Domain>(
            Arrays.asList(
                    Domain.LYR,
                    Domain.TTM,
                    Domain.PB,
                    Domain.DYR));

    protected final Map<Domain, HtmlNavigator> navigatorMap = new HashMap<Domain, HtmlNavigator>();

    @Override
    public void syncToday() {
        DateIterator dateIterator = new DateIterator();
        String dateStr = dateIterator.get();

        syncOneDay(dateStr);
    }

    @Override
    public void syncAllHis() {
        syncPeriodHis(new DateIterator().get());
    }

    @Override
    public void syncOneDay(String dateStr) {
        Collection<UrlIndicator> urlIndicators = createUrlIndicators(dateStr);

        for (UrlIndicator urlIndicator : urlIndicators) {
            if (!containsData(urlIndicator)) continue;

            parse(urlIndicator);
        }
    }

    @Override
    public void syncPeriodHis(String dateStr) {
        DateIterator dateIterator = new DateIterator(dateStr);
        int cntForNotFindingDate = 0;
        while (cntForNotFindingDate < tolerateNotFindingDate) {
            logger.info("更新证监会市场指数历史数据，日期：" + dateStr);
            Collection<UrlIndicator> urlIndicators = createUrlIndicators(dateStr);

            boolean findNothing = true;
            for (UrlIndicator urlIndicator : urlIndicators) {
                if (!containsData(urlIndicator)) continue;

                parse(urlIndicator);
                findNothing = false;
            }

            dateStr = dateIterator.yesterday().get();

            if (findNothing) {
                cntForNotFindingDate++;
                continue;
            }

            cntForNotFindingDate = 0; // reset the counter to 0.
        }
    }

    public void refreshAll() {
        logger.info("证监会股票市场指数历史数据更新开始");
        DateIterator dateIterator = new DateIterator();
        String dateStr = dateIterator.get();
        int cntForNotFindingDate = 0;
        while (cntForNotFindingDate < tolerateNotFindingDate) {
            logger.info("更新证监会市场指数历史数据，日期：" + dateStr);
            Collection<UrlIndicator> urlIndicators = createUrlIndicators(dateStr);

            boolean findNothing = true;
            for (UrlIndicator urlIndicator : urlIndicators) {
                if (!containsData(urlIndicator)) continue;

                parse(urlIndicator);
                findNothing = false;
            }

            dateStr = dateIterator.yesterday().get();

            if (findNothing) {
                cntForNotFindingDate++;
                continue;
            }

            cntForNotFindingDate = 0; // reset the counter to 0.
        }

        logger.info("股票市场指数历史数据更新完成");
    }

    private Collection<UrlIndicator> createUrlIndicators(String dateStr) {
        Collection<UrlIndicator> urlIndicators = new ArrayList<UrlIndicator>();

        for (Domain domain : domains) {
            String urlParameters = createUrlParameters(domain, dateStr);
            String url = baseUrl + urlParameters;
            urlIndicators.add(new UrlIndicator(domain, dateStr, url));
        }

        return urlIndicators;
    }

    protected String createUrlParameters(Domain domain, String dateStr) {
        return "?type=" + navigatorMap.get(domain).getType() + "&date=" + dateStr;
    }

    protected boolean containsData(UrlIndicator urlIndicator) {
        HtmlParser parser = navigatorMap.get(urlIndicator.getDomain()).getParser();
        return parser.containsData(urlIndicator.getUrl());
    }

    protected void parse(UrlIndicator urlIndicator) {
        HtmlParser parser = navigatorMap.get(urlIndicator.getDomain()).getParser();
        parser.parse(urlIndicator.getUrl(), urlIndicator.getDateStr());
    }

    protected enum Domain {
        LYR, // 静态市盈率
        TTM, // 滚动市盈率
        PB, // 市净率
        DYR // 股息率
    }

    @Data
    @AllArgsConstructor
    protected class UrlIndicator {
        private Domain domain;
        private String dateStr;
        private String url;
    }

    @AllArgsConstructor
    @Data
    protected class HtmlNavigator {
        private String type;
        private HtmlParser parser;
    }
}
