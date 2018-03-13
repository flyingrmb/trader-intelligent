package org.actech.smart.trader.sync.entity;

import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by paul on 2018/3/10.
 */
@Document
@NoArgsConstructor
// China Securities Regulatory Commission Industrial Classification
public class CsrcIndustrialClassification extends BoardClassification implements Level {
    @Override
    public boolean isLevelOne() {
        return code.length() == 1;
    }
}
