package org.actech.smart.trader.sync.stock.service;

import org.actech.smart.trader.core.net.DocumentCache;
import org.actech.smart.trader.core.util.DateIterator;
import org.actech.smart.trader.core.util.SeasonIterator;
import org.actech.smart.trader.registry.annotation.Registry;
import org.actech.smart.trader.registry.annotation.ServicePoint;
import org.actech.smart.trader.sync.market.entity.StockClassification;
import org.actech.smart.trader.sync.market.repository.StockClassificationRepository;
import org.actech.smart.trader.sync.stock.parser.StockListHtmlParser;
import org.actech.smart.trader.sync.stock.parser.StockTraderHtmlParser;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SealedObject;

/**
 * Created by paul on 2018/3/14.
 */
@Service
@Registry
public class StockTrackSyncService {
    private static final String DETAIL_URL = "http://quote.eastmoney.com/stocklist.html";
    private static final String TRADER_URL = "http://quotes.money.163.com/trade/lsjysj_";

    @Autowired
    private StockListHtmlParser detailParser;

    @Autowired
    private StockTraderHtmlParser traderParser;

    @Autowired
    private StockClassificationRepository repository;

    @Autowired
    private DocumentCache documentCache;

    @ServicePoint(code="stock1", name="同步股票当日最新基本面信息", example="service/stock1")
    public void syncStockDetailInfo(String param) {
        DateIterator iterator = new DateIterator();
        detailParser.parse(DETAIL_URL, iterator.get());
    }

    @ServicePoint(code="stock3", name="同步股票历史交易信息", example = "service/stock3")
    public void syncStockTraderInfo(String param) {
        repository.findAll().forEach(it -> syncStockTraderInfo(it, param));
    }

    private void syncStockTraderInfo(StockClassification classification, String param) {
        SeasonIterator iterator = new SeasonIterator();
        final String url = TRADER_URL + classification.getCode() + ".html?";

        Document document = documentCache.get(generateUrl(url, iterator));
        while (traderParser.shouldParse(document)) {
            traderParser.parse(document, param);

            iterator = iterator.preSeason();
            document = documentCache.get(generateUrl(url, iterator));
        }
    }

    private String generateUrl(String url, SeasonIterator iterator) {
        return url + "year=" + iterator.year() + "&season=" + iterator.season();
    }
}
