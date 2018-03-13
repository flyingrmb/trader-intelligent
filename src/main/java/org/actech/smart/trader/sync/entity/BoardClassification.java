package org.actech.smart.trader.sync.entity;

import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by paul on 2018/3/12.
 */
@Document
@NoArgsConstructor
public class BoardClassification extends CatalogClassification {
    @Override
    public boolean isLevelOne() {
        return true;
    }
}
