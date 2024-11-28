package com.theusick.controller;


import com.theusick.service.VehicleService;
import com.theusick.service.model.VehicleModel;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Vehicle")
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleApiController {

    private final VehicleService vehicleService;

    @GetMapping
    public List<VehicleModel> getVehicles() {
        return vehicleService.getVehicles();
    }

}
