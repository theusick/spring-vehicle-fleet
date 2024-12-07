package com.theusick.controller.dto.vehicle;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@Schema(description = "Vehicle")
public class VehicleBaseDTO {

    private Long id;

    private int year;
    private int mileage;
    private String color;
    private double price;
    private String licensePlate;

    private Long brandId;

}
