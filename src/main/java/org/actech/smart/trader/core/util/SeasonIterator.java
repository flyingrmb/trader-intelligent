package org.actech.smart.trader.core.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by paul on 2018/3/15.
 */
public class SeasonIterator {
    private int year = 0;
    private int season = 0;

    public SeasonIterator() {
        Calendar calendar = Calendar.getInstance();
        this.init(calendar);
    }

    private void init(Calendar calendar) {
        this.year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        this.season = month / 4;
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
