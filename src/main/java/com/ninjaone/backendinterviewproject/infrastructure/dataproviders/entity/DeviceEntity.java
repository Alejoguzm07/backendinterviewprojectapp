package com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity(name = "device")
public class DeviceEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "service_cost", nullable = false)
    private Double serviceCost;

    @OneToOne
    private DeviceTypeEntity deviceType;

    @ManyToMany
    private List<ServiceEntity> serviceList;
}
