package com.theusick.fleet.controller.dto.enterprise;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@Schema(name = "EnterpriseResponse")
public class EnterpriseBaseDTO {

    @NotNull
    Long id;
    @NotBlank
    String name;
    @NotBlank
    String city;

    @NotNull
    List<Long> vehicles;
    @NotNull
    List<Long> drivers;

}
