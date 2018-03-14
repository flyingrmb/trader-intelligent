package org.actech.smart.trader.sync.market.repository;

import org.actech.smart.trader.sync.market.entity.StockClassification;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by paul on 2018/3/10.
 */
public interface StockClassificationRepository extends CrudRepository<StockClassification, String> {
    Optional<StockClassification> findById(String code);
}
