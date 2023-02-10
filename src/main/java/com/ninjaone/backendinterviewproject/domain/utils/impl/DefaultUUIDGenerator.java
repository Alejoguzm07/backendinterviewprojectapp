package com.ninjaone.backendinterviewproject.domain.utils.impl;

import com.ninjaone.backendinterviewproject.domain.utils.IdGenerator;

import java.util.UUID;

public class DefaultUUIDGenerator implements IdGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
