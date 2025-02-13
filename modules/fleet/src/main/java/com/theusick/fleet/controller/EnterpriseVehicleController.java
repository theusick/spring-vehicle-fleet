package com.theusick.fleet.controller;

import com.theusick.core.api.exception.ForbiddenApiException;
import com.theusick.core.api.exception.NotFoundApiException;
import com.theusick.core.security.repository.entity.User;
import com.theusick.core.service.exception.NoAccessException;
import com.theusick.core.service.exception.NoSuchException;
import com.theusick.fleet.controller.dto.vehicle.VehicleBaseDTO;
import com.theusick.fleet.controller.dto.vehicle.VehicleInfoDTO;
import com.theusick.fleet.service.VehicleService;
import com.theusick.fleet.service.mapper.VehicleMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Enterprise Vehicles")
@RequestMapping("/api/v1/enterprises/{enterpriseId}/vehicles")
@RequiredArgsConstructor
public class EnterpriseVehicleController {

    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('MANAGER')")
    public Page<VehicleBaseDTO> getEnterpriseVehicles(@AuthenticationPrincipal User manager,
                                                      @NotNull @PathVariable Long enterpriseId,
                                                      @NotNull Pageable pageable) {
        try {
            return vehicleMapper.vehicleDTOPageFromModels(
                vehicleService.getEnterpriseVehiclesPageForManager(manager.getId(), enterpriseId, pageable)
            );
        } catch (NoAccessException exception) {
            throw new ForbiddenApiException(exception.getMessage());
        }
    }

    @PostMapping(
        produces = {MediaType.APPLICATION_JSON_VALUE},
        consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleBaseDTO createEnterpriseVehicle(@PathVariable Long enterpriseId,
                                                  @Valid @RequestBody VehicleInfoDTO vehicleDTO,
                                                  @AuthenticationPrincipal User manager) {
        try {
            return vehicleMapper.vehicleBaseDTOFromModel(
                vehicleService.createVehicleForManager(
                    enterpriseId,
                    vehicleDTO.getBrand().getId(),
                    manager.getId(),
                    vehicleMapper.vehicleModelFromInfoDTO(vehicleDTO))
            );
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        } catch (NoAccessException exception) {
            throw new ForbiddenApiException(exception.getMessage());
        }
    }

    @PutMapping(
        value = "/{vehicleId}",
        produces = {MediaType.APPLICATION_JSON_VALUE},
        consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("hasRole('MANAGER')")
    public VehicleBaseDTO updateEnterpriseVehicle(@PathVariable Long enterpriseId,
                                                  @PathVariable Long vehicleId,
                                                  @Valid @RequestBody VehicleInfoDTO vehicleDTO,
                                                  @AuthenticationPrincipal User manager) {
        try {
            return vehicleMapper.vehicleBaseDTOFromModel(
                vehicleService.updateVehicleForManager(
                    enterpriseId,
                    null,
                    manager.getId(),
                    vehicleMapper.vehicleModelFromInfoDTO(vehicleDTO).withId(vehicleId))
            );
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        } catch (NoAccessException exception) {
            throw new ForbiddenApiException(exception.getMessage());
        }
    }

    @DeleteMapping(value = "/{vehicleId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEnterpriseVehicle(@PathVariable Long enterpriseId,
                                        @PathVariable Long vehicleId,
                                        @AuthenticationPrincipal User manager) {
        try {
            vehicleService.deleteVehicleForManager(enterpriseId, manager.getId(), vehicleId);
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        } catch (NoAccessException exception) {
            throw new ForbiddenApiException(exception.getMessage());
        }
    }

}
