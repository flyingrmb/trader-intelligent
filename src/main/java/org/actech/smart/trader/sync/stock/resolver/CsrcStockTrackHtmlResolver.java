package org.actech.smart.trader.sync.stock.resolver;

import org.actech.smart.trader.sync.stock.entity.StockFundTrack;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

import static org.actech.smart.trader.core.util.NumericUtils.parseDouble;
import static org.actech.smart.trader.core.entity.Signature.DEF_INDEX;

/**
 * Created by paul on 2018/3/13.
 */
@Component
public class CsrcStockTrackHtmlResolver extends StockHtmlResolver<StockFundTrack> {
    @Override
    public Collection<StockFundTrack> resolve(Document document) {
        Collection<StockFundTrack> result = new HashSet<StockFundTrack>();

        Elements elements = navigatorToContents(document);

        for (Element element : elements) {
            Elements tdList = element.getElementsByTag("td");
            assert (tdList.size() == 11);

            StockFundTrack stockFundTrack = new StockFundTrack();

            stockFundTrack.setCode(tdList.get(1).text().trim());
            stockFundTrack.setName(tdList.get(2).text().trim());

            stockFundTrack.setLyr(parseDouble(tdList.get(7).text().trim(), 1.00));
            stockFundTrack.setTtm(parseDouble(tdList.get(8).text().trim(), 1.00));
            stockFundTrack.setPb(parseDouble(tdList.get(9).text().trim(), 1.00));
            stockFundTrack.setDyr(parseDouble(tdList.get(10).text().trim(), DEF_INDEX));

            result.add(stockFundTrack);
        }

        return result;
    }
}
