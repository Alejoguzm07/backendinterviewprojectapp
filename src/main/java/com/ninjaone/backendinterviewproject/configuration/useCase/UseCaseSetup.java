package com.ninjaone.backendinterviewproject.configuration.useCase;

import com.ninjaone.backendinterviewproject.domain.usecases.*;
import com.ninjaone.backendinterviewproject.domain.usecases.impl.*;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceRepository;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceTypeRepository;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.ServiceRepository;
import com.ninjaone.backendinterviewproject.domain.utils.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseSetup {

    @Bean
    AddDeviceUseCase addDeviceUseCase(final DeviceRepository deviceRepository,
                                      final DeviceTypeRepository deviceTypeRepository,
                                      final IdGenerator idGenerator) {
        return new AddDeviceUseCaseImpl(deviceRepository, deviceTypeRepository, idGenerator);
    }

    @Bean
    AddDeviceTypeUseCase addDeviceTypeUseCase(final DeviceTypeRepository deviceTypeRepository,
                                              final IdGenerator idGenerator){
        return new AddDeviceTypeUseCaseImpl(deviceTypeRepository, idGenerator);
    }

    @Bean
    AddServiceUseCase addServiceUseCase(final ServiceRepository serviceRepository,
                                        final DeviceTypeRepository deviceTypeRepository,
                                        final IdGenerator idGenerator){
        return new AddServiceUseCaseImpl(serviceRepository, deviceTypeRepository, idGenerator);
    }

    @Bean
    DeleteDeviceUseCase deleteDeviceUseCase(final DeviceRepository deviceRepository){
        return new DeleteDeviceUseCaseImpl(deviceRepository);
    }

    @Bean
    DeleteServiceUseCase deleteServiceUseCase(final ServiceRepository serviceRepository){
        return new DeleteServiceUseCaseImpl(serviceRepository);
    }

    @Bean
    DeleteDeviceTypeUseCase deleteDeviceTypeUseCase(final DeviceTypeRepository deviceTypeRepository){
        return new DeleteDeviceTypeUseCaseImpl(deviceTypeRepository);
    }

    @Bean
    AssignServiceUseCase assignServiceUseCase(final ServiceRepository serviceRepository,
                                              final DeviceRepository deviceRepository){
        return new AssignServiceUseCaseImpl(deviceRepository, serviceRepository);
    }

    @Bean
    UnassignServiceUseCase unassignServiceUseCase(final DeviceRepository deviceRepository){
        return new UnassignServiceUseCaseImpl(deviceRepository);
    }

    @Bean
    CalculateDeviceTotalCostUseCase calculateDeviceTotalCostUseCase(final DeviceRepository deviceRepository){
        return new CalculateDeviceTotalCostUseCaseImpl(deviceRepository);
    }

    @Bean
    CalculateTotalCostUseCase calculateTotalCostUseCase(final DeviceRepository deviceRepository){
        return new CalculateTotalCostUseCaseImpl(deviceRepository);
    }

    @Bean
    CalculateDeviceServiceCostUseCase calculateDeviceServiceCostUseCase(final DeviceRepository deviceRepository){
        return new CalculateDeviceServiceCostUseCaseImpl(deviceRepository);
    }

}
