package org.actech.smart.trader.sync.market.repository;

import org.actech.smart.trader.sync.market.entity.CsiIndustrialClassification;
import org.actech.smart.trader.core.repository.TrackRepository;

import java.util.List;

/**
 * Created by paul on 2018/3/12.
 */
public interface CsiIndustrialClassificationRepository extends TrackRepository<CsiIndustrialClassification> {
    CsiIndustrialClassification findByReleaseAndCode(String release, String code);
    CsiIndustrialClassification findFirstByCodeOrderByReleaseDesc(String code);

    CsiIndustrialClassification findFirstByOrderByReleaseDesc();
    List<CsiIndustrialClassification> findByRelease(String release);
}
