package org.actech.smart.trader.core.common;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;


import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

/**
 * Created by paul on 2018/3/11.
 */
public class DateIteratorTest {
    @Test
    public void shouldIteratorDays() {
        DateIterator iterator = new DateIterator();
        String todayStr = iterator.get();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String compareTo = dateFormat.format(new Date());
        assertThat(todayStr, is(compareTo));

        compareTo = dateFormat.format(new Date(new Date().getTime() - 86400000L));
        String yesterdayStr = iterator.yesterday().get();
        assertThat(yesterdayStr, is(compareTo));
    }
}