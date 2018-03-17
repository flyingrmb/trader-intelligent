package org.actech.smart.trader.core.parser;

import org.actech.smart.trader.core.trait.Cacheable;
import org.actech.smart.trader.core.net.NetworkResourceCache;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by paul on 2018/3/11.
 */
public abstract class CacheableParser<T> implements HtmlParser, Cacheable {
    @Autowired
    private NetworkResourceCache cache;

    @Override
    public boolean shouldParse(String url) {
        Class<T> entityClass = (Class<T>)(getParameterizedType(getClass())).getActualTypeArguments()[0];
        return shouldParse(cache.get(url, entityClass));
    }

    public abstract boolean shouldParse(T htmlPage);

    @Override
    public void parse(String url, Object parameter) {
        Class<T> entityClass = (Class<T>)(getParameterizedType(getClass())).getActualTypeArguments()[0];
        parse(cache.get(url, entityClass), parameter);
    }

    private ParameterizedType getParameterizedType(Class clazz) {
       Type type = clazz.getGenericSuperclass();
       if (type instanceof ParameterizedType)
           return (ParameterizedType)type;

       return getParameterizedType((Class)type);
    }

    public abstract void parse(T htmlPage, Object dateStr);
}
