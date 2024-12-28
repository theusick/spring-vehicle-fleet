package com.theusick.controller.dto.driver;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
@Schema(description = "Driver")
public class ActiveDriverDTO {

    private Long id;

    private String name;
    private int age;
    private double salary;

    private Long vehicle;

}
