package org.actech.smart.trader.sync.repositories;

import org.actech.smart.trader.sync.entity.CsiIndustrialClassification;

/**
 * Created by paul on 2018/3/12.
 */
public interface CsiIndustrialClassificationRepository extends TrackRepository<CsiIndustrialClassification> {
    CsiIndustrialClassification findByReleaseAndCode(String release, String code);
}
