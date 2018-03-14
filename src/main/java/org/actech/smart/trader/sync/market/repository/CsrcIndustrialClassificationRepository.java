package org.actech.smart.trader.sync.market.repository;

import org.actech.smart.trader.sync.market.entity.CsrcIndustrialClassification;
import org.actech.smart.trader.core.repository.TrackRepository;

/**
 * Created by paul on 2018/3/10.
 */
public interface CsrcIndustrialClassificationRepository extends TrackRepository<CsrcIndustrialClassification> {
    CsrcIndustrialClassification findByReleaseAndCode(String release, String code);
}
