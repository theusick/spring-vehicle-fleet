package com.theusick.fleet.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverModel {

    private Long id;

    private String name;
    private int age;
    private double salary;

    private Long enterpriseId;

    private List<VehicleDriverModel> vehicleDrivers;

    private VehicleModel activeVehicle;

}
