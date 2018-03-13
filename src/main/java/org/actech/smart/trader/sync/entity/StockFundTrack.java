package org.actech.smart.trader.sync.entity;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by paul on 2018/3/13.
 */
@Document
@NoArgsConstructor
public class StockFundTrack {
    @Id
    private String id;

    @Indexed
    protected String release; // 发布日期yyyy-MM-dd

    private String code; // 股票代码
    private String name; // 股票名称

    protected double lyr; // 最新市盈率
    protected double ttm; // 滚动市盈率
    protected double pb; // 市净率
    protected double dyr; // 股息率
}
