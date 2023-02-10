package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.controller;

import com.ninjaone.backendinterviewproject.domain.model.device.Device;
import com.ninjaone.backendinterviewproject.domain.usecases.*;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.controller.cache.RmmCacheManager;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.DeviceRequest;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.DeviceResponse;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper.DeviceMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/device")
public class DeviceController {

    private final DeviceMapper deviceMapper;
    private final RmmCacheManager cacheManager;
    private final AddDeviceUseCase addDeviceUseCase;
    private final DeleteDeviceUseCase deleteDeviceUseCase;
    private final AssignServiceUseCase assignServiceUseCase;
    private final UnassignServiceUseCase unassignServiceUseCase;
    private final CalculateTotalCostUseCase calculateTotalCostUseCase;
    private final CalculateDeviceTotalCostUseCase calculateDeviceTotalCostUseCase;
    private final CalculateDeviceServiceCostUseCase calculateDeviceServiceCostUseCase;

    @PostMapping
    public ResponseEntity<DeviceResponse> addDevice(@RequestBody final DeviceRequest device) {
        return ResponseEntity.ok(
                deviceMapper.toResponse(
                        addDeviceUseCase.add(
                                deviceMapper.fromRequest(device)
                        )
                )
        );
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<String> deleteDevice(@PathVariable final String deviceId) {
        deleteDeviceUseCase.delete(deviceId);
        return ResponseEntity.ok("Successfully deleted");
    }

    @PostMapping("/assign/device-id/{deviceId}/service-id/{serviceId}")
    public ResponseEntity<DeviceResponse> assignServiceToDevice(@PathVariable final String deviceId, @PathVariable final String serviceId) {
        final Device updatedDevice = assignServiceUseCase.assign(deviceId, serviceId);
        cacheManager.put(deviceId,updatedDevice.getServiceCost());
        return ResponseEntity.ok(
                deviceMapper.toResponse(
                        updatedDevice
                )
        );
    }

    @PostMapping("/unassign/device-id/{deviceId}/service-id/{serviceId}")
    public ResponseEntity<DeviceResponse> unassignServiceToDevice(@PathVariable final String deviceId, @PathVariable final String serviceId) {
        final Device updatedDevice = unassignServiceUseCase.unassign(deviceId, serviceId);
        cacheManager.put(deviceId,updatedDevice.getServiceCost());
        return ResponseEntity.ok(
                deviceMapper.toResponse(
                        updatedDevice
                )
        );
    }

    @GetMapping("/calculate-total")
    public ResponseEntity<Double> calculateTotal() {
        return ResponseEntity.ok(calculateTotalCostUseCase.calculate());
    }

    @GetMapping("/calculate-total/{deviceId}")
    public ResponseEntity<Double> calculateTotalDeviceCost(@PathVariable final String deviceId) {
        return ResponseEntity.ok(calculateDeviceTotalCostUseCase.calculate(deviceId));
    }

    @GetMapping("/calculate-services/{deviceId}")
    public ResponseEntity<Double> calculateServicesTotal(@PathVariable final String deviceId) {
        final Double cachedResult = cacheManager.get(deviceId);
        if(cachedResult != null) {
            return ResponseEntity.ok(cachedResult);
        } else {
            final Double calculationResult = calculateDeviceServiceCostUseCase.calculate(deviceId);
            cacheManager.put(deviceId, calculationResult);
            return ResponseEntity.ok(calculationResult);
        }
    }

}
