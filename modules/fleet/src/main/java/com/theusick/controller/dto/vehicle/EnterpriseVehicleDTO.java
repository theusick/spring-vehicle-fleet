package com.theusick.controller.dto.vehicle;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@Schema(description = "EnterpriseVehicle")
public class EnterpriseVehicleDTO {

    @JsonUnwrapped
    private VehicleBaseDTO vehicle;
    private Long enterpriseId;

}
