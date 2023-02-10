package com.ninjaone.backendinterviewproject.configuration.utils;

import com.ninjaone.backendinterviewproject.domain.utils.IdGenerator;
import com.ninjaone.backendinterviewproject.domain.utils.impl.DefaultUUIDGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdGeneratorSetup {

    @Bean
    IdGenerator idGenerator(){
        return new DefaultUUIDGenerator();
    }
}
