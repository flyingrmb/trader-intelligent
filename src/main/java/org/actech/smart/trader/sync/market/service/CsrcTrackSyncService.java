package org.actech.smart.trader.sync.market.service;

import org.actech.smart.trader.core.util.DateIterator;
import org.actech.smart.trader.registry.annotation.Registry;
import org.actech.smart.trader.registry.annotation.ServicePoint;
import org.actech.smart.trader.sync.market.parser.csrc.CsrcIndustrialClassificationDyrHtmlParser;
import org.actech.smart.trader.sync.market.parser.csrc.CsrcIndustrialClassificationLyrHtmlParser;
import org.actech.smart.trader.sync.market.parser.csrc.CsrcIndustrialClassificationPbHtmlParser;
import org.actech.smart.trader.sync.market.parser.csrc.CsrcIndustrialClassificationTtmHtmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * Created by paul on 2018/3/10.
 */
@Service
@Registry
public class CsrcTrackSyncService extends CsindexHtmlStructure {
    @ServicePoint(code="csrc1", name="同步CSRC指数数据", example = "service/csrc1 或者 service/csrc1=2017-12-31")
    public void syncCsrcCurrentData(String param) {
        DateIterator dateIterator = new DateIterator(param);
        String dateStr = dateIterator.get();

        Collection<UrlIndicator> urlIndicators = createUrlIndicators(dateStr);

        for (UrlIndicator urlIndicator : urlIndicators) {
            if (!containsData(urlIndicator)) continue;

            parse(urlIndicator);
        }
    }

    @ServicePoint(code="csrc2", async=true, name="同步CSRC历史指数数据", example = "service/scrc2")
    public void syncAllCsrcData(String param) {
        DateIterator dateIterator = new DateIterator();
        String dateStr = dateIterator.get();

        int cntForNotFindingDate = 0;
        while (cntForNotFindingDate < tolerateNotFindingDate) {
            logger.info("更新CSRC指数历史数据，日期：" + dateStr);
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

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        navigatorMap.put(Domain.LYR, new HtmlNavigator("zjh1", applicationContext.getBean(CsrcIndustrialClassificationLyrHtmlParser.class))); // 静态市盈率
        navigatorMap.put(Domain.TTM, new HtmlNavigator("zjh2", applicationContext.getBean(CsrcIndustrialClassificationTtmHtmlParser.class))); // 滚动市盈率
        navigatorMap.put(Domain.PB, new HtmlNavigator("zjh3", applicationContext.getBean(CsrcIndustrialClassificationPbHtmlParser.class))); // 市净率
        navigatorMap.put(Domain.DYR, new HtmlNavigator("zjh4", applicationContext.getBean(CsrcIndustrialClassificationDyrHtmlParser.class))); // 股息率
    }
}
