package com.theusick.controller.dto.enterprise;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@Schema(name = "EnterpriseInfo")
public class EnterpriseInfoDTO {

    private String name;
    private String city;

}
