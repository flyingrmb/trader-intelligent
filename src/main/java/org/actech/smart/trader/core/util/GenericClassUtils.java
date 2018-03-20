package org.actech.smart.trader.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Created by paul on 2018/3/19.
 */
public class GenericClassUtils {
    private static final Log logger = LogFactory.getLog(GenericClassUtils.class);

    public static void traversal(Class source, Class target, Stack<Type> path) {
        getAllParents(source).forEach(it -> {
            System.out.println(it.getTypeName());
            if (it instanceof Class) {
                traversal((Class) it, target, null);
            } else if (it instanceof ParameterizedType) {
                // traversal(((ParameterizedType)it).getRawType(), target, null);
            }
        });
    }

    public static List<Type> getAllParents(Class source) {
        List<Type> parents = new ArrayList<Type>();

        Type superClass = source.getGenericSuperclass();
        if (superClass != null) parents.add(superClass);

        Type[] superInterfaces = source.getGenericInterfaces();
        if (superInterfaces != null) parents.addAll(Arrays.asList(superInterfaces));

        return parents;
    }

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
