package org.actech.smart.trader.data.service;

import org.actech.smart.trader.core.util.NumericUtils;
import org.actech.smart.trader.registry.annotation.Registry;
import org.actech.smart.trader.registry.annotation.ServicePoint;
import org.actech.smart.trader.sync.market.entity.BoardClassification;
import org.actech.smart.trader.sync.market.entity.CsiIndustrialClassification;
import org.actech.smart.trader.sync.market.entity.CsrcIndustrialClassification;
import org.actech.smart.trader.sync.market.repository.BoardClassificationRepository;
import org.actech.smart.trader.sync.market.repository.CsiIndustrialClassificationRepository;
import org.actech.smart.trader.sync.market.repository.CsrcIndustrialClassificationRepository;
import org.actech.smart.trader.sync.stock.entity.StockFundTrack;
import org.actech.smart.trader.sync.stock.repository.StockFundTrackRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.xml.crypto.Data;

/**
 * Created by paul on 2018/3/20.
 */
@Service
@Registry
public class DataRepairService {
    private final Log logger = LogFactory.getLog(DataRepairService.class);

    @ServicePoint(code="repair", name="数据修复", async=true,
            example="service/repair={csi}{csrc}{board}{fund}{trade}")
    public String repairData(String param) {
        Assert.notNull(param, "input param should not be null.");

        if ("csi".equals(param)) return repairCsiData();
        if ("csrc".equals(param)) return repairCsrcData();
        if ("board".equals(param)) return repairBoardData();
        if ("fund".equals(param)) return repairFundData();
        if ("trade".equals(param)) return repairTradeData();

        Assert.isTrue(false, "The param is not valid, please see the service help.");
        return "FALSE";
    }

    @Autowired
    private CsiIndustrialClassificationRepository csiRepository;

    private String repairCsiData() {
        int page = 0;
        final int size = 1000;

        Slice<CsiIndustrialClassification> classificationSlice = csiRepository.findAll(PageRequest.of(page, size));
        while (classificationSlice.hasContent()) {
            classificationSlice.forEach(it -> {
                if (NumericUtils.isAnchorValue(it.getDyr())) it.setDyr(null);
                if (NumericUtils.isAnchorValue(it.getLyr())) it.setLyr(null);
                if (NumericUtils.isAnchorValue(it.getTtm())) it.setTtm(null);
                if (NumericUtils.isAnchorValue(it.getPb())) it.setPb(null);
            });
            csiRepository.saveAll(classificationSlice);
            classificationSlice = csiRepository.findAll(PageRequest.of(++page, size));
            logger.info("[csi]已处理数据量：" + page * size + "/" + ((PageImpl)classificationSlice).getTotalElements());
        }
        logger.info("[csi]所有数据均已完成修复");
        return "SUCCESS";
    }

    @Autowired
    private CsrcIndustrialClassificationRepository csrcRepository;

    private String repairCsrcData() {
        int page = 0;
        final int size = 1000;

        Slice<CsrcIndustrialClassification> classificationSlice = csrcRepository.findAll(PageRequest.of(page, size));
        while (classificationSlice.hasContent()) {
            classificationSlice.forEach(it -> {
                if (NumericUtils.isAnchorValue(it.getDyr())) it.setDyr(null);
                if (NumericUtils.isAnchorValue(it.getLyr())) it.setLyr(null);
                if (NumericUtils.isAnchorValue(it.getTtm())) it.setTtm(null);
                if (NumericUtils.isAnchorValue(it.getPb())) it.setPb(null);
            });
            csrcRepository.saveAll(classificationSlice);
            classificationSlice = csrcRepository.findAll(PageRequest.of(++page, size));
            logger.info("[csrc]已处理数据量：" + page * size + "/" + ((PageImpl)classificationSlice).getTotalElements());
        }
        logger.info("[csrc]所有数据均已完成修复");
        return "SUCCESS";
    }

    @Autowired
    private BoardClassificationRepository boardRepository;

    private String repairBoardData() {
        int page = 0;
        final int size = 1000;

        Slice<BoardClassification> classificationSlice = boardRepository.findAll(PageRequest.of(page, size));
        while (classificationSlice.hasContent()) {
            classificationSlice.forEach(it -> {
                if (NumericUtils.isAnchorValue(it.getDyr())) it.setDyr(null);
                if (NumericUtils.isAnchorValue(it.getLyr())) it.setLyr(null);
                if (NumericUtils.isAnchorValue(it.getTtm())) it.setTtm(null);
                if (NumericUtils.isAnchorValue(it.getPb())) it.setPb(null);
            });
            boardRepository.saveAll(classificationSlice);
            classificationSlice = boardRepository.findAll(PageRequest.of(++page, size));
            logger.info("[Board]已处理数据量：" + page * size + "/" + ((PageImpl)classificationSlice).getTotalElements());
        }
        logger.info("[Board]所有数据均已完成修复");
        return "SUCCESS";
    }

    @Autowired
    private StockFundTrackRepository fundRepository;

    private String repairFundData() {
        int page = 0;
        final int size = 1000;

        Slice<StockFundTrack> classificationSlice = fundRepository.findAll(PageRequest.of(page, size));
        while (classificationSlice.hasContent()) {
            classificationSlice.forEach(it -> {
                if (NumericUtils.isAnchorValue(it.getDyr())) it.setDyr(null);
                if (NumericUtils.isAnchorValue(it.getLyr())) it.setLyr(null);
                if (NumericUtils.isAnchorValue(it.getTtm())) it.setTtm(null);
                if (NumericUtils.isAnchorValue(it.getPb())) it.setPb(null);
            });
            fundRepository.saveAll(classificationSlice);
            classificationSlice = fundRepository.findAll(PageRequest.of(++page, size));
            logger.info("[FundTrack]已处理数据量：" + page * size + "/" + ((PageImpl)classificationSlice).getTotalElements());
        }
        logger.info("[FundTrack]所有数据均已完成修复");
        return "SUCCESS";
    }

    private String repairTradeData() {
        return null;
    }
}
