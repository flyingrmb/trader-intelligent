package org.actech.smart.trader.sync;

import org.actech.smart.trader.sync.entity.CsrcIndustrialClassification;
import org.actech.smart.trader.sync.entity.StockClassification;
import org.actech.smart.trader.sync.repositories.StockClassificationRepository;
import org.actech.smart.trader.sync.repositories.CsrcIndustrialClassificationRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;


import static org.actech.smart.trader.core.common.NumericUtils.*;

/**
 * Created by paul on 2018/3/10.
 */
@Component
public class CompanyHtmlParser {
    @Autowired
    private StockClassificationRepository stockClassificationRepository;

    @Autowired
    private CsrcIndustrialClassificationRepository csrcIndustrialClassificationRepository;

    public void updateCompanies() throws IOException {
        Iterable<CsrcIndustrialClassification> industries = csrcIndustrialClassificationRepository.findAll();
        for(org.actech.smart.trader.sync.entity.CsrcIndustrialClassification CsrcIndustrialClassification : industries) {
            if (CsrcIndustrialClassification.getCode().length() > 1) continue;

            // String baseUrl = CsrcIndustrialClassification.getDetailUrl();
            // parser(baseUrl);
        }
    }

    public void parse(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        parse(document);
    }

    public void parse(File file, String charset) throws IOException {
        Document document = Jsoup.parse(file, charset);
        parse(document);
    }

    public void parse(Document document) {
        Elements elements = document.getElementsByClass("table-bg");

        assert(elements.size() == 1);

        Element element = elements.get(0);
        elements = element.getElementsByTag("tbody");

        assert(elements.size() >= 1);

        element = elements.get(0);
        elements = element.getElementsByTag("tr");

        for (Element ele : elements) {
            Elements tdList = ele.getElementsByTag("td");
            String code = tdList.get(1).text().trim();
            String name = tdList.get(2).text().trim();
            String ccode = tdList.get(3).text().trim();
            String cname = tdList.get(4).text().trim();
            String icode = tdList.get(5).text().trim();
            String iname = tdList.get(6).text().trim();
            double ratio = parseDouble(tdList.get(7).text().trim(), 1000.00);
            double dratio = parseDouble(tdList.get(8).text().trim(), 10000.00);
            double pbv = parseDouble(tdList.get(9).text().trim(), 0.00);
            double dyr = parseDouble(tdList.get(10).text().trim(), 0.00);

            StockClassification stockClassification = new StockClassification();
            stockClassification.setCode(code);
            stockClassification.setName(name);
            // stockClassification.setCsrcCode(ccode);
            stockClassification.setCsrcLevelTwoCode(cname);
            // stockClassification.setIcode(icode);
            // stockClassification.setIname(iname);
            // stockClassification.setRatio(ratio);
            // stockClassification.setDratio(dratio);
            // stockClassification.setPbv(pbv);
            // stockClassification.setDyr(dyr);

            stockClassificationRepository.save(stockClassification);
        }
    }
}
