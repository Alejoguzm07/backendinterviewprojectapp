package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.controller;

import com.ninjaone.backendinterviewproject.domain.usecases.AddDeviceTypeUseCase;
import com.ninjaone.backendinterviewproject.domain.usecases.DeleteDeviceTypeUseCase;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.DeviceTypeRequest;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.DeviceTypeResponse;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper.DeviceTypeMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/device-type")
public class DeviceTypeController {

    private final AddDeviceTypeUseCase addDeviceTypeUseCase;

    private final DeleteDeviceTypeUseCase deleteDeviceTypeUseCase;

    private final DeviceTypeMapper deviceTypeMapper;

    @PostMapping
    ResponseEntity<DeviceTypeResponse> addDeviceType(@RequestBody final DeviceTypeRequest deviceType) {
        return ResponseEntity.ok(
                deviceTypeMapper.toResponse(
                        addDeviceTypeUseCase.add(
                                deviceTypeMapper.fromRequest(deviceType)
                        )
                )
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteDeviceType(final @PathVariable String id) {
        deleteDeviceTypeUseCase.delete(id);
        return ResponseEntity.ok("Successfully deleted");
    }

}
