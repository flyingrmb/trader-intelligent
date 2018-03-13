package org.actech.smart.trader.sync.parser;

import org.actech.smart.trader.sync.entity.BoardClassification;
import org.actech.smart.trader.sync.entity.Level;
import org.actech.smart.trader.sync.entity.Signature;
import org.actech.smart.trader.sync.repositories.TrackRepository;
import org.actech.smart.trader.sync.resolver.HtmlResolver;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.EntityUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by paul on 2018/3/12.
 */
@ConfigurationProperties("trader.sync")
public abstract class CatalogParser<T extends Signature> extends CacheableParser {
    private boolean enableStockSchema;
    public void setEnableStockSchema(boolean enableStockSchema) {
        this.enableStockSchema = enableStockSchema;
    }

    protected TrackRepository<T> repository;
    protected HtmlResolver<T> industrialResolver;

    protected HtmlParser stockClassificationParser;
    protected HtmlParser stockTrackParser;

    @Override
    protected boolean containsData(Document document) {
        if (document == null) return false;

        Elements elements = industrialResolver.navigatorToContents(document);
        return elements.size() != 0;
    }

    @Override
    protected void parse(Document document, String dateStr) {
        assert(document != null);

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
