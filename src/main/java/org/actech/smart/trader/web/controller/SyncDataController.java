package org.actech.smart.trader.web.controller;

import org.actech.smart.trader.web.service.SyncMarketDataService;
import org.actech.smart.trader.web.service.TopRatioSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Created by paul on 2018/3/10.
 */
@RestController
public class SyncDataController {
    @Autowired
    private SyncMarketDataService marketService;

    @Autowired
    private TopRatioSelection topRatioSelection;

    @RequestMapping("/syncmarket/{command}")
    public String syncmarket(@PathVariable String command) throws IOException {
        return syncmarket(command, "");
    }

    @RequestMapping("/syncmarket/{command}/param/{param}")
    public String syncmarket(@PathVariable String command, @PathVariable String param) throws IOException {
        marketService.sync(command, param);
        return "Command is committed successfully.";
    }

    @RequestMapping("/select/outdiff/{outdiff}/innerdiff/{innerdiff}")
    public String doJob(@PathVariable double outdiff, @PathVariable double innerdiff) throws IOException {
        List<String> contentList = topRatioSelection.selectTopRatio(outdiff, innerdiff);

        StringBuilder sb = new StringBuilder();
        for (String item : contentList) {
            sb.append(item + "\r\n");
        }

        return sb.toString();
    }

}
