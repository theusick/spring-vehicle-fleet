package com.theusick.controller.dto.driver;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@Schema(description = "Driver")
public class EnterpriseDriverDTO {

    @JsonUnwrapped
    private DriverBaseDTO driver;
    private Long enterpriseId;

}