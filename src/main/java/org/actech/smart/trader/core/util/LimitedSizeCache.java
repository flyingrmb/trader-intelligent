package org.actech.smart.trader.core.util;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by paul on 2018/3/11.
 */
public class LimitedSizeCache {
    private Map<String, Object> cache = new ConcurrentHashMap<String, Object>();
    private Queue<String> indices = new ConcurrentLinkedQueue<String>();
    private final int size;

    public LimitedSizeCache(int size) {
        this.size = size;
    }

    public <T> T get(String key, Class<T> type) {
        return (T)cache.get(key);
    }

    public void add(String key, Object value) {
        if (value == null || key == null) return ;
        cache.put(key, value);
        indices.add(key);

        if (indices.size() > size) {
            synchronized (this) {
                if (indices.size() > size) {
                    String oldestKey = indices.remove();
                    cache.remove(oldestKey);
                }
            }
        }
    }

    public synchronized void clear() {
        indices.clear();
        cache.clear();
    }
}
