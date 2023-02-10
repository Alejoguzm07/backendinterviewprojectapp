package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequest {

    private String name;

    private Double cost;

    private List<String> acceptedDevices;
}
