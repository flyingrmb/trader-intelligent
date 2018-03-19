package org.actech.smart.trader.filter.component.impl;

import org.actech.smart.trader.filter.component.StockFacetFactory;
import org.actech.smart.trader.filter.entity.StockFacet;
import org.actech.smart.trader.sync.market.entity.CsiIndustrialClassification;
import org.actech.smart.trader.sync.market.entity.StockClassification;
import org.actech.smart.trader.sync.market.repository.CsiIndustrialClassificationRepository;
import org.actech.smart.trader.sync.market.repository.StockClassificationRepository;
import org.actech.smart.trader.sync.stock.entity.StockFundTrack;
import org.actech.smart.trader.sync.stock.repository.StockFundTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paul on 2018/3/18.
 */
@Component
public class CsiStockFacetFactory implements StockFacetFactory {
    @Autowired
    private CsiIndustrialClassificationRepository industrialRepository;

    @Autowired
    private StockClassificationRepository stockRepository;

    @Autowired
    private StockFundTrackRepository fundTrackRepository;

    @Override
    public List<StockFacet> createStockFacet() {
        List<StockFacet> result = new ArrayList<StockFacet>();

        stockRepository.findAll().forEach(it -> {
            StockFacet facet = createStockFacet(it);
            if (facet != null) result.add(facet);
        });

        return result;
    }

    private StockFacet createStockFacet(StockClassification it) {
        StockFacet facet = new StockFacet();
        facet.setStockClassification(it);

        CsiIndustrialClassification classification = industrialRepository.findFirstByCodeOrderByReleaseDesc(it.getCsiLevelFourCode());
        facet.setIndustryClassification(classification);

        StockFundTrack stockFundTrack = fundTrackRepository.findFirstByCodeOrderByReleaseDesc(it.getCode());
        facet.setStockFundTrack(stockFundTrack);

        if (stockFundTrack == null) return null;
        if (stockFundTrack.getPeg() == null) return null;
        if (stockFundTrack.getLyr() == null) return null;
        if (stockFundTrack.getTtm() == null) return null;
        if (stockFundTrack.getPeg() > stockFundTrack.getLyr()) return null;
        if (stockFundTrack.getPeg() > stockFundTrack.getTtm()) return null;

        return facet;
    }
}
