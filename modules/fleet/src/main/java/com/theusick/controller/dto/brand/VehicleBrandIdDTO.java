package com.theusick.controller.dto.brand;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@Schema(description = "VehicleBrandId")
public class VehicleBrandIdDTO {

    private Long id;

}
