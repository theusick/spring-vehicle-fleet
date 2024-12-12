package com.theusick.controller.dto.driver;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@Schema(description = "VehicleDriverWithoutDriverIdDTO")
public class VehicleDriverWithoutDriverIdDTO {

    private Long vehicleId;
    private boolean active;

}
