package org.actech.smart.trader.core.trait;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by paul on 2018/3/13.
 */
public interface Level {
    @JsonIgnore Boolean isLevelOne();
    @ JsonIgnore Boolean isLastLevel();
}
