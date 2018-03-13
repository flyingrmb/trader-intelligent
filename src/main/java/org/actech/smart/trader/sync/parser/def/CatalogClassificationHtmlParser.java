package org.actech.smart.trader.sync.parser.def;

import org.actech.smart.trader.sync.entity.CatalogClassification;
import org.actech.smart.trader.sync.parser.CacheableHtmlParser;
import org.actech.smart.trader.sync.parser.HtmlParser;
import org.actech.smart.trader.sync.repositories.TrackRepository;
import org.actech.smart.trader.sync.resolver.HtmlResolver;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by paul on 2018/3/12.
 */
public abstract class CatalogClassificationHtmlParser<T extends CatalogClassification> extends CacheableHtmlParser {
    protected TrackRepository<T> repository;
    protected HtmlResolver<T> resolver;

    protected HtmlParser stockParser;

    @Override
    protected boolean containsData(Document document) {
        if (document == null) return false;

        Elements elements = resolver.navigatorToContents(document);
        return elements.size() != 0;
    }

    @Override
    protected void parse(Document document, String dateStr) {
        assert(document != null);

        Collection<T> shouldSave = new HashSet<T>();

        Collection<T> contents = resolver.resolve(document);
        for (T newObj : contents) {
            newObj.setRelease(dateStr);

            T oldObj = repository.findByReleaseAndCode(dateStr, newObj.getCode());
            newObj = merge(newObj, oldObj);
            if (newObj == null) continue;

            shouldSave.add(newObj);

            // 逻辑需要调整，即使行业数据无需更新，也要触发股票信息的更新
            if (newObj.isLevelOne() && stockParser != null) // 只更新大类下的数据，减少调用次数
                stockParser.parse(newObj.getStockUrl(), dateStr);
        }

        repository.saveAll(shouldSave);
    }

    protected abstract T merge(T newObj, T oldObj);

    protected T merge0(T newObj, T oldObj) {
        if (oldObj == null) return newObj;

        if (oldObj.getId() != null) newObj.setId(oldObj.getId());
        if (oldObj.getRelease() != null) newObj.setRelease(oldObj.getRelease());

        if (oldObj.getCode() != null) newObj.setCode(oldObj.getCode());
        if (oldObj.getName() != null) newObj.setName(oldObj.getName());

        if (oldObj.getLyr() != 0.00) newObj.setLyr(oldObj.getLyr());
        if (oldObj.getTtm() != 0.00) newObj.setTtm(oldObj.getTtm());
        if (oldObj.getPb() != 0.00) newObj.setPb(oldObj.getPb());
        if (oldObj.getDyr() != 0.00) newObj.setDyr(oldObj.getDyr());

        return newObj;
    }

    protected boolean isEqual(T newObj, T oldObj) {
        if (newObj == null || oldObj == null) return false;
        if (!(newObj.getRelease().equals(oldObj.getRelease()))) return false;
        if (!(newObj.getCode().equals(oldObj.getCode()))) return false;
        if (!(newObj.getName().equals(oldObj.getName()))) return false;

        return true;
    }
}
