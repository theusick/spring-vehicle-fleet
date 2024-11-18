package com.theusick.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String plateNumber;

    private VehicleBrandModel brand;

}
