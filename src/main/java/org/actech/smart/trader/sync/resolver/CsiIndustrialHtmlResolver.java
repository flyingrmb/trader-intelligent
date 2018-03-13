package org.actech.smart.trader.sync.resolver;

import org.actech.smart.trader.sync.entity.CsiIndustrialClassification;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

import static org.actech.smart.trader.core.common.NumericParser.parseDouble;
import static org.actech.smart.trader.sync.entity.CatalogClassification.DEF_INDEX;

/**
 * Created by paul on 2018/3/12.
 */
@Component
public class CsiIndustrialHtmlResolver extends CatalogHtmlResolver<CsiIndustrialClassification> {
    @Override
    public Collection<CsiIndustrialClassification> resolve(Document document) {
        Elements elements = navigatorToContents(document);
        Collection<CsiIndustrialClassification> result = new HashSet<CsiIndustrialClassification>();

        String categoryCode = "";
        for (Element element : elements) {
            Elements tdList = element.getElementsByTag("td");
            assert (tdList.size() == 9);

            CsiIndustrialClassification classification = new CsiIndustrialClassification();

            String code = tdList.get(0).text().trim();

            classification.setCode(code); // 行业代码
            classification.setName(tdList.get(1).text().trim());

            classification.setIndex(parseDouble(tdList.get(2).text().trim(), DEF_INDEX));
            classification.setStockUrl(tdList.get(3).getElementsByTag("a").get(0).attr("href"));

            result.add(classification);
        }

        return result;
    }
}
