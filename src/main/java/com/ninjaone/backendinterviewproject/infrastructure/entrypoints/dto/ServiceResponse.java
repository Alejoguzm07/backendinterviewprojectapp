package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto;

import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse {

    private String id;

    private String name;

    private Double cost;

    private List<DeviceTypeResponse> acceptedDevices;

}
