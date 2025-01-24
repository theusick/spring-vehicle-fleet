package com.theusick.fleet.controller.dto.brand;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@Schema(description = "VehicleBrand")
public class VehicleBrandBaseDTO {

    @NotNull
    Long id;

    @NotBlank
    String name;
    @NotBlank
    String type;
    int seats;
    double fuelTank;
    double payloadCapacity;

}
