package com.theusick.fleet.controller.dto.vehicle;


import com.theusick.fleet.controller.dto.brand.VehicleBrandIdDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;

@Value
@Builder
@Jacksonized
@Schema(name = "VehicleInfo")
public class VehicleInfoDTO {

    @PositiveOrZero
    int year;
    @PositiveOrZero
    int mileage;
    @NotBlank
    String color;
    @DecimalMin(
        value = "0.0",
        inclusive = false,
        message = "Price must be greater than 0"
    )
    double price;
    @NotBlank
    String licensePlate;
    @NotNull
    OffsetDateTime purchaseDate;

    @Valid
    @NotNull
    VehicleBrandIdDTO brand;

}
