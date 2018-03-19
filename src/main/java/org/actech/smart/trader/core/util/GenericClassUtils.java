package org.actech.smart.trader.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by paul on 2018/3/19.
 */
public class GenericClassUtils {
    private static final Log logger = LogFactory.getLog(GenericClassUtils.class);

    public static Class<?> getGenericParameterizedType(Class clazz, Class target, int index) {
        ParameterizedType type = getParameterizedType(clazz, target);
        if (type == null) {
            logger.error("当前类的父类中没有找到对应的目标对象");
            return null;
        }

        if (!(index < type.getActualTypeArguments().length)) {
            logger.error("泛型父类的泛型数量不足。");
            return null;
        }

        return (Class<?>) type.getActualTypeArguments()[index];
    }

    public static ParameterizedType getParameterizedType(Class clazz, Class target) {
        Class superClazz = clazz.getSuperclass();
        if (target.getSimpleName().equals(superClazz.getSimpleName())) {
            Type type = clazz.getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                return (ParameterizedType)type;
            }
        }

        return getParameterizedType(clazz.getSuperclass(), target);
    }
}
