package com.theusick.fleet.controller.dto.driver;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@Schema(name = "ActiveDriver")
public class ActiveDriverDTO {

    @NotNull
    Long id;

    @NotBlank
    String name;
    int age;
    double salary;

    @NotNull
    @JsonProperty("vehicle")
    Long activeVehicleId;

}
