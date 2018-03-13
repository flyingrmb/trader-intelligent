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

/**
 * Created by paul on 2018/3/10.
 */
@Component
public class StockDetailHtmlParser {
    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private StockClassificationRepository stockClassificationRepository;

    public void updateStockDetailInfo() {
        logger.info("更新股票最新动态市盈率");

        long count = stockClassificationRepository.count();
        logger.info("总计待更新股票数： " + count);

        Iterable<StockClassification> companies = stockClassificationRepository.findAll();

        int progress = 0;
        for (StockClassification stockClassification : companies) {
            int retry = 0;
            parse(stockClassification, retry);
            progress += 1;
            if (progress % 100 == 0) {
                logger.info("处理进度：" + progress + "/" + count + "……");
            }
        }
        logger.info("动态市盈率刷新完成");
    }

    public void parse(StockClassification stockClassification, int retry) {
        if (retry >= 3) {
            logger.info("网络状况差，重试三次后仍无法获取数据，股票编码：" + stockClassification.getCode());
            return ;
        }

        // try {
            // Document document = Jsoup.connect(stockClassification.getDetailUrl()).get();
            // parser(document, stockClassification);
        // } catch (IOException e) {
        //    logger.warn("链接失败，开始重试，股票代码：" + stockClassification.getCode());
        //    parser(stockClassification, ++retry);
        // }
    }

    public void parse(File file, String charset, StockClassification stockClassification) throws IOException {
        Document document = Jsoup.parse(file, charset);
        parse(document, stockClassification);
    }

    private void parse(Document document, StockClassification stockClassification) {
        Element element = document.getElementById("rtp2");
        assert(element != null);

        Elements tdList = element.getElementsByTag("td");
        for (Element td : tdList)
            setProperty(stockClassification, td.text().trim());

        stockClassificationRepository.save(stockClassification);
    }

    private void setProperty(StockClassification stockClassification, String text) {
/*        if (text.startsWith("收益(三)：")) {
            stockClassification.setReceipts(parseDouble(text.split("：")[1].trim(), 0.00));
        } else if (text.startsWith("PE(动)：")) {
            stockClassification.setDratio(parseDouble(text.split("：")[1].trim(), 10000.00));
        } else if (text.startsWith("净资产：")) {
            stockClassification.setNetAsset(parseDouble(text.split("：")[1].trim(), 0.00));
        } else if (text.startsWith("市净率：")) {
            stockClassification.setPbv(parseDouble(text.split("：")[1].trim(), 0.00));
        }*/
    }
}
