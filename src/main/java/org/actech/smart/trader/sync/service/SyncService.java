package org.actech.smart.trader.sync.service;

/**
 * Created by paul on 2018/3/12.
 */
public interface SyncService {
    default void sync(int command, String dateStr) {
        if (command == 1) {
            syncToday();
            return ;
        }

        if (command == 2) {
            syncAllHis();
            return ;
        }

        if (command == 3) {
            syncOneDay(dateStr);
            return ;
        }

        if (command == 4) {
            syncPeriodHis(dateStr);
            return ;
        }
    }

    void syncToday();
    void syncAllHis();

    void syncOneDay(String dateStr);
    void syncPeriodHis(String dateStr);
}
