package com.theusick.controller.dto.brand;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@Schema(description = "VehicleBrandId")
public class VehicleBrandIdDTO {

    @NotNull
    Long id;

}
