package org.actech.smart.trader.sync.market.service;

import org.actech.smart.trader.sync.market.parser.csrc.CsrcIndustrialClassificationDyrHtmlParser;
import org.actech.smart.trader.sync.market.parser.csrc.CsrcIndustrialClassificationLyrHtmlParser;
import org.actech.smart.trader.sync.market.parser.csrc.CsrcIndustrialClassificationPbHtmlParser;
import org.actech.smart.trader.sync.market.parser.csrc.CsrcIndustrialClassificationTtmHtmlParser;
import org.actech.smart.trader.sync.net.RemoteConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by paul on 2018/3/10.
 */
@Service
public class CsrcTrackSyncService extends RemoteConnection {
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
