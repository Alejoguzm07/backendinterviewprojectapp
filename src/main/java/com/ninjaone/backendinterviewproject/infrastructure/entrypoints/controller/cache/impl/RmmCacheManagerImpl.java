package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.controller.cache.impl;

import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.controller.cache.RmmCacheManager;
import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;

@AllArgsConstructor
public class RmmCacheManagerImpl implements RmmCacheManager {

    private final Cache caffeinCache;
    @Override
    public Double get(String deviceId) {
        final Cache.ValueWrapper cachedValue = caffeinCache.get(deviceId);
        if(cachedValue != null){
            return (Double) cachedValue.get();
        }
        return null;
    }

    @Override
    public void put(String deviceId, Double calculation) {
        caffeinCache.put(deviceId,calculation);
    }
}
