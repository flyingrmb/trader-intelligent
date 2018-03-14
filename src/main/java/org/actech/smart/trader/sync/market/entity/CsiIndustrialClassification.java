package org.actech.smart.trader.sync.market.entity;

import lombok.NoArgsConstructor;
import org.actech.smart.trader.core.trait.Level;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by paul on 2018/3/12.
 */
@Document
@NoArgsConstructor
// China Securities Index
public class CsiIndustrialClassification extends BoardClassification implements Level {
    @Override
    public boolean isLevelOne() {
        return code.length() == 2;
    }
}
