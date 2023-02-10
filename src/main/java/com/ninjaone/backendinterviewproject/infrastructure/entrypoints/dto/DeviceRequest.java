package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceRequest {

    private String name;

    private String deviceType;

}
