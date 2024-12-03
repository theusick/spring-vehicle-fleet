package com.theusick.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@Schema(description = "VehicleBrand")
public class VehicleDTO {

    private Long id;

    private int year;
    private int mileage;
    private String color;
    private double price;
    private String plateNumber;

    private Long brandId;

}
