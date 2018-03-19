package org.actech.smart.trader.filter.entity;

import lombok.Data;

/**
 * Created by paul on 2018/3/18.
 */
@Data
public class Condition {
    private String index;
    private Double relative;

    public Condition(String index, Double relative) {
        this.index = index;
        this.relative = relative;
    }
}
