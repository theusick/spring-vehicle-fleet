package com.theusick.fleet.controller;


import com.theusick.core.security.repository.entity.User;
import com.theusick.fleet.controller.dto.brand.VehicleBrandBaseDTO;
import com.theusick.fleet.controller.dto.vehicle.VehicleBaseDTO;
import com.theusick.fleet.controller.dto.vehicletelemetry.VehicleTelemetryFeatureCollection;
import com.theusick.fleet.service.VehicleBrandService;
import com.theusick.fleet.service.VehicleService;
import com.theusick.fleet.service.VehicleTelemetryService;
import com.theusick.fleet.service.mapper.VehicleBrandMapper;
import com.theusick.fleet.service.mapper.VehicleMapper;
import com.theusick.fleet.service.mapper.VehicleTelemetryMapper;
import com.theusick.fleet.service.model.VehicleTelemetryModel;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@Tag(name = "Vehicle")
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    private final VehicleBrandService vehicleBrandService;
    private final VehicleBrandMapper vehicleBrandMapper;

    private final VehicleTelemetryService vehicleTelemetryService;
    private final VehicleTelemetryMapper vehicleTelemetryMapper;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<VehicleBaseDTO> getVehicles() {
        return vehicleService.getVehicles()
            .stream()
            .map(vehicleMapper::vehicleBaseDTOFromModel)
            .toList();
    }

    @GetMapping(value = "/brands", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public List<VehicleBrandBaseDTO> getVehicleBrands(@AuthenticationPrincipal User user) {
        return vehicleBrandService.getVehicleBrands()
            .stream()
            .map(vehicleBrandMapper::vehicleBrandBaseDTOFromModel)
            .toList();
    }

    @GetMapping(
        value = "/{vehicleId}",
        produces = {MediaType.APPLICATION_JSON_VALUE, "application/geo+json"}
    )
    @PreAuthorize("hasRole('MANAGER')")
    public Object getVehicleTelemetry(@PathVariable Long vehicleId,
                                      @RequestParam OffsetDateTime start,
                                      @RequestParam OffsetDateTime end,
                                      @RequestParam(defaultValue = "json") String format) {
        List<VehicleTelemetryModel> vehicleTelemetry =
            vehicleTelemetryService.getVehicleTelemetry(vehicleId, start, end);

        if ("geojson".equalsIgnoreCase(format)) {
            return VehicleTelemetryFeatureCollection.builder()
                .features(vehicleTelemetry.stream()
                    .map(vehicleTelemetryMapper::telemetryModelToFeature)
                    .toList())
                .build();
        }

        return vehicleTelemetry.stream()
            .map(vehicleTelemetryMapper::telemetryBaseDTOFromModel)
            .toList();
    }

}
