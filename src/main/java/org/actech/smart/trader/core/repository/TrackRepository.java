package org.actech.smart.trader.core.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by paul on 2018/3/12.
 */
@NoRepositoryBean
public interface TrackRepository<T> extends CrudRepository<T, String> {
    T findByReleaseAndCode(String release, String code);
}
