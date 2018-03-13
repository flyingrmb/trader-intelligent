package org.actech.smart.trader.sync;

import org.actech.smart.trader.sync.entity.StockClassification;
import org.actech.smart.trader.sync.repositories.StockClassificationRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by paul on 2018/3/10.
 */
@Component
public class StockSummarizeHtmlParser {
    private static final Log logger = LogFactory.getLog(StockSummarizeHtmlParser.class);

    @Autowired
    private StockClassificationRepository stockClassificationRepository;

    public void parse(String url) throws IOException {
        logger.info("开始追加股票详细信息URL……");
        Document document = Jsoup.connect(url).get();
        parse(document);
    }

    public void parse(File file, String charset) throws IOException {
        Document document = Jsoup.parse(file, charset);
        parse(document);
    }

    private void parse(Document document) {
        Elements elements = document.getElementsByClass("quotebody");
        assert(elements.size() == 1);

        Element element = elements.get(0);

        elements = element.getElementsByTag("ul");
        assert(elements.size() == 2);

        for (Element ul : elements)
            parse(ul.getElementsByTag("li"));
    }

    private void parse(Elements liList) {
        for (Element li : liList) {
            Element anchor = li.getElementsByTag("a").get(0);
            String detailUrl = anchor.attr("href");
            String text = anchor.text();

            String code = getCode(text);
            Optional<StockClassification> company = stockClassificationRepository.findById(code);

            if (!company.isPresent()) {
                logger.info("The syncmarket[" + code + "] is absent!");
                continue;
            }

            StockClassification entity = company.get();
            // entity.setDetailUrl(detailUrl);

            stockClassificationRepository.save(entity);
        }
    }

    private String getCode(String text) {
        int index = text.indexOf("(");
        assert(index != -1);

        text = text.substring(index + 1);
        index = text.indexOf(")");
        assert(index != -1);

        return text.substring(0, index);
    }
}
