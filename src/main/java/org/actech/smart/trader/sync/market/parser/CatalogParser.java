package org.actech.smart.trader.sync.market.parser;

import org.actech.smart.trader.core.parser.CacheableParser;
import org.actech.smart.trader.core.parser.HtmlParser;
import org.actech.smart.trader.sync.market.entity.BoardClassification;
import org.actech.smart.trader.core.trait.Level;
import org.actech.smart.trader.core.entity.Signature;
import org.actech.smart.trader.core.repository.TrackRepository;
import org.actech.smart.trader.core.resolver.HtmlResolver;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.EntityUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by paul on 2018/3/12.
 */
@ConfigurationProperties("trader.sync")
public abstract class CatalogParser<T extends Signature> extends CacheableParser<Document> {
    private boolean enableStockSchema;
    public void setEnableStockSchema(boolean enableStockSchema) {
        this.enableStockSchema = enableStockSchema;
    }

    protected TrackRepository<T> repository;
    protected HtmlResolver<T> industrialResolver;

    protected HtmlParser stockClassificationParser;
    protected HtmlParser stockTrackParser;

    @Override
    public boolean shouldParse(Document document) {
        if (document == null) return false;

        Elements elements = industrialResolver.navigatorToContents(document);
        return elements.size() != 0;
    }

    @Override
    public void parse(Document document, Object parameter) {
        Assert.notNull(document, "document should not be null.");
        Assert.notNull(parameter, "parameter should not be null.");
        Assert.isTrue(parameter instanceof String, "parameter should be a string.");

        String dateStr = (String)parameter;

        Collection<T> shouldSave = new HashSet<T>();

        Collection<T> contents = industrialResolver.resolve(document);
        for (T classification : contents) {
            if (classification == null) continue;

            classification.setRelease(dateStr);
            classification = setIndex(classification);

            if (shouldInvokeStockUrl(classification)) invokeStockUrl(classification);

            T oldClassification = repository.findByReleaseAndCode(dateStr, classification.getCode());
            if (classification.equals(oldClassification)) continue;

            classification = copyProperties(classification, oldClassification);
            shouldSave.add(classification);
        }

        repository.saveAll(shouldSave);
    }

    protected abstract T setIndex(T classification);

    private void invokeStockUrl(T classification) {
        if (stockClassificationParser != null && enableStockSchema)
            stockClassificationParser.parse(((BoardClassification)classification).getStockUrl(), classification.getRelease());

        if (stockTrackParser != null)
            stockTrackParser.parse(((BoardClassification)classification).getStockUrl(), classification.getRelease());
    }

    private boolean shouldInvokeStockUrl(T classification) {
        if (classification == null) return false;
        if (!(classification instanceof BoardClassification)) return false;
        if (!(classification instanceof Level)) return false;

        return ((Level)classification).isLevelOne();
    }

    private T copyProperties(T target, T source) {
        if (source == null) return target;

        EntityUtils.copyProperties(source, target);
        return target;
    }
}
