package com.theusick.frontend.controller.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Hidden
@Builder
@Jacksonized
public class VehicleViewDTO {

    private Long id;

    private int year;
    private int mileage;
    private String color;
    private double price;
    private String licensePlate;

    private Long brandId;
    private Long enterpriseId;

}
