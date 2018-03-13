package org.actech.smart.trader.sync.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by paul on 2018/3/10.
 */
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockClassification {
    @Id
    private String code; // 股票代码
    private String name; // 股票名称

    @Indexed
    private String csrcLevelOneCode; // 证监会门类代码
    private String csrcLevelOneName; // 证监会门类名称

    @Indexed
    private String csrcLevelTwoCode; // 证监会大类代码
    private String csrcLevelTwoName; // 证监会大类名称

    // China Securities Index
    @Indexed
    private String csiLevelOneCode; // 中证行业一级代码
    private String csiLevelOneName; // 中证行业一级名称
    @Indexed
    private String csiLevelTwoCode; // 中证行业二级代码
    private String csiLevelTwoName; // 中证行业二级名称
    @Indexed
    private String csiLevelThreeCode; // 中证行业三级代码
    private String csiLevelThreeName; // 中证行业三级名称
    @Indexed
    private String csiLevelFourCode; // 中证行业四级代码
    private String csiLevelFourName; // 中证行业四级名称

    // 股票所属板块
    private String boardCode; // 板块代码
    private String boardName; // 板块名称

    // private double ratio; // 静态市盈率
    // private double dyr; // 最新股息率

    // private String detailUrl; // 详细信息链接

    // private double receipts; // 收益
    // private double dratio; // 动态市盈率
    // private double netAsset; // 净资产（每股）
    // private double pbv; // 市净率

    public String briefInfo() {
        // return name + "[" + code + "]\t\t\t\t\t\t\t\t{" + iname + "}[" + icode + "]\t\t\t\t\t\t\t\t(" + ratio + "," + dratio + ")";
        return "";
    }
}
