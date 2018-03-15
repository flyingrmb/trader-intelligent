package org.actech.smart.trader.core.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by paul on 2018/3/15.
 */
public class SeasonIteratorTest {
    @Test
    public void shouldIteratorSeason() {
        SeasonIterator iterator = new SeasonIterator();
        System.out.println(iterator.year());
        System.out.println(iterator.season());

        iterator = iterator.preSeason();
        System.out.println(iterator.year());
        System.out.println(iterator.season());
    }

}