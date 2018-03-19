package org.actech.smart.trader.core.entity;

import lombok.Data;
import org.actech.smart.trader.core.util.NumericUtils;
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

    public int hashCode() {
        if (code == null && release == null) return 0;
        if (code == null) return release.hashCode();
        if (release == null) return code.hashCode();

        return release.hashCode() + code.hashCode();
    }

    public boolean equals(Object object) {
        if (object == null) return false;
        if (!(object instanceof Signature)) return false;

        Signature another = (Signature)object;

        if (release == null || !release.equals(another.release)) return false;
        if (code == null || !code.equals(another.code)) return false;
        if (name == null || !name.equals(another.name)) return false;
        if (!NumericUtils.equals(lyr, another.lyr)) return false;
        if (!NumericUtils.equals(ttm, another.ttm)) return false;
        if (!NumericUtils.equals(pb, another.pb)) return false;
        if (!NumericUtils.equals(dyr, another.dyr)) return false;

        return true;
    }
}
