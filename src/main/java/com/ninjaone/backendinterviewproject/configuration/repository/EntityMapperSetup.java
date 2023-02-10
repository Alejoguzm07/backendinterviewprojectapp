package com.ninjaone.backendinterviewproject.configuration.repository;

import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper.DeviceEntityMapper;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper.DeviceTypeEntityMapper;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper.ServiceEntityMapper;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper.impl.DeviceEntityMapperImpl;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper.impl.DeviceTypeEntityMapperImpl;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper.impl.ServiceEntityMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityMapperSetup {

    @Bean
    public DeviceTypeEntityMapper deviceTypeEntityMapper() {
        return new DeviceTypeEntityMapperImpl();
    }

    @Bean
    public ServiceEntityMapper serviceEntityMapper(final DeviceTypeEntityMapper deviceTypeEntityMapper) {
        return new ServiceEntityMapperImpl(deviceTypeEntityMapper);
    }

    @Bean
    public DeviceEntityMapper deviceEntityMapper(final ServiceEntityMapper serviceEntityMapper,
                                                 final DeviceTypeEntityMapper deviceTypeEntityMapper){
        return new DeviceEntityMapperImpl(deviceTypeEntityMapper, serviceEntityMapper);
    }
}
