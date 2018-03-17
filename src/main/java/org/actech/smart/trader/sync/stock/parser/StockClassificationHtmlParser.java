package org.actech.smart.trader.sync.stock.parser;

import org.actech.smart.trader.sync.market.entity.StockClassification;
import org.actech.smart.trader.core.parser.CacheableParser;
import org.actech.smart.trader.sync.market.repository.StockClassificationRepository;
import org.actech.smart.trader.core.resolver.HtmlResolver;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

/**
 * Created by paul on 2018/3/12.
 */
public abstract class StockClassificationHtmlParser extends CacheableParser<Document> {
    @Autowired
    private StockClassificationRepository repository;

    protected HtmlResolver resolver;

    @Override
    public boolean shouldParse(Document document) {
        Elements elements = resolver.navigatorToContents(document);
        return elements.size() > 0;
    }

    @Override
    public void parse(Document document, Object dateStr) {
        Collection<StockClassification> shouldSave = new HashSet<StockClassification>();

        Collection<StockClassification> classifications = resolver.resolve(document);
        for (StockClassification classification : classifications) {

            StockClassification persisted = null;
            Optional<StockClassification> optional = repository.findById(classification.getCode());
            if (optional.isPresent()) persisted = optional.get();

            classification = merge(classification, persisted);
            if (classification == null) continue;

            shouldSave.add(classification);
        }

        repository.saveAll(shouldSave);
    }

    protected abstract StockClassification merge(StockClassification newObj, StockClassification oldObj);

    protected boolean newHasHigherVersion(String newStr, String oldStr) {
        if (newStr == null) return false;
        return !newStr.equals(oldStr);
    }
}
