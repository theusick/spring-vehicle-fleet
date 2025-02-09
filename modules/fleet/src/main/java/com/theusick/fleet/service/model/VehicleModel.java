package com.theusick.fleet.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
@With
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
    private OffsetDateTime purchaseDate;

    private Long enterpriseId;
    private Long brandId;

    private Set<Long> driverIds;

}
