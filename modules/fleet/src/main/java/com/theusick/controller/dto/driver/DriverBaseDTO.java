package com.theusick.controller.dto.driver;

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
@Schema(description = "Driver")
public class DriverBaseDTO {

    @NotNull
    Long id;

    @NotBlank
    String name;
    int age;
    @NotBlank
    double salary;

    @NotNull
    List<Long> vehicles;

}
