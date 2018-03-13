package org.actech.smart.trader.sync.parser.def;

import org.actech.smart.trader.sync.entity.StockClassification;
import org.actech.smart.trader.sync.parser.CacheableHtmlParser;
import org.actech.smart.trader.sync.repositories.StockClassificationRepository;
import org.actech.smart.trader.sync.resolver.HtmlResolver;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by paul on 2018/3/12.
 */
public abstract class StockClassificationHtmlParser extends CacheableHtmlParser {
    @Autowired
    private StockClassificationRepository repository;

    protected HtmlResolver resolver;

    @Override
    protected void parse(Document document, String dateStr) {
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
