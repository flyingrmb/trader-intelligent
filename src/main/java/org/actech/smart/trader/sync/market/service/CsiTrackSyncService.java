package org.actech.smart.trader.sync.market.service;

import org.actech.smart.trader.core.util.DateIterator;
import org.actech.smart.trader.registry.annotation.Registry;
import org.actech.smart.trader.registry.annotation.ServicePoint;
import org.actech.smart.trader.sync.market.parser.csi.CsiIndustrialClassificationDyrHtmlParser;
import org.actech.smart.trader.sync.market.parser.csi.CsiIndustrialClassificationLyrHtmlParser;
import org.actech.smart.trader.sync.market.parser.csi.CsiIndustrialClassificationPbHtmlParser;
import org.actech.smart.trader.sync.market.parser.csi.CsiIndustrialClassificationTtmHtmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * Created by paul on 2018/3/12.
 */
@Service
@Registry
public class CsiTrackSyncService extends CsindexHtmlStructure {
    @ServicePoint(code="csi1", name="同步CSI指数数据", example = "service/csi1 或者 service/csi1=2017-12-31")
    public String syncCurrentCsiData(String param) {
        DateIterator dateIterator = new DateIterator(param);
        String dateStr = dateIterator.get();

        Collection<UrlIndicator> urlIndicators = createUrlIndicators(dateStr);

        for (UrlIndicator urlIndicator : urlIndicators) {
            if (!containsData(urlIndicator)) continue;

            parse(urlIndicator);
        }

        return "SUCCESS";
    }

    @ServicePoint(code="csi2", async=true, name="同步CSI历史指数数据", example = "service/csi2")
    public String syncAllCsiData(String param) {
        DateIterator dateIterator = new DateIterator();
        String dateStr = dateIterator.get();

        int cntForNotFindingDate = 0;
        while (cntForNotFindingDate < tolerateNotFindingDate) {
            logger.info("更新CSI指数历史数据，日期：" + dateStr);
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

        return "SUCCESS";
    }

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        navigatorMap.put(Domain.LYR, new HtmlNavigator("zz1", applicationContext.getBean(CsiIndustrialClassificationLyrHtmlParser.class))); // 静态市盈率
        navigatorMap.put(Domain.TTM, new HtmlNavigator("zz2", applicationContext.getBean(CsiIndustrialClassificationTtmHtmlParser.class))); // 滚动市盈率
        navigatorMap.put(Domain.PB, new HtmlNavigator("zz3", applicationContext.getBean(CsiIndustrialClassificationPbHtmlParser.class))); // 市净率
        navigatorMap.put(Domain.DYR, new HtmlNavigator("zz4", applicationContext.getBean(CsiIndustrialClassificationDyrHtmlParser.class))); // 股息率
    }
}
