package com.theusick.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleModel {

    private Long id;

    private int year;
    private int mileage;
    private String color;
    private double price;
    private String licensePlate;

    private Long enterpriseId;
    private VehicleBrandModel brand;

    private List<VehicleDriverModel> vehicleDrivers;

}
