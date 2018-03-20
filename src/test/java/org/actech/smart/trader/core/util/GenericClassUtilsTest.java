package org.actech.smart.trader.core.util;

import org.actech.smart.trader.sync.market.entity.CsiIndustrialClassification;
import org.actech.smart.trader.sync.market.entity.StockClassification;
import org.actech.smart.trader.sync.market.repository.CsiIndustrialClassificationRepository;
import org.actech.smart.trader.sync.market.repository.StockClassificationRepository;
import org.junit.Test;
import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.Type;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by paul on 2018/3/19.
 */
public class GenericClassUtilsTest {

    /*@Test
    public void shouldFindGenericClassType() {
        Class type = GenericClassUtils.getGenericParameterizedType(ClassB.class, ClassA.class, 0);
        assertThat(type.getSimpleName(), is(String.class.getSimpleName()));
    }

    @Test
    public void shouldFindGenericInterfaceType() {
        Class type = GenericClassUtils.getGenericParameterizedType(ClassC.class, InterfaceA.class, 0);
        assertThat(type.getSimpleName(), is(String.class.getSimpleName()));
    }

    @Test
    public void shouldFindSecondGenericInterfaceType() {
        Class type = GenericClassUtils.getGenericParameterizedType(ClassD.class, InterfaceA.class, 0);
        assertThat(type.getSimpleName(), is(String.class.getSimpleName()));
    }

    @Test
    public void shouldFindGenericInterfaceGenericType() {
        Class type = GenericClassUtils.getGenericParameterizedType(ClassD.class, InterfaceA.class, 0);
        assertThat(type.getSimpleName(), is(String.class.getSimpleName()));
    }*/

    @Test
    public void testAAlreadyInUseExample() {
        GenericClassUtils.traversal(CsiIndustrialClassificationRepository.class, CrudRepository.class, null);
    }

   /* @Test
    public void shouldGetAllTypes() {
        Type[] types = GenericClassUtils.getGenericParents(ClassF.class);
        System.out.println(types);
    }*/





    class ClassA<T> {}
    class ClassB extends ClassA<String> {}

    interface InterfaceA<T>{}
    class ClassC implements InterfaceA<String> {}

    interface InterfaceB<X> extends InterfaceA<String> {}
    class ClassD implements InterfaceB<Double> {}

    interface InterfaceC<X, T> extends InterfaceA<T> {}
    class ClassE implements InterfaceC<Double ,String>{}

    class ClassF extends ClassA implements InterfaceB<String>, InterfaceC<Double, String> {}
}