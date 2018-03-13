package org.actech.smart.trader.sync.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Created by paul on 2018/3/12.
 */
@Data
public abstract class CatalogClassification {
    public static final Double DEF_INDEX = Double.MAX_VALUE;

    @Id
    protected String id;

    @Indexed
    protected String release; // 发布日期yyyy-MM-dd
    @Indexed
    protected String code; // 行业代码

    protected String name; // 行业名称
    protected Double lyr; // 最新市盈率
    protected Double ttm; // 滚动市盈率
    protected Double pb; // 市净率
    protected Double dyr; // 股息率

    @Transient
    protected Double index; // 页面录入指标

    @Transient
    protected String stockUrl; // 该字段不保存入数据库

    public void setLyr(Double value) {
        if (value == DEF_INDEX) value = 1.0;
        this.lyr = value;
    }

    public void setTtm(Double value) {
        if (value == DEF_INDEX) value = 1.0;
        this.ttm = value;
    }

    public void setPb(Double value) {
        if (value == DEF_INDEX) value = 1.0;
        this.pb = value;
    }

    public abstract boolean isLevelOne();
}
