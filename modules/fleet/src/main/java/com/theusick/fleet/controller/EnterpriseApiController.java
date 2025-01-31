package com.theusick.fleet.controller;

import com.theusick.core.api.exception.ForbiddenApiException;
import com.theusick.core.api.exception.NotFoundApiException;
import com.theusick.core.security.repository.entity.User;
import com.theusick.core.service.exception.NoAccessException;
import com.theusick.core.service.exception.NoSuchException;
import com.theusick.fleet.controller.dto.driver.ActiveDriverDTO;
import com.theusick.fleet.controller.dto.driver.DriverBaseDTO;
import com.theusick.fleet.controller.dto.enterprise.EnterpriseBaseDTO;
import com.theusick.fleet.controller.dto.enterprise.EnterpriseInfoDTO;
import com.theusick.fleet.controller.dto.vehicle.VehicleBaseDTO;
import com.theusick.fleet.controller.dto.vehicle.VehicleInfoDTO;
import com.theusick.fleet.service.DriverService;
import com.theusick.fleet.service.EnterpriseService;
import com.theusick.fleet.service.VehicleService;
import com.theusick.fleet.service.mapper.DriverMapper;
import com.theusick.fleet.service.mapper.EnterpriseMapper;
import com.theusick.fleet.service.mapper.VehicleMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

import java.util.List;

@RestController
@Tag(name = "Enterprise")
@RequestMapping("/api/v1/enterprises")
@RequiredArgsConstructor
public class EnterpriseApiController {

    private final EnterpriseService enterpriseService;
    private final EnterpriseMapper enterpriseMapper;

    private final DriverService driverService;
    private final DriverMapper driverMapper;

    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    @GetMapping(value = "/{enterpriseId}/vehicles", produces = {"application/json"})
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
        value = "/{enterpriseId}/vehicles",
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleBaseDTO createEnterpriseVehicle(@PathVariable Long enterpriseId,
                                                  @RequestBody @Valid VehicleInfoDTO vehicleDTO,
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
        value = "/{enterpriseId}/vehicles/{vehicleId}",
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    @PreAuthorize("hasRole('MANAGER')")
    public VehicleBaseDTO updateEnterpriseVehicle(@PathVariable Long enterpriseId,
                                                  @PathVariable Long vehicleId,
                                                  @RequestBody @Valid VehicleInfoDTO vehicleDTO,
                                                  @AuthenticationPrincipal User manager) {
        try {
            return vehicleMapper.vehicleBaseDTOFromModel(
                vehicleService.updateVehicleForManager(
                    enterpriseId,
                    vehicleDTO.getBrand().getId(),
                    manager.getId(),
                    vehicleMapper.vehicleModelFromInfoDTO(vehicleDTO).withId(vehicleId))
            );
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        } catch (NoAccessException exception) {
            throw new ForbiddenApiException(exception.getMessage());
        }
    }

    @DeleteMapping(value = "/{enterpriseId}/vehicles/{vehicleId}", produces = {"application/json"})
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEnterpriseVehicle(@PathVariable Long enterpriseId,
                                        @PathVariable Long vehicleId,
                                        @AuthenticationPrincipal User manager) {
        try {
            vehicleService.deleteVehicleForManager(enterpriseId, vehicleId, manager.getId());
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        } catch (NoAccessException exception) {
            throw new ForbiddenApiException(exception.getMessage());
        }
    }

    @GetMapping(value = "/{enterpriseId}/drivers", produces = {"application/json"})
    @PreAuthorize("hasRole('MANAGER')")
    public Page<DriverBaseDTO> getEnterpriseDrivers(@AuthenticationPrincipal User manager,
                                                    @PathVariable Long enterpriseId,
                                                    @NotNull Pageable pageable) {
        try {
            return driverMapper.driverDTOPageFromModels(
                driverService.getEnterpriseDriversPageForManager(manager.getId(), enterpriseId, pageable)
            );
        } catch (NoAccessException exception) {
            throw new ForbiddenApiException(exception.getMessage());
        }
    }

    @GetMapping(value = "/{enterpriseId}/drivers/active", produces = {"application/json"})
    @PreAuthorize("hasRole('MANAGER')")
    public Page<ActiveDriverDTO> getEnterpriseActiveDrivers(@AuthenticationPrincipal User manager,
                                                            @PathVariable Long enterpriseId,
                                                            @NotNull @PageableDefault(size = 20)
                                                            Pageable pageable) {
        try {
            return driverMapper.activeDiverDTOPageFromModels(
                driverService.getEnterpriseActiveDriversPageForManager(
                    manager.getId(),
                    enterpriseId,
                    pageable
                ));
        } catch (NoAccessException exception) {
            throw new ForbiddenApiException(exception.getMessage());
        }
    }

    @PostMapping(value = "/{enterpriseId}", produces = {"application/json"})
    @PreAuthorize("hasRole('MANAGER')")
    public EnterpriseBaseDTO getEnterprise(@PathVariable Long enterpriseId,
                                           @AuthenticationPrincipal User manager) {
        try {
            return enterpriseMapper.enterpriseBaseDTOFromModel(
                enterpriseService.getEnterpriseForManager(enterpriseId, manager.getId()));
        } catch (NoAccessException exception) {
            throw new ForbiddenApiException(exception.getMessage());
        }
    }

    @GetMapping(produces = {"application/json"})
    @PreAuthorize("hasRole('MANAGER')")
    public List<EnterpriseBaseDTO> getEnterprises(@AuthenticationPrincipal User manager) {
        return enterpriseService.getEnterprisesForManager(manager.getId()).stream()
            .map(enterpriseMapper::enterpriseBaseDTOFromModel)
            .toList();
    }

    @PostMapping(
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    public EnterpriseBaseDTO createEnterprise(@RequestBody @Valid EnterpriseInfoDTO enterpriseDTO,
                                              @AuthenticationPrincipal User manager) {
        try {
            return enterpriseMapper.enterpriseBaseDTOFromModel(
                enterpriseService.createEnterpriseForManager(
                    enterpriseMapper.enterpriseModelFromInfoDTO(enterpriseDTO), manager.getId())
            );
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @PutMapping(
        value = "/{enterpriseId}",
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    @PreAuthorize("hasRole('MANAGER')")
    public EnterpriseBaseDTO updateEnterprise(@PathVariable Long enterpriseId,
                                              @RequestBody @Valid EnterpriseInfoDTO enterpriseDTO,
                                              @AuthenticationPrincipal User manager) {
        try {
            return enterpriseMapper.enterpriseBaseDTOFromModel(
                enterpriseService.updateEnterpriseForManager(
                    enterpriseMapper.enterpriseModelFromInfoDTO(enterpriseDTO).withId(enterpriseId),
                    manager.getId())
            );
        } catch (NoAccessException exception) {
            throw new ForbiddenApiException(exception.getMessage());
        }
    }

    @DeleteMapping(value = "/{enterpriseId}", produces = {"application/json"})
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEnterprise(@PathVariable Long enterpriseId,
                                 @AuthenticationPrincipal User manager) {
        try {
            enterpriseService.deleteEnterpriseForManager(enterpriseId, manager.getId());
        } catch (NoAccessException exception) {
            throw new ForbiddenApiException(exception.getMessage());
        }
    }

}
