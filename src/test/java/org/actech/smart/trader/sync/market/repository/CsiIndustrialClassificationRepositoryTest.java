package org.actech.smart.trader.sync.market.repository;

import org.actech.smart.trader.Application;
import org.actech.smart.trader.UnitTestConfiguration;
import org.actech.smart.trader.sync.market.entity.CsiIndustrialClassification;
import org.actech.smart.trader.test.annotation.MockData;
import org.actech.smart.trader.test.annotation.MockDataInit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Pageable;

import static org.junit.Assert.*;

/**
 * Created by paul on 2018/3/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, UnitTestConfiguration.class})
@MockDataInit(@MockData(file = "classpath:entity/csi_classifications.json", repository = CsiIndustrialClassificationRepository.class))
@ActiveProfiles("dev")
public class CsiIndustrialClassificationRepositoryTest {
    @Autowired
    private CsiIndustrialClassificationRepository repository;

    @Test
    public void traversalUsingPage() {
        int page = 0;
        int size = 100;

        Slice<CsiIndustrialClassification> classificationSlice = repository.findAll(PageRequest.of(page, size));
        while (classificationSlice.hasContent()) {
            classificationSlice.forEach(it -> {
                System.out.println("Each: " + it.getCode() + it.getRelease());
            });

            classificationSlice = repository.findAll(PageRequest.of(++page, size));
        }
    }
}