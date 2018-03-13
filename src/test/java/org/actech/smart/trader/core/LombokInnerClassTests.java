package org.actech.smart.trader.core;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

/**
 * Created by paul on 2018/3/11.
 */
public class LombokInnerClassTests {

    @Test
    public void annotationShouldValidInInnerClass() {
        InnerObject object = new InnerObject();
        object.setProperty1("Hello");
        assertThat(object.getProperty1(), is("Hello"));
    }


    @Data
    @NoArgsConstructor
    class InnerObject {
        private String property1;
    }
}
