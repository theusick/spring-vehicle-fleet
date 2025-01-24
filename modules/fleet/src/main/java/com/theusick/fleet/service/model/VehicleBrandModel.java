package com.theusick.fleet.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleBrandModel {

    private Long id;

    private String name;
    private String type;
    private int seats;
    private double fuelTank;
    private double payloadCapacity;

}
