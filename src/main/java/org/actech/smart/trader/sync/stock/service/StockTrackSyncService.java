package org.actech.smart.trader.sync.stock.service;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.actech.smart.trader.core.net.NetworkResourceCache;
import org.actech.smart.trader.core.util.DateIterator;
import org.actech.smart.trader.core.util.SeasonIterator;
import org.actech.smart.trader.registry.annotation.Registry;
import org.actech.smart.trader.registry.annotation.ServicePoint;
import org.actech.smart.trader.sync.market.entity.StockClassification;
import org.actech.smart.trader.sync.market.repository.StockClassificationRepository;
import org.actech.smart.trader.sync.stock.parser.StockListHtmlParser;
import org.actech.smart.trader.sync.stock.parser.StockTraderHtmlParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.HTML;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by paul on 2018/3/14.
 */
@Service
@Registry
public class StockTrackSyncService {
    private final Log logger = LogFactory.getLog(getClass());

    private static final String DETAIL_URL = "http://quote.eastmoney.com/stocklist.html";
    private static final String TRADER_URL = "http://quotes.money.163.com/trade/lsjysj_";

    @Autowired
    private StockListHtmlParser detailParser;

    @Autowired
    private StockTraderHtmlParser traderParser;

    @Autowired
    private StockClassificationRepository repository;

    @Autowired
    private NetworkResourceCache networkResourceCache;

    @ServicePoint(code="stock1", name="同步股票当日最新基本面信息", example="service/stock1")
    public void syncStockDetailInfo(String param) {
        DateIterator iterator = new DateIterator();
        detailParser.parse(DETAIL_URL, iterator.get());
    }

    @ServicePoint(code="stock3", async=true, name="同步股票历史交易信息", example =
            {"service/stock3",
            "service/stock3=2017-12-31"})
    public void syncStockTraderInfo(String param) {
        logger.info("开始处理股票历史交易数据：" + param);
        SeasonIterator iterator = new SeasonIterator(param);

        Iterable<StockClassification> classifications = repository.findAll();

        boolean done = false;
        Set<String> finished = new HashSet<String>();
        while (!done) {
            done = true;

            for (StockClassification classification : classifications) {
                if (finished.contains(classification.getCode())) continue;

                boolean finish = syncStockTraderInfo(classification, iterator);

                if (finish) finished.add(classification.getCode());
                if (!finish) done = false;
            }

            iterator = iterator.preSeason();
        }

        logger.info("完成股票历史交易数据处理。");
    }

    private boolean syncStockTraderInfo(StockClassification classification, SeasonIterator iterator) {
        final String url = TRADER_URL + classification.getCode() + ".html?";

        HtmlPage htmlPage = networkResourceCache.get(generateUrl(url, iterator), HtmlPage.class);
        if (!traderParser.shouldParse(htmlPage)) {
            return true;
        }

        traderParser.parse(htmlPage, classification);
        return false;
    }

    private String generateUrl(String url, SeasonIterator iterator) {
        return url + "year=" + iterator.year() + "&season=" + iterator.season();
    }
}
