package org.actech.smart.trader.sync.stock.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by paul on 2018/3/14.
 */
@Document
@Data
@NoArgsConstructor
public class StockTradeTrack {
    @Id
    private String id;

    @Indexed
    private String release;

    @Indexed
    private String code;
    private String name;

    private Double openPrice;
    private Double closingPrice;
    private Double highestPrice;
    private Double lowestPrice;
    private Double margin;
    private Double marginRatio;
    private Double volumn;
    private Double turnVolumn;
    private Double amplitude;
    private Double turnoverRate;
}
