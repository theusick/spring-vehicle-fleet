package com.theusick.controller;

import com.theusick.api.exception.NotFoundApiException;
import com.theusick.controller.dto.VehicleDTO;
import com.theusick.service.DriverService;
import com.theusick.service.EnterpriseService;
import com.theusick.service.VehicleService;
import com.theusick.service.exception.NoSuchException;
import com.theusick.service.mapper.VehicleMapper;
import com.theusick.service.model.DriverModel;
import com.theusick.service.model.EnterpriseModel;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Enterprise")
@RequestMapping("/api/enterprises")
@RequiredArgsConstructor
public class EnterpriseApiController {

    private final EnterpriseService enterpriseService;
    private final DriverService driverService;

    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    @GetMapping(produces = {"application/json"})
    public List<EnterpriseModel> getEnterprises() {
        return enterpriseService.getEnterprises();
    }

    @GetMapping(value = "/{enterpriseId}/vehicles", produces = {"application/json"})
    public List<VehicleDTO> getEnterpriseVehicles(@PathVariable Long enterpriseId) {
        try {
            return vehicleService.getEnterpriseVehicles(enterpriseId).stream()
                .map(vehicleMapper::vehicleDTOFromModel)
                .collect(Collectors.toList());
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @GetMapping(value = "/{enterpriseId}/drivers", produces = {"application/json"})
    public List<DriverModel> getEnterpriseDrivers(@PathVariable Long enterpriseId) {
        try {
            return driverService.getEnterpriseDrivers(enterpriseId);
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

}
