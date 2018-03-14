package org.actech.smart.trader.core.util;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by paul on 2018/3/11.
 */
public class LimitedSizeCache<K, V> {
    private Map<K, V> cache = new ConcurrentHashMap<K, V>();
    private Queue<K> indices = new ConcurrentLinkedQueue<K>();
    private final int size;

    public LimitedSizeCache(int size) {
        this.size = size;
    }

    public V get(K key) {
        return cache.get(key);
    }

    public void add(K key, V value) {
        if (value == null || key == null) return ;
        cache.put(key, value);
        indices.add(key);

        if (indices.size() > size) {
            synchronized (this) {
                if (indices.size() > size) {
                    K oldestKey = indices.remove();
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
