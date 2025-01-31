package com.theusick.fleet.controller;


import com.theusick.fleet.controller.dto.vehicle.VehicleBaseDTO;
import com.theusick.fleet.service.VehicleService;
import com.theusick.fleet.service.mapper.VehicleMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Vehicle")
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleApiController {

    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    @GetMapping(produces = {"application/json"})
    public List<VehicleBaseDTO> getVehicles() {
        return vehicleService.getVehicles()
            .stream()
            .map(vehicleMapper::vehicleBaseDTOFromModel)
            .toList();
    }

}
