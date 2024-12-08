package com.theusick.controller.dto.driver;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@Schema(description = "Driver")
public class DriverBaseDTO {

    private Long id;

    private String name;
    private int age;
    private double salary;

    private Long vehicleId;

    private boolean active;

}
