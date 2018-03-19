package org.actech.smart.trader.core.export;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.actech.smart.trader.registry.annotation.Registry;
import org.actech.smart.trader.registry.annotation.ServicePoint;
import org.actech.smart.trader.sync.market.entity.CsiIndustrialClassification;
import org.actech.smart.trader.sync.market.entity.StockClassification;
import org.actech.smart.trader.sync.market.repository.CsiIndustrialClassificationRepository;
import org.actech.smart.trader.sync.market.repository.StockClassificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by paul on 2018/3/18.
 */
@Component
@Registry
public class EntityExporter {
    @Autowired
    private StockClassificationRepository stockClassificationRepository;

    @Autowired
    private CsiIndustrialClassificationRepository csiClassificationRepository;

    @ServicePoint(code="export1", name="导出股票分类表")
    public void exportStockClassification(String parameter) throws IOException {
        Iterable<StockClassification> stockClassifications = stockClassificationRepository.findAll();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("/Users/paul/stock_classifications.json"), stockClassifications);
    }

    @ServicePoint(code="export2", name="导出CSI最新数据表")
    public void exportLatestCsiClassification(String parameter) throws IOException {
        CsiIndustrialClassification csiClassification = csiClassificationRepository.findFirstByOrderByReleaseDesc();
        Assert.notNull(csiClassification, "No record in the database");

        List<CsiIndustrialClassification> classifications = csiClassificationRepository.findByRelease(csiClassification.getRelease());

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("/Users/paul/csi_classifications.json"), classifications);
    }
}
