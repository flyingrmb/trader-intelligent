package org.actech.smart.trader.sync.stock.resolver;

import org.actech.smart.trader.sync.market.entity.StockClassification;
import org.actech.smart.trader.sync.market.repository.StockClassificationRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.TreeSet;

/**
 * Created by paul on 2018/3/15.
 */
@Component
public class StockListHtmlResolver extends StockHtmlResolver<StockClassification> {
    private final Log logger = LogFactory.getLog(StockListHtmlResolver.class);

    @Autowired
    private StockClassificationRepository repository;

    public Elements navigatorToContents(Document document) {
        if (document == null) return new Elements();

        Elements elements = document.getElementsByClass("quotebody");
        Assert.notEmpty(elements, "未找到数据节点");

        Element element = elements.get(0);

        return element.getElementsByTag("li");
    }

    @Override
    public Collection<StockClassification> resolve(Document document) {
        Elements elements = navigatorToContents(document);
        Collection<StockClassification> result = new ArrayList<StockClassification>();

        for (Element element : elements) {
            StockClassification classification = resolve(element);
            if (classification != null)
                result.add(classification);
        }
        return result;
    }

    private StockClassification resolve(Element element) {
        String code = getCode(element.text());
        StockClassification classification = queryClassification(code);

        if (classification == null) {
            logger.info("股票" + element.text() + "未入库，不处理该股票基本面信息.");
            return null;
        }

        String url = element.getElementsByTag("a").get(0).attr("href");
        Assert.notNull(url, "url should not be null");
        classification.setUrl(url);

        return classification;
    }

    private String getCode(String text) {
        if (text == null) return null;

        int index = text.indexOf("(");
        if (index != -1)
            text = text.substring(index+1, text.length());

        index = text.indexOf(")");
        if (index != -1)
            text = text.substring(0, index);

        return text;
    }

    private StockClassification queryClassification(String code) {
        if (code == null || code.length() == 0) return null;

        Optional<StockClassification> optional = repository.findById(code);
        if (optional.isPresent()) return optional.get();

        return null;
    }
}
