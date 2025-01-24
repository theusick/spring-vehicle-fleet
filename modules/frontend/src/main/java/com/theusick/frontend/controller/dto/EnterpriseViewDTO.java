package com.theusick.frontend.controller.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Hidden
@Builder
@Jacksonized
public class EnterpriseViewDTO {

    private Long id;

    private String name;
    private String city;

}
