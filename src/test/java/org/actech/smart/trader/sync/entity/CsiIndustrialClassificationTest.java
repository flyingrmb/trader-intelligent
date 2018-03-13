package org.actech.smart.trader.sync.entity;

import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.EntityUtils;

import static org.junit.Assert.*;

/**
 * Created by paul on 2018/3/13.
 */
public class CsiIndustrialClassificationTest {
    @Test
    public void testCopyProperties() {
        CsiIndustrialClassification source = new CsiIndustrialClassification();
        CsiIndustrialClassification target = new CsiIndustrialClassification();

        source.setCode("10086");
        target.setDyr(12.09);
        EntityUtils.copyProperties(source, target);

        assertTrue(target.getDyr() == 12.09);
        assertTrue(target.getCode().equals("10086"));
    }
}