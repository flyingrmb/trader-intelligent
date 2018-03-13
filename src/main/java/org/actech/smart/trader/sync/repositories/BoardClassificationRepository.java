package org.actech.smart.trader.sync.repositories;

import org.actech.smart.trader.sync.entity.BoardClassification;

/**
 * Created by paul on 2018/3/12.
 */
public interface BoardClassificationRepository extends TrackRepository<BoardClassification> {
    BoardClassification findByReleaseAndCode(String release, String code);
}
