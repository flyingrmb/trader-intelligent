package org.actech.smart.trader.sync.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.actech.smart.trader.sync.parser.HtmlParser;
import org.actech.smart.trader.sync.parser.csi.CsiIndustrialClassificationDyrHtmlParser;
import org.actech.smart.trader.sync.parser.csi.CsiIndustrialClassificationLyrHtmlParser;
import org.actech.smart.trader.sync.parser.csi.CsiIndustrialClassificationPbHtmlParser;
import org.actech.smart.trader.sync.parser.csi.CsiIndustrialClassificationTtmHtmlParser;
import org.actech.smart.trader.sync.parser.csrc.CsrcIndustrialClassificationDyrHtmlParser;
import org.actech.smart.trader.sync.parser.csrc.CsrcIndustrialClassificationLyrHtmlParser;
import org.actech.smart.trader.sync.parser.csrc.CsrcIndustrialClassificationPbHtmlParser;
import org.actech.smart.trader.sync.parser.csrc.CsrcIndustrialClassificationTtmHtmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by paul on 2018/3/12.
 */
@Service
public class CsiIndustrialClassificationSync extends IndustrialClassificationSync {
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
