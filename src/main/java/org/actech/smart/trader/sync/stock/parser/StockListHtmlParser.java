package org.actech.smart.trader.sync.stock.parser;

import org.actech.smart.trader.core.parser.CacheableParser;
import org.actech.smart.trader.sync.market.entity.StockClassification;
import org.actech.smart.trader.core.net.NetworkResourceCache;
import org.actech.smart.trader.sync.stock.entity.StockFundTrack;
import org.actech.smart.trader.sync.stock.repository.StockFundTrackRepository;
import org.actech.smart.trader.sync.stock.resolver.StockDetailHtmlResolver;
import org.actech.smart.trader.sync.stock.resolver.StockListHtmlResolver;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * Created by paul on 2018/3/15.
 */
@Service
public class StockListHtmlParser extends CacheableParser<Document> {
    @Autowired
    private StockListHtmlResolver resolver;

    @Autowired
    private StockFundTrackRepository repository;

    @Autowired
    private StockDetailHtmlResolver detailParser;

    @Autowired
    private NetworkResourceCache cache;

    @Override
    public boolean shouldParse(Document document) {
        Elements elements = resolver.navigatorToContents(document);
        return elements.size() > 0;
    }

    @Override
    public void parse(Document document, Object parameter) {
        Assert.notNull(document, "document should not null.");
        Assert.notNull(parameter, "parameter should not be null.");
        Assert.isTrue(parameter instanceof String, "parameter should be a string.");

        String dateStr = (String)parameter;

        Collection<StockClassification> classifications = resolver.resolve(document);
        classifications.forEach(it -> updateStockTrackPeValue(it, dateStr));
    }

    private void updateStockTrackPeValue(StockClassification classification, String dateStr) {
        if (classification == null) return ;

        String code = classification.getCode();
        String url = classification.getUrl();

        StockFundTrack stockFundTrack = repository.findByReleaseAndCode(dateStr, code);
        if (stockFundTrack == null) {
            stockFundTrack = new StockFundTrack();
            stockFundTrack.setCode(code);
            stockFundTrack.setRelease(dateStr);
        }

        updateStockFunTrack(stockFundTrack, url);
    }

    private void updateStockFunTrack(StockFundTrack stockFundTrack, String url) {
        Assert.notNull(stockFundTrack, "stockFunTrack should not be null.");
        Assert.notNull(url, "url should not be null.");

        Document document = cache.get(url, Document.class);
        Collection<StockFundTrack> collection = detailParser.resolve(document);
        if (collection == null || collection.size() == 0) return ;

        EntityUtils.copyProperties(collection.iterator().next(), stockFundTrack);
        repository.save(stockFundTrack);
    }

}
