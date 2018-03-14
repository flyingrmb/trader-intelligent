package org.actech.smart.trader.sync.market.service;

import org.actech.smart.trader.core.util.DateIterator;
import org.actech.smart.trader.registry.annotation.Registry;
import org.actech.smart.trader.registry.annotation.ServicePoint;
import org.actech.smart.trader.sync.market.parser.board.BoardClassificationDyrHtmlParser;
import org.actech.smart.trader.sync.market.parser.board.BoardClassificationPbHtmlParser;
import org.actech.smart.trader.sync.market.parser.board.BoardClassificationTtmHtmlParser;
import org.actech.smart.trader.sync.market.parser.board.BoardClssificationLyrHtmlParser;
import org.actech.smart.trader.sync.net.RemoteConnection;
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
public class BoardTrackSyncService extends RemoteConnection {
    @ServicePoint(code="B1", name="同步最新主板数据", example = {
            "service/B1",
            "service/B1:2018-03-17"
    })
    public void syncCurrentBoardData(String param) {
        DateIterator dateIterator = new DateIterator();
        String dateStr = dateIterator.get();

        Collection<UrlIndicator> urlIndicators = createUrlIndicators(dateStr);

        for (UrlIndicator urlIndicator : urlIndicators) {
            if (!containsData(urlIndicator)) continue;

            parse(urlIndicator);
        }
    }

    @ServicePoint(code="B2", name="同步主板所有历史数据", example = {
            "service/B2",
            "service/B2:2018-03-17"
    })
    public void syncAllBoardData(String param) {
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
    }

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        navigatorMap.put(Domain.LYR, new HtmlNavigator("zy1", applicationContext.getBean(BoardClssificationLyrHtmlParser.class))); // 静态市盈率
        navigatorMap.put(Domain.TTM, new HtmlNavigator("zy2", applicationContext.getBean(BoardClassificationTtmHtmlParser.class))); // 滚动市盈率
        navigatorMap.put(Domain.PB, new HtmlNavigator("zy3", applicationContext.getBean(BoardClassificationPbHtmlParser.class))); // 市净率
        navigatorMap.put(Domain.DYR, new HtmlNavigator("zy4", applicationContext.getBean(BoardClassificationDyrHtmlParser.class))); // 股息率
    }
}
