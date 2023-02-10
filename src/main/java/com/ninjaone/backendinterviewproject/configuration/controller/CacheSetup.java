package com.ninjaone.backendinterviewproject.configuration.controller;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.controller.cache.RmmCacheManager;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.controller.cache.impl.RmmCacheManagerImpl;
import org.springframework.cache.Cache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheSetup {

    private final Integer timeToLive;

    private final String cacheName;

    public CacheSetup(@Value("${cache.name}") String cacheName, @Value("${cache.ttl}") Integer timeToLive) {
        this.timeToLive = timeToLive;
        this.cacheName = cacheName;
    }

    @Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder().expireAfterWrite(timeToLive, TimeUnit.MINUTES);
    }

    @Bean
    public Cache cache(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager(cacheName);
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager.getCache(cacheName);
    }

    @Bean
    public RmmCacheManager rmmCacheManager(final Cache caffeinCache){
        return new RmmCacheManagerImpl(caffeinCache);
    }

}
