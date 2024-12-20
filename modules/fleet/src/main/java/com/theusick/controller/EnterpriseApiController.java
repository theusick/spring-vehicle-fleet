package com.theusick.controller;

import com.theusick.api.exception.NotFoundApiException;
import com.theusick.controller.dto.driver.DriverBaseDTO;
import com.theusick.controller.dto.enterprise.EnterpriseBaseDTO;
import com.theusick.controller.dto.vehicle.VehicleBaseDTO;
import com.theusick.service.DriverService;
import com.theusick.service.EnterpriseService;
import com.theusick.service.VehicleService;
import com.theusick.service.exception.NoSuchException;
import com.theusick.service.mapper.DriverMapper;
import com.theusick.service.mapper.EnterpriseMapper;
import com.theusick.service.mapper.VehicleMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Enterprise")
@RequestMapping("/api/enterprises")
@RequiredArgsConstructor
public class EnterpriseApiController {

    private final EnterpriseService enterpriseService;
    private final EnterpriseMapper enterpriseMapper;

    private final DriverService driverService;
    private final DriverMapper driverMapper;

    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    @GetMapping(produces = {"application/json"})
    public List<EnterpriseBaseDTO> getEnterprises() {
        return enterpriseService.getEnterprises().stream()
            .map(enterpriseMapper::enterpriseBaseDTOFromModel)
            .toList();
    }

    @GetMapping(value = "/{enterpriseId}/vehicles", produces = {"application/json"})
    public List<VehicleBaseDTO> getEnterpriseVehicles(@PathVariable Long enterpriseId) {
        try {
            return vehicleService.getEnterpriseVehicles(enterpriseId).stream()
                .map(vehicleMapper::vehicleBaseDTOFromModel)
                .toList();
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @GetMapping(value = "/{enterpriseId}/drivers", produces = {"application/json"})
    public List<DriverBaseDTO> getEnterpriseDrivers(@PathVariable Long enterpriseId) {
        try {
            return driverService.getEnterpriseDrivers(enterpriseId).stream()
                .map(driverMapper::driverBaseDTOFromModel)
                .toList();
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

}
