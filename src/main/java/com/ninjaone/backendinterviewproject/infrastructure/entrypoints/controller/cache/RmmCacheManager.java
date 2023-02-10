package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.controller.cache;

public interface RmmCacheManager {

    Double get(final String deviceId);

    void put(final String deviceId, final Double calculation);
}
