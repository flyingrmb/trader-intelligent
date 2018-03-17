package org.actech.smart.trader.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by paul on 2018/3/15.
 */
public class SeasonIterator {
    private static final Log logger = LogFactory.getLog(SeasonIterator.class);

    private int year = 0;
    private int season = 0;

    public SeasonIterator() {
        Calendar calendar = Calendar.getInstance();
        this.init(calendar);
    }

    public SeasonIterator(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        if (dateStr == null || dateStr.trim().length() == 0) {
            this.init(Calendar.getInstance());
            return ;
        }

        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(format.parse(dateStr));
        } catch (ParseException e) {
            logger.warn("解析日期字符串失败", e);
        }

        this.init(calendar);
    }

    private void init(Calendar calendar) {
        this.year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        this.season = month / 3;
    }



    public SeasonIterator preSeason() {
        if (season == 0) year--;
        season = (season + 3) % 4;
        return this;
    }

    public int year() {
        return year;
    }

    public int season() {
        return season + 1;
    }
}
