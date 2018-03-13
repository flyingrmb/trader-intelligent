package org.actech.smart.trader.sync.repositories;

import org.actech.smart.trader.sync.entity.CsrcIndustrialClassification;

/**
 * Created by paul on 2018/3/10.
 */
public interface CsrcIndustrialClassificationRepository extends TrackRepository<CsrcIndustrialClassification> {
    CsrcIndustrialClassification findByReleaseAndCode(String release, String code);
}
