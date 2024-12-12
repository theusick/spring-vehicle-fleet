package com.theusick.controller;

import com.theusick.controller.dto.brand.VehicleBrandBaseDTO;
import com.theusick.service.VehicleBrandService;
import com.theusick.service.mapper.VehicleBrandMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Vehicle Brand")
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class VehicleBrandApiController {

    private final VehicleBrandService vehicleBrandService;
    private final VehicleBrandMapper vehicleBrandMapper;

    @GetMapping(produces = {"application/json"})
    public List<VehicleBrandBaseDTO> getVehicleBrands() {
        return vehicleBrandService.getVehicleBrands()
            .stream()
            .map(vehicleBrandMapper::vehicleBrandBaseDTOFromModel)
            .toList();
    }

}
