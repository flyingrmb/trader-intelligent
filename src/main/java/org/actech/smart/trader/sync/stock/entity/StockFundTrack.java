package org.actech.smart.trader.sync.stock.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.actech.smart.trader.core.entity.Signature;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by paul on 2018/3/13.
 */
@Document
@Getter
@Setter
@NoArgsConstructor
public class StockFundTrack extends Signature {
    private Double eps; // 每股收益(Earning Per Share)
    private Double peg; // 动态市盈率
    private Double navps; // 每股净资产(Net Asset Value Per Share)
    private Double br; // 企业营收(Enterprise Revenue)
    private Double bryoy; // 营收同比(Enterprise Revenue Year-On-Year)
    private Double np; // 净利润(Net Profit)
    private Double npyoy; // 净利润同比(Net Profit Year-On-Year)
    private Double gir; // 毛利率(Gross Interest Rate)
    private Double nir; // 净利率(Net Interest Rate)
    private Double roe; // 净资产收益率(Rate of Return on Common Stockholders’ Equity)
    private Double dr; // 负债率(Debt Ratio)
    private Double tcs; // 总股本(Total Capital Stock)
    private Double tmv; // 总市值(Total Market Value)
    private Double csc; // 流通股本(Capital Stock of Circulation)
    private Double mvc; // 流通市值（Market Value of Circulation)
    private Double udpps; // 每股未分配利润（UnDistributed Profit Per Share)
}
