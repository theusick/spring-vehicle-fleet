package com.theusick.controller.dto.vehicle;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@Schema(description = "VehicleDriverForVehicle")
public class VehicleDriverForVehicleDTO {

    // Contains only driverId
    @NotNull
    Long id;

}
