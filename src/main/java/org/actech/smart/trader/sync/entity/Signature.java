package org.actech.smart.trader.sync.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Created by paul on 2018/3/13.
 */
@Data
public class Signature {
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

    public boolean equals(Object object) {
        if (object == null) return false;
        if (!(object instanceof Signature)) return false;

        Signature another = (Signature)object;

        if (release == null || !release.equals(another.release)) return false;
        if (code == null || !code.equals(another.code)) return false;
        if (name == null || !name.equals(another.name)) return false;
        if (lyr == null || lyr.doubleValue() != another.lyr.doubleValue()) return false;
        if (ttm == null || ttm.doubleValue() != another.ttm.doubleValue()) return false;
        if (pb == null || pb.doubleValue() != another.pb.doubleValue()) return false;
        if (dyr == null || dyr.doubleValue() != another.dyr.doubleValue()) return false;

        return true;
    }

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
}
