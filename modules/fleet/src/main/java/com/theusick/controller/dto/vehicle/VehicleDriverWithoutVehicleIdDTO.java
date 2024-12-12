package com.theusick.controller.dto.vehicle;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@Schema(description = "VehicleDriverWithoutVehicleIdDTO")
public class VehicleDriverWithoutVehicleIdDTO {

    private Long driverId;
    private boolean active;

}
