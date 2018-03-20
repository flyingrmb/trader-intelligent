package org.actech.smart.trader.filter.component.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.actech.smart.trader.Application;
import org.actech.smart.trader.UnitTestConfiguration;
import org.actech.smart.trader.core.util.JacksonUtils;
import org.actech.smart.trader.filter.entity.Condition;
import org.actech.smart.trader.filter.entity.StockFacet;
import org.actech.smart.trader.sync.market.entity.CsiIndustrialClassification;
import org.actech.smart.trader.sync.market.entity.StockClassification;
import org.actech.smart.trader.sync.market.repository.CsiIndustrialClassificationRepository;
import org.actech.smart.trader.sync.market.repository.StockClassificationRepository;
import org.actech.smart.trader.test.annotation.MockData;
import org.actech.smart.trader.test.annotation.MockDataInit;
import org.actech.smart.trader.test.runner.JpaMockRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by paul on 2018/3/18.
 */
@RunWith(JpaMockRunner.class)
@SpringBootTest(classes = {Application.class, UnitTestConfiguration.class})
@MockDataInit({@MockData(
        file="classpath:entity/stock_classifications.json",
        repository= StockClassificationRepository.class
        // entity=StockClassification.class
        ),
        @MockData(
                file="classpath:entity/csi_classifications.json",
                repository= CsiIndustrialClassificationRepository.class
                // entity=CsiIndustrialClassification.class
                )})
@ActiveProfiles("dev")
public class CsiStockFilterTest {
    @Autowired
    private CsiStockFilter filter;

    @Test
    public void filterStockIfNoCondtions() {
        List<Condition> conditions = new ArrayList<Condition>();

        List<StockFacet> result = filter.filter(conditions);
        assertThat(result.size(), is(0));
    }

    @Test
    public void filterStockOfLyr() {
        List<Condition> conditions = new ArrayList<Condition>();
        Condition condition = new Condition("lyr", 0.8);
        conditions.add(condition);

        List<StockFacet> result = filter.filter(conditions);
    }

    @Test
    public void shouldParseFileWithJackson() throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:entity/stock_classifications.json");

        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = JacksonUtils.getCollectionType(ArrayList.class, StockClassification.class);

        List<StockClassification> classifications = (List<StockClassification>)mapper.readValue(resource.getFile(), javaType);
        assertThat(classifications, is(notNullValue()));
    }
}