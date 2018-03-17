package org.actech.smart.trader.sync.stock.parser;

import org.actech.smart.trader.core.entity.Signature;
import org.actech.smart.trader.sync.stock.entity.StockFundTrack;
import org.actech.smart.trader.core.parser.CacheableParser;
import org.actech.smart.trader.sync.stock.repository.StockFundTrackRepository;
import org.actech.smart.trader.sync.stock.resolver.CsrcStockTrackHtmlResolver;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by paul on 2018/3/13.
 */
@Component
public class StockTrackHtmlParser extends CacheableParser<Document> {
    @Autowired
    private StockFundTrackRepository repository;

    @Autowired
    private CsrcStockTrackHtmlResolver resolver;

    @Override
    public boolean shouldParse(Document document) {
        Elements elements = resolver.navigatorToContents(document);
        return elements.size() > 0;
    }

    @Override
    public void parse(Document document, Object parameter) {
        Assert.notNull(parameter, "parameter should not be null.");
        Assert.isTrue(parameter instanceof String, "parameter should be a string.");

        String dateStr = (String)parameter;

        Collection<StockFundTrack> stockFunTracks = resolver.resolve(document);

        Collection<StockFundTrack> shouldSave = new HashSet<StockFundTrack>();
        for (StockFundTrack stockFundTrack : stockFunTracks) {
            stockFundTrack.setRelease(dateStr);

            String code = stockFundTrack.getCode();
            if (code == null || parameter == null) continue;

            StockFundTrack oldObj = repository.findByReleaseAndCode(dateStr, code);
            if (((Signature)stockFundTrack).equals(oldObj)) continue;

            EntityUtils.copyProperties(oldObj, stockFundTrack);
            shouldSave.add(stockFundTrack);
        }

        repository.saveAll(shouldSave);
    }
}
