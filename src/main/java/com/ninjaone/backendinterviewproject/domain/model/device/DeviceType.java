package com.ninjaone.backendinterviewproject.domain.model.device;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@Builder
public class DeviceType {

    private String id;
    private String name;
    private Double cost;

}
