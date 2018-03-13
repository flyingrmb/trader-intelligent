package org.actech.smart.trader.core.common;

import org.jsoup.nodes.Element;

/**
 * Created by paul on 2018/3/10.
 */
public class NumericParser {

    public static Double parseDouble(String text, Double defval) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return defval;
        }
    }
}
