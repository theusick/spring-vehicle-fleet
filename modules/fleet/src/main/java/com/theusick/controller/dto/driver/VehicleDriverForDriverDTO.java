package com.theusick.controller.dto.driver;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@Schema(description = "VehicleDriverForDriver")
public class VehicleDriverForDriverDTO {

    // Contains only vehicleId
    @NotNull
    Long id;

}
