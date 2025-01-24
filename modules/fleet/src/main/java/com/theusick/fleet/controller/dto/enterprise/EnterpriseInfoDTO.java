package com.theusick.fleet.controller.dto.enterprise;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@Schema(name = "EnterpriseInfo")
public class EnterpriseInfoDTO {

    @NotBlank
    String name;
    @NotBlank
    String city;

}
