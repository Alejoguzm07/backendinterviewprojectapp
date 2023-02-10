package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.controller;

import com.ninjaone.backendinterviewproject.domain.usecases.AddServiceUseCase;
import com.ninjaone.backendinterviewproject.domain.usecases.DeleteServiceUseCase;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.ServiceRequest;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.ServiceResponse;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper.ServiceMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/service")
public class ServiceController {

    private final ServiceMapper serviceMapper;

    private final AddServiceUseCase addServiceUseCase;

    private final DeleteServiceUseCase deleteServiceUseCase;

    @PostMapping
    private ResponseEntity<ServiceResponse> addService(@RequestBody final ServiceRequest service){
        return ResponseEntity.ok(
                serviceMapper.toResponse(
                        addServiceUseCase.add(
                                serviceMapper.fromRequest(service)
                        )
                )
        );
    }

    @DeleteMapping("/{serviceId}")
    private ResponseEntity<String> deleteService(@PathVariable final String serviceId){
        deleteServiceUseCase.delete(serviceId);
        return ResponseEntity.ok("Successfully deleted");
    }
}
