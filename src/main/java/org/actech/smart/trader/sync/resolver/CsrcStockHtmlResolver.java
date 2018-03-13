package org.actech.smart.trader.sync.resolver;

import org.actech.smart.trader.sync.entity.StockClassification;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by paul on 2018/3/12.
 */
@Component
public class CsrcStockHtmlResolver extends StockHtmlResolver {
    @Override
    public Collection<StockClassification> resolve(Document document) {
        Collection<StockClassification> result = new HashSet<StockClassification>();

        Elements elements = navigatorToContents(document);

        for (Element element : elements) {
            Elements tdList = element.getElementsByTag("td");
            assert (tdList.size() == 11);

            StockClassification classification = new StockClassification();

            classification.setCode(tdList.get(1).text().trim());
            classification.setName(tdList.get(2).text().trim());
            classification.setCsrcLevelOneCode(tdList.get(3).text().trim());
            classification.setCsrcLevelOneName(tdList.get(4).text().trim());
            classification.setCsrcLevelTwoCode(tdList.get(5).text().trim());
            classification.setCsrcLevelTwoName(tdList.get(6).text().trim());

            result.add(classification);
        }

        return result;
    }
}
