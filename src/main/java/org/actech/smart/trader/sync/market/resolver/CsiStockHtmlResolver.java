package org.actech.smart.trader.sync.market.resolver;

import org.actech.smart.trader.sync.market.entity.StockClassification;
import org.actech.smart.trader.sync.stock.resolver.StockHtmlResolver;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by paul on 2018/3/12.
 */
@Component
public class CsiStockHtmlResolver extends StockHtmlResolver<StockClassification> {
    @Override
    public Collection<StockClassification> resolve(Document document) {
        Collection<StockClassification> result = new HashSet<StockClassification>();

        Elements elements = navigatorToContents(document);

        for (Element element : elements) {
            Elements tdList = element.getElementsByTag("td");
            assert (tdList.size() == 15);

            StockClassification classification = new StockClassification();

            classification.setCode(tdList.get(1).text().trim());
            classification.setName(tdList.get(2).text().trim());
            classification.setCsiLevelOneCode(tdList.get(3).text().trim());
            classification.setCsiLevelOneName(tdList.get(4).text().trim());
            classification.setCsiLevelTwoCode(tdList.get(5).text().trim());
            classification.setCsiLevelTwoName(tdList.get(6).text().trim());
            classification.setCsiLevelThreeCode(tdList.get(7).text().trim());
            classification.setCsiLevelThreeName(tdList.get(8).text().trim());
            classification.setCsiLevelFourCode(tdList.get(9).text().trim());
            classification.setCsiLevelFourName(tdList.get(10).text().trim());

            result.add(classification);
        }

        return result;
    }
}
