package com.theusick.fleet.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverModel {

    private Long id;

    private String name;
    private int age;
    private double salary;

    private Set<Long> vehicleIds;

    private Long activeVehicleId;

}
