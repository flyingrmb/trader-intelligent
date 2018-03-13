package org.actech.smart.trader.core.common;

import org.jsoup.nodes.Element;

/**
 * Created by paul on 2018/3/10.
 */
public class NumericUtils {

    public static Double parseDouble(String text, Double defval) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return defval;
        }
    }

    public static boolean equals(Double value1, Double value2) {
        if (value1 == null && value2 == null) return true;
        if (value1 == null || value2 == null) return false;
        return value1 == value2;
    }
}
