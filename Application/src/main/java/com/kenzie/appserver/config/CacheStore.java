package com.kenzie.appserver.config;

import com.google.common.cache.Cache;

import javax.annotation.PreDestroy;

public class CacheStore<K, V> {

    private final Cache<K, V> cache;

    public CacheStore(Cache<K, V> cache) {
        this.cache = cache;
    }

    public V get(K key) {
        return cache.getIfPresent(key);
    }

    public void put(K key, V value) {
        cache.put(key, value);
    }

    public void invalidate(K key) {
        cache.invalidate(key);
    }

    @PreDestroy
    public void cleanUp() {
        cache.invalidateAll();
        cache.cleanUp();
    }
}
