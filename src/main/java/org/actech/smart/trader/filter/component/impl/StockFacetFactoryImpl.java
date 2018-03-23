package org.actech.smart.trader.filter.component.impl;

import org.actech.smart.trader.core.entity.Signature;
import org.actech.smart.trader.core.repository.TrackRepository;
import org.actech.smart.trader.filter.component.StockFacetFactory;
import org.actech.smart.trader.filter.entity.StockFacet;
import org.actech.smart.trader.sync.market.entity.CsiIndustrialClassification;
import org.actech.smart.trader.sync.market.entity.StockClassification;
import org.actech.smart.trader.sync.market.repository.CsiIndustrialClassificationRepository;
import org.actech.smart.trader.sync.market.repository.CsrcIndustrialClassificationRepository;
import org.actech.smart.trader.sync.market.repository.StockClassificationRepository;
import org.actech.smart.trader.sync.stock.entity.StockFundTrack;
import org.actech.smart.trader.sync.stock.repository.StockFundTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paul on 2018/3/18.
 */
@Component
public class StockFacetFactoryImpl implements StockFacetFactory {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CsiIndustrialClassificationRepository industrialRepository;

    @Autowired
    private StockClassificationRepository stockRepository;

    @Autowired
    private StockFundTrackRepository fundTrackRepository;

    @Override
    public List<StockFacet> createStockFacet(String type) {
        List<StockFacet> result = new ArrayList<StockFacet>();

        stockRepository.findAll().forEach(it -> {
            StockFacet facet = createStockFacet(type, it);
            if (facet != null) result.add(facet);
        });

        return result;
    }

    private StockFacet createStockFacet(String type, StockClassification stockClassification) {
        StockFacet facet = new StockFacet();
        facet.setStockClassification(stockClassification);

        if ("csi".equals(type)) {
            CsiIndustrialClassificationRepository repository = applicationContext.getBean(CsiIndustrialClassificationRepository.class);
            Signature classification = repository.findFirstByCodeOrderByReleaseDesc(stockClassification.getCsiLevelFourCode());
            facet.setIndustryClassification(classification);
        } else if ("csrc".equals(type)) {
            CsrcIndustrialClassificationRepository repository = applicationContext.getBean(CsrcIndustrialClassificationRepository.class);
            Signature classification = repository.findFirstByCodeOrderByReleaseDesc(stockClassification.getCsrcLevelTwoCode());
            facet.setIndustryClassification(classification);
        }

        if (facet.getIndustryClassification() == null) return null;

        StockFundTrack stockFundTrack = fundTrackRepository.findFirstByCodeOrderByReleaseDesc(stockClassification.getCode());
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
