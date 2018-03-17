package org.actech.smart.trader.core.util;

/**
 * Created by paul on 2018/3/10.
 */
public class NumericUtils {
    public static Double parseDouble(String text) {
        return parseDouble(text, null);
    }

    public static Double parseDouble(String text, Double def) {
        return parseDouble(text, 1.00, def);
    }


    public static Double parseDouble(String text, Double ratio, Double def) {
        try {
            if (text == null) return def;

            text = text.trim().replaceAll(",", "").replaceAll(" ", "");
            if (text.endsWith("亿")) {
                ratio *= 100000000;
                text = text.substring(0, text.length() - "亿".length());
            } else if (text.endsWith("万")) {
                ratio *= 10000;
                text = text.substring(0, text.length() - "万".length());
            } else if (text.endsWith("元")) {
                ratio *= 1;
                text = text.substring(0, text.length() - "亿".length());
            } else if (text.endsWith("%")) {
                ratio *= 0.01;
                text = text.substring(0, text.length() - "%".length());
            }

            return Double.parseDouble(text) * ratio;
        } catch (NumberFormatException e) {
            return def;
        }
    }

    public static boolean equals(Double value1, Double value2) {
        if (value1 == null && value2 == null) return true;
        if (value1 == null || value2 == null) return false;
        return value1 == value2;
    }
}
