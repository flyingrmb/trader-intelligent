package org.actech.smart.trader.web.service;

import org.actech.smart.trader.sync.entity.CsrcIndustrialClassification;
import org.actech.smart.trader.sync.repositories.StockClassificationRepository;
import org.actech.smart.trader.sync.repositories.CsrcIndustrialClassificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paul on 2018/3/10.
 */
@Service
public class TopRatioSelection {
    @Autowired
    private CsrcIndustrialClassificationRepository csrcIndustrialClassificationRepository;
    @Autowired
    private StockClassificationRepository stockClassificationRepository;

    public List<String> selectTopRatio(double outdiff, double innerdiff) {
        List<String> result = new ArrayList<String>();

        Iterable<CsrcIndustrialClassification> industries = csrcIndustrialClassificationRepository.findAll();
        for (org.actech.smart.trader.sync.entity.CsrcIndustrialClassification CsrcIndustrialClassification : industries) {
            if (CsrcIndustrialClassification.getCode().length() == 1) continue;
            // if (CsrcIndustrialClassification.getRatio() >= 10000.00) continue;

            String icode = CsrcIndustrialClassification.getCode();
            
            // double threshold = outdiff * CsrcIndustrialClassification.getRatio();
            // List<StockClassification> backend = stockClassificationRepository.findByIcodeAndRatioLessThanOrderByRatioAsc(icode, threshold);

            // for (StockClassification syncmarket : backend) {
            //     if (syncmarket.getDratio() == 0.00) continue;

            //    if (syncmarket.getDratio() / syncmarket.getRatio() < innerdiff)
            //        result.add(syncmarket.briefInfo());
            // }
        }

        return result;
    }
}
