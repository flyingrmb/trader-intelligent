package org.actech.smart.trader.sync.market.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by paul on 2018/3/10.
 */
@Document
@Getter
@Setter
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

    @Transient
    private String url; // 保存数据爬取地址

    public int hashCode() {
        if (code == null) return 0;
        return code.hashCode();
    }
}
