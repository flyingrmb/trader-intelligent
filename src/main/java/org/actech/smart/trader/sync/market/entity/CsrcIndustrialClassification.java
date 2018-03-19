package org.actech.smart.trader.sync.market.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NoArgsConstructor;
import org.actech.smart.trader.core.trait.Level;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by paul on 2018/3/10.
 */
@Document
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
// China Securities Regulatory Commission Industrial Classification
public class CsrcIndustrialClassification extends BoardClassification implements Level {
    @Override
    public Boolean isLevelOne() {
        return code.length() == 1;
    }

    @Override
    public Boolean isLastLevel() {
        return code.length() == 3;
    }
}
