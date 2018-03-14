package org.actech.smart.trader.sync.repositories;

import org.actech.smart.trader.Application;
import org.actech.smart.trader.UnitTestConfiguration;
import org.actech.smart.trader.sync.market.entity.BoardClassification;
import org.actech.smart.trader.sync.market.repository.BoardClassificationRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by paul on 2018/3/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, UnitTestConfiguration.class})
@ActiveProfiles("dev")
public class BoardClassificationRepositoryTest {
    @Autowired
    private BoardClassificationRepository repository;

    @Test
    @Ignore
    public void checkPackageRename() {
        BoardClassification classification = repository.findByReleaseAndCode("2018-03-13", "ZXB");
        assertTrue(classification != null);
    }

}