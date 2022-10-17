package com.flightright.monitor.cache;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class InMemoryUserKeyCache {
    private final Set<String> cache = Collections.synchronizedSet(new HashSet<>());

    public void add(String cacheKey) {
        cache.add(cacheKey);
    }

    public boolean contains(String cacheKey) {
        return cache.contains(cacheKey);
    }

    public void remove(String cacheKey) {
        cache.remove(cacheKey);
    }

    public void clear() {
        cache.clear();
    }
}
