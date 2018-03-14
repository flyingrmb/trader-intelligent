package org.actech.smart.trader.sync.net;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.actech.smart.trader.core.util.DateIterator;
import org.actech.smart.trader.core.parser.HtmlParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Created by paul on 2018/3/11.
 */
public abstract class RemoteConnection {
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

    protected Collection<UrlIndicator> createUrlIndicators(String dateStr) {
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
        return parser.shouldParse(urlIndicator.getUrl());
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
