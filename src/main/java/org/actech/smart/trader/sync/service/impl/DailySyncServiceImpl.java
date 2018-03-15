package org.actech.smart.trader.sync.service.impl;

import org.actech.smart.trader.registry.annotation.Registry;
import org.actech.smart.trader.registry.annotation.ServicePoint;
import org.actech.smart.trader.sync.service.DailySyncService;
import org.actech.smart.trader.sync.market.service.BoardTrackSyncService;
import org.actech.smart.trader.sync.market.service.CsiTrackSyncService;
import org.actech.smart.trader.sync.market.service.CsrcTrackSyncService;
import org.actech.smart.trader.sync.stock.service.StockTrackSyncService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by paul on 2018/3/15.
 */
@Service
@Registry
public class DailySyncServiceImpl implements DailySyncService {
    private final Log logger = LogFactory.getLog(DailySyncServiceImpl.class);

    @Autowired
    private CsrcTrackSyncService csrcTrackSyncService;

    @Autowired
    private CsiTrackSyncService csiTrackSyncService;

    @Autowired
    private BoardTrackSyncService boardTrackSyncService;

    @Autowired
    private StockTrackSyncService stockTrackSyncService;


    @Override
    @ServicePoint(code="sync", name="同步全量当日输入", example = "service/sync")
    public void downloadAndSync(String param) {
        logger.info("处理CSRC当日数据>>>");
        csrcTrackSyncService.syncCsrcCurrentData(null);
        logger.info("处理CSI当日数据>>>");
        csiTrackSyncService.syncCurrentCsiData(null);
        logger.info("处理主板当日数据>>>");
        boardTrackSyncService.syncCurrentBoardData(null);
        logger.info("处理当日所有股票基本面数据>>>");
        stockTrackSyncService.syncStockDetailInfo(null);
        logger.info("完成所有数据处理！");
    }
}
