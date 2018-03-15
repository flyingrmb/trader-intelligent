package org.actech.smart.trader.sync.market.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.actech.smart.trader.core.entity.Signature;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by paul on 2018/3/12.
 */
@Document
@NoArgsConstructor
@Getter
@Setter
public class BoardClassification extends Signature {
    @Transient
    protected Double index; // 页面录入指标

    @Transient
    protected String stockUrl; // 该字段不保存入数据库
}
