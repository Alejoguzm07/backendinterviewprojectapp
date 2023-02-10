package com.ninjaone.backendinterviewproject.configuration.repository;

import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceRepository;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceTypeRepository;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.ServiceRepository;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper.DeviceEntityMapper;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper.DeviceTypeEntityMapper;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper.ServiceEntityMapper;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.repository.DeviceRepositoryImpl;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.repository.DeviceTypeRepositoryImpl;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.repository.ServiceRepositoryImpl;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.repository.jpa.DeviceRepositoryJpa;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.repository.jpa.DeviceTypeRepositoryJpa;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.repository.jpa.ServiceRepositoryJpa;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositorySetup {

    @Bean
    DeviceRepository deviceRepository(final DeviceRepositoryJpa deviceRepositoryJpa,
                                      final DeviceEntityMapper deviceEntityMapper) {
        return new DeviceRepositoryImpl(deviceRepositoryJpa, deviceEntityMapper);
    }

    @Bean
    ServiceRepository serviceRepository(final ServiceRepositoryJpa serviceRepositoryJpa,
                                        final ServiceEntityMapper serviceEntityMapper) {
        return new ServiceRepositoryImpl(serviceRepositoryJpa, serviceEntityMapper);
    }

    @Bean
    DeviceTypeRepository deviceTypeRepository(final DeviceTypeRepositoryJpa deviceTypeRepositoryJpa,
                                              final DeviceTypeEntityMapper deviceTypeEntityMapper){
        return new DeviceTypeRepositoryImpl(deviceTypeRepositoryJpa,deviceTypeEntityMapper);
    }
}
