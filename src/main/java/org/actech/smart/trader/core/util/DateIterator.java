package org.actech.smart.trader.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by paul on 2018/3/11.
 */
public class DateIterator {
    private static Log logger = LogFactory.getLog(DateIterator.class);

    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat format;

    public DateIterator(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        if (dateStr == null || dateStr.trim().length() == 0) {
            this.init(new Date(), format);
            return ;
        }

        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            logger.warn("解析日期字符串失败", e);
            date = new Date();
        }

        this.init(date, format);
    }

    public DateIterator() {
        this(new Date());
    }

    public DateIterator(Date date) {
        this(date, new SimpleDateFormat("yyyy-MM-dd"));
    }

    public DateIterator(Date date, SimpleDateFormat dateFormat) {
        this.init(date, dateFormat);
    }

    private void init(Date date, SimpleDateFormat format) {
        calendar.setTime(date);
        this.format = format;
    }

    public DateIterator yesterday() {
        return before(Calendar.DATE, 1);
    }

    public DateIterator before(int field, int diff) {
        calendar.add(field, -diff);
        return this;
    }

    public String get() {
        return format.format(calendar.getTime());
    }
}
