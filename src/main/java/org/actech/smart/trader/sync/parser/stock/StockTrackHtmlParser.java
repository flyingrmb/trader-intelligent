package org.actech.smart.trader.sync.parser.stock;

import org.actech.smart.trader.sync.entity.Signature;
import org.actech.smart.trader.sync.entity.StockClassification;
import org.actech.smart.trader.sync.entity.StockFundTrack;
import org.actech.smart.trader.sync.parser.CacheableParser;
import org.actech.smart.trader.sync.repositories.StockFundTrackRepository;
import org.actech.smart.trader.sync.resolver.CsrcStockTrackHtmlResolver;
import org.actech.smart.trader.sync.resolver.HtmlResolver;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by paul on 2018/3/13.
 */
@Component
public class StockTrackHtmlParser extends CacheableParser {
    @Autowired
    private StockFundTrackRepository repository;

    @Autowired
    private CsrcStockTrackHtmlResolver resolver;

    @Override
    protected boolean containsData(Document document) {
        Elements elements = resolver.navigatorToContents(document);
        return elements.size() > 0;
    }

    @Override
    protected void parse(Document document, String dateStr) {
        Collection<StockFundTrack> stockFunTracks = resolver.resolve(document);

        Collection<StockFundTrack> shouldSave = new HashSet<StockFundTrack>();
        for (StockFundTrack stockFundTrack : stockFunTracks) {
            stockFundTrack.setRelease(dateStr);

            String code = stockFundTrack.getCode();
            if (code == null || dateStr == null) continue;

            StockFundTrack oldObj = repository.findByReleaseAndCode(dateStr, code);
            if (((Signature)stockFundTrack).equals(oldObj)) continue;

            shouldSave.add(stockFundTrack);
        }

        repository.saveAll(shouldSave);
    }
}
