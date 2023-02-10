package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceTypeRequest {
    private String name;
    private Double cost;
}
