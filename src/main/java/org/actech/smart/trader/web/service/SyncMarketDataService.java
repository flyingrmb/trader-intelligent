package org.actech.smart.trader.web.service;

import org.actech.smart.trader.sync.CompanyHtmlParser;
import org.actech.smart.trader.sync.StockDetailHtmlParser;
import org.actech.smart.trader.sync.StockSummarizeHtmlParser;
import org.actech.smart.trader.sync.service.BoardClassificationSync;
import org.actech.smart.trader.sync.service.CsiIndustrialClassificationSync;
import org.actech.smart.trader.sync.service.CsrcIndustrialClassificationSync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by paul on 2018/3/10.
 */
@Service
public class SyncMarketDataService {
    private static final String STOCK_URL = "http://quote.eastmoney.com/stocklist.html";

    @Autowired
    private CsrcIndustrialClassificationSync csrcSync;

    @Autowired
    private CsiIndustrialClassificationSync csiSync;

    @Autowired
    private BoardClassificationSync boardSync;

    public void sync(String command, String param) throws IOException {
        Worker worker = new Worker(this.csrcSync, this.csiSync, this.boardSync);
        worker.setCommand(command, param);

        Thread thread = new Thread(worker);
        thread.start();
    }

    class Worker implements Runnable {
        private CsrcIndustrialClassificationSync csrcSync;
        private CsiIndustrialClassificationSync csiSync;
        private BoardClassificationSync boardSync;

        public Worker(CsrcIndustrialClassificationSync csrcSync,
                      CsiIndustrialClassificationSync csiSync,
                      BoardClassificationSync boardSync) {
            this.csrcSync = csrcSync;
            this.csiSync = csiSync;
            this.boardSync = boardSync;
        }

        @Override
        public void run() {
            csrcSync.sync(csrcCmd, param);
            csiSync.sync(csiCmd, param);
            boardSync.sync(boardCmd, param);
        }

        private int csrcCmd = 0;
        private int csiCmd = 0;
        private int boardCmd = 0;

        private String param = null;

        public void setCommand(String command, String param) {
            this.param = param;

            int indicator = Integer.parseInt(command, 16);
            this.csrcCmd = (indicator >> 8) & 15;
            this.csiCmd = (indicator >> 4) & 15;
            this.boardCmd = indicator & 15;
        }
    }
}
