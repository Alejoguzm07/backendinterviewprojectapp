package com.ninjaone.backendinterviewproject.configuration.controller;

import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper.DeviceMapper;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper.DeviceTypeMapper;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper.ServiceMapper;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper.impl.DeviceMapperImpl;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper.impl.DeviceTypeMapperImpl;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper.impl.ServiceMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperSetup {

    @Bean
    public DeviceMapper deviceMapper(final DeviceTypeMapper deviceTypeMapper, final ServiceMapper serviceMapper) {
        return new DeviceMapperImpl(deviceTypeMapper, serviceMapper);
    }

    @Bean
    public DeviceTypeMapper deviceTypeMapper(){
        return new DeviceTypeMapperImpl();
    }

    @Bean
    public ServiceMapper serviceMapper(final DeviceTypeMapper deviceTypeMapper){
        return new ServiceMapperImpl(deviceTypeMapper);
    }


}
