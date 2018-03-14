package org.actech.smart.trader.sync.market.service;

import org.actech.smart.trader.sync.market.parser.csi.CsiIndustrialClassificationDyrHtmlParser;
import org.actech.smart.trader.sync.market.parser.csi.CsiIndustrialClassificationLyrHtmlParser;
import org.actech.smart.trader.sync.market.parser.csi.CsiIndustrialClassificationPbHtmlParser;
import org.actech.smart.trader.sync.market.parser.csi.CsiIndustrialClassificationTtmHtmlParser;
import org.actech.smart.trader.sync.net.RemoteConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by paul on 2018/3/12.
 */
@Service
public class CsiTrackSyncService extends RemoteConnection {
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
