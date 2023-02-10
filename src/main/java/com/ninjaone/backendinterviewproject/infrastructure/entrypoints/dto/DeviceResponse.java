package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceResponse {

    private String id;

    private String name;

    private DeviceTypeResponse deviceType;

    private List<ServiceResponse> serviceList;
}
