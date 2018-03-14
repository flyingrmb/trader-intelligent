package org.actech.smart.trader.sync.market.repository;

import org.actech.smart.trader.sync.market.entity.BoardClassification;
import org.actech.smart.trader.core.repository.TrackRepository;

/**
 * Created by paul on 2018/3/12.
 */
public interface BoardClassificationRepository extends TrackRepository<BoardClassification> {
    BoardClassification findByReleaseAndCode(String release, String code);
}
