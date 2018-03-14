package org.actech.smart.trader.sync.market.resolver;

import org.actech.smart.trader.sync.market.entity.BoardClassification;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.actech.smart.trader.core.util.NumericUtils.parseDouble;
import static org.actech.smart.trader.core.entity.Signature.DEF_INDEX;

/**
 * Created by paul on 2018/3/12.
 */
@Component
public class BoardHtmlResolver extends CatalogHtmlResolver<BoardClassification> {
    private Map<String, String> boardMap = new HashMap<String, String>();

    @PostConstruct
    public void afterPropertiesSet() {
        boardMap.put("上海A股", "SHA");
        boardMap.put("深圳A股", "SZA");
        boardMap.put("沪深A股", "HSA");
        boardMap.put("深市主板", "SZM");
        boardMap.put("中小板", "ZXB");
        boardMap.put("创业板", "CYB");
    }

    @Override
    public Elements navigatorToContents(Document document) {
        Elements elements = document.getElementsByClass("companyBox");
        assert(elements.size() == 1);

        Element element = elements.get(0);

        elements = element.getElementsByTag("table");
        assert(elements.size() == 1);

        element = elements.get(0);

        elements = element.getElementsByTag("tbody");
        assert(elements.size() == 1);

        element = elements.get(0);
        return element.getElementsByTag("tr");
    }

    @Override
    public Collection<BoardClassification> resolve(Document document) {
        Elements elements = navigatorToContents(document);
        Collection<BoardClassification> result = new HashSet<BoardClassification>();

        String categoryCode = "";
        for (Element element : elements) {
            Elements tdList = element.getElementsByTag("td");
            assert (tdList.size() == 8);

            BoardClassification classification = new BoardClassification();

            String name = tdList.get(0).text().trim();

            classification.setCode(boardMap.get(name)); // 行业代码
            classification.setName(name);

            classification.setIndex(parseDouble(tdList.get(1).text().trim(), DEF_INDEX));
            result.add(classification);
        }

        return result;
    }

}
