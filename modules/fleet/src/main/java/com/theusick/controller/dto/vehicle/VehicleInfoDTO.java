package com.theusick.controller.dto.vehicle;


import com.theusick.controller.dto.brand.VehicleBrandIdDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@Schema(name = "VehicleInfo")
public class VehicleInfoDTO {

    private int year;
    private int mileage;
    private String color;
    private double price;
    private String licensePlate;

    private VehicleBrandIdDTO brand;

}
