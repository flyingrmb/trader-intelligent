package org.actech.smart.trader.sync.market.resolver;

import org.actech.smart.trader.sync.market.entity.CsrcIndustrialClassification;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

import static org.actech.smart.trader.sync.market.entity.CsrcIndustrialClassification.DEF_INDEX;
import static org.actech.smart.trader.core.util.NumericUtils.*;

/**
 * Created by paul on 2018/3/12.
 */
@Component
public class CsrcIndustrialHtmlResolver extends CatalogHtmlResolver<CsrcIndustrialClassification> {
    @Override
    public Collection<CsrcIndustrialClassification> resolve(Document document) {
        Elements elements = navigatorToContents(document);
        Collection<CsrcIndustrialClassification> result = new HashSet<CsrcIndustrialClassification>();

        String categoryCode = "";
        for (Element element : elements) {
            Elements tdList = element.getElementsByTag("td");
            assert (tdList.size() == 9);

            CsrcIndustrialClassification classification = new CsrcIndustrialClassification();

            String code = tdList.get(0).text().trim();
            if (code.length() == 1) {
                categoryCode = code;
            } else {
                code = categoryCode + code;
            }

            classification.setCode(code); // 行业代码
            classification.setName(tdList.get(1).text().trim());

            classification.setIndex(parseDouble(tdList.get(2).text().trim()));
            classification.setStockUrl(tdList.get(3).getElementsByTag("a").get(0).attr("href"));

            result.add(classification);
        }

        return result;
    }
}
