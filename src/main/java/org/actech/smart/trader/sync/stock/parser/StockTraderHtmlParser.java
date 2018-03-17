package org.actech.smart.trader.sync.stock.parser;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.actech.smart.trader.core.parser.CacheableParser;
import org.actech.smart.trader.core.util.NumericUtils;
import org.actech.smart.trader.sync.market.entity.StockClassification;
import org.actech.smart.trader.sync.stock.entity.StockTradeTrack;
import org.actech.smart.trader.sync.stock.repository.StockTradeTrackRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by paul on 2018/3/15.
 */
@Service
public class StockTraderHtmlParser extends CacheableParser<HtmlPage> {
    private Log logger = LogFactory.getLog(getClass());

    @Autowired
    private StockTradeTrackRepository repository;

    @Override
    public boolean shouldParse(HtmlPage htmlPage) {
        List<HtmlElement> contents = navigator(htmlPage);
        return contents != null && contents.size() > 1;
    }

    @Override
    public void parse(HtmlPage htmlPage, Object parameter) {
        Assert.notNull(parameter, "stockCode should not be null.");
        Assert.isTrue(parameter instanceof StockClassification, "");

        List<HtmlElement> contents = navigator(htmlPage);
        if (contents == null) {
            logger.info("Found no content in the html page");
            return ;
        }

        for (HtmlElement element : contents) {
            if ("thead".equals(element.getParentNode().getNodeName()))
                continue;

            parse(element, (StockClassification)parameter);
        }
    }

    private List<HtmlElement> navigator(HtmlPage htmlPage) {
        if (htmlPage == null) return new ArrayList<HtmlElement>();

        HtmlElement htmlElement = htmlPage.getDocumentElement();

        Assert.notNull(htmlElement, "html element should not be null.");

        List<HtmlElement> elementList = htmlElement.getElementsByAttribute("div", "class", "inner_box");
        Assert.notNull(elementList, "element list should not be null.");
        Assert.isTrue(elementList.size() > 0, "element size should not be null.");

        htmlElement = elementList.get(0);

        return htmlElement.getElementsByTagName("tr");
    }

    private void parse(HtmlElement element, StockClassification classification) {
        List<HtmlElement> elements = element.getElementsByTagName("td");
        Assert.notNull(elements, "elements should not be null.");
        Assert.isTrue(elements.size() == 11, "The number of elements is not correct.");

        StockTradeTrack stockTradeTrack = new StockTradeTrack();
        stockTradeTrack.setRelease(elements.get(0).asText());
        stockTradeTrack.setCode(classification.getCode());
        stockTradeTrack.setName(classification.getName());

        stockTradeTrack.setOpenPrice(getValue(elements.get(1)));
        stockTradeTrack.setClosingPrice(getValue(elements.get(2)));
        stockTradeTrack.setHighestPrice(getValue(elements.get(3)));
        stockTradeTrack.setLowestPrice(getValue(elements.get(4)));
        stockTradeTrack.setMargin(getValue(elements.get(5)));
        stockTradeTrack.setMarginRatio(getValue(elements.get(6)));
        stockTradeTrack.setVolumn(getValue(elements.get(7)));
        stockTradeTrack.setTurnVolumn(getValue(elements.get(8)));
        stockTradeTrack.setAmplitude(getValue(elements.get(9)));
        stockTradeTrack.setTurnoverRate(getValue(elements.get(10)));

        StockTradeTrack target = repository.findByReleaseAndCode(stockTradeTrack.getRelease(), stockTradeTrack.getCode());
        if (target == null) target = new StockTradeTrack();

        EntityUtils.copyProperties(stockTradeTrack, target);

        repository.save(target);
    }

    private Double getValue(HtmlElement element) {
        return NumericUtils.parseDouble(element.asText());
    }
}
