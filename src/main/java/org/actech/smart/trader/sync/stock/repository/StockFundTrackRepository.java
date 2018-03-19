package org.actech.smart.trader.sync.stock.repository;

import org.actech.smart.trader.core.repository.TrackRepository;
import org.actech.smart.trader.sync.stock.entity.StockFundTrack;

import java.util.Collection;

/**
 * Created by paul on 2018/3/13.
 */
public interface StockFundTrackRepository extends TrackRepository<StockFundTrack> {
    StockFundTrack findFirstByOrderByReleaseDesc();

    Collection<StockFundTrack> findAllByRelease(String release);
    StockFundTrack findFirstByCodeOrderByReleaseDesc(String code);
}
