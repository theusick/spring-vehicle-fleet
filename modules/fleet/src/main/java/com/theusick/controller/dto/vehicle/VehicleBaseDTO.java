package com.theusick.controller.dto.vehicle;

import com.theusick.controller.dto.brand.VehicleBrandBaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
@Schema(description = "Vehicle")
public class VehicleBaseDTO {

    private Long id;

    private int year;
    private int mileage;
    private String color;
    private double price;
    private String licensePlate;

    private VehicleBrandBaseDTO brand;

    private List<VehicleDriverWithoutVehicleIdDTO> drivers;

}
