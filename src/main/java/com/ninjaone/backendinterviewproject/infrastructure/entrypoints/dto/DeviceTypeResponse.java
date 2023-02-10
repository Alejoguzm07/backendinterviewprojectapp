package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceTypeResponse {
    private String id;
    private String name;
    private Double cost;
}
