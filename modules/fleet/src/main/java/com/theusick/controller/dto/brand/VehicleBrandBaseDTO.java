package com.theusick.controller.dto.brand;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@Schema(description = "VehicleBrand")
public class VehicleBrandBaseDTO {

    private Long id;

    private String name;
    private String type;
    private int seats;
    private double fuelTank;
    private double payloadCapacity;

}
