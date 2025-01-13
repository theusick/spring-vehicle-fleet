package com.theusick.controller;

import com.theusick.api.exception.NotFoundApiException;
import com.theusick.controller.dto.driver.ActiveDriverDTO;
import com.theusick.controller.dto.driver.DriverBaseDTO;
import com.theusick.controller.dto.enterprise.EnterpriseBaseDTO;
import com.theusick.controller.dto.enterprise.EnterpriseInfoDTO;
import com.theusick.controller.dto.vehicle.VehicleBaseDTO;
import com.theusick.controller.dto.vehicle.VehicleInfoDTO;
import com.theusick.security.repository.entity.Manager;
import com.theusick.service.DriverService;
import com.theusick.service.EnterpriseService;
import com.theusick.service.VehicleService;
import com.theusick.service.exception.NoSuchException;
import com.theusick.service.mapper.DriverMapper;
import com.theusick.service.mapper.EnterpriseMapper;
import com.theusick.service.mapper.VehicleMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
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
import java.util.Objects;

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

    @GetMapping(value = "/{enterpriseId}/vehicles", produces = {"application/json"})
    @PreAuthorize("hasRole('MANAGER')")
    public List<VehicleBaseDTO> getEnterpriseVehicles(@AuthenticationPrincipal Manager manager,
                                                      @PathVariable Long enterpriseId) {
        try {
            return vehicleService.getEnterpriseVehiclesForManager(manager.getId(), enterpriseId).stream()
                .map(vehicleMapper::vehicleBaseDTOFromModel)
                .toList();
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @PostMapping(value = "/{enterpriseId}/vehicles", produces = {"application/json"})
    @PreAuthorize("hasRole('MANAGER')")
    public VehicleBaseDTO createEnterpriseVehicle(@PathVariable Long enterpriseId,
                                                  @RequestBody VehicleInfoDTO vehicleDTO,
                                                  @AuthenticationPrincipal Manager manager) {
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
        }
    }

    @PutMapping(value = "/{enterpriseId}/vehicles/{vehicleId}", produces = {"application/json"})
    @PreAuthorize("hasRole('MANAGER')")
    public VehicleBaseDTO updateEnterpriseVehicle(@PathVariable Long enterpriseId,
                                                  @PathVariable Long vehicleId,
                                                  @RequestBody VehicleInfoDTO vehicleDTO,
                                                  @AuthenticationPrincipal Manager manager) {
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
        }
    }

    @DeleteMapping(value = "/{enterpriseId}/vehicles/{vehicleId}", produces = {"application/json"})
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEnterpriseVehicle(@PathVariable Long enterpriseId,
                                        @PathVariable Long vehicleId,
                                        @AuthenticationPrincipal Manager manager) {
        try {
            vehicleService.deleteVehicleForManager(enterpriseId, vehicleId, manager.getId());
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @GetMapping(value = "/{enterpriseId}/drivers", produces = {"application/json"})
    @PreAuthorize("hasRole('MANAGER')")
    public List<DriverBaseDTO> getEnterpriseDrivers(@AuthenticationPrincipal Manager manager,
                                                    @PathVariable Long enterpriseId) {
        try {
            return driverService.getEnterpriseDriversForManager(manager.getId(), enterpriseId).stream()
                .map(driverMapper::driverBaseDTOFromModel)
                .toList();
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @GetMapping(value = "/{enterpriseId}/drivers/active", produces = {"application/json"})
    @PreAuthorize("hasRole('MANAGER')")
    public List<ActiveDriverDTO> getEnterpriseActiveDrivers(@AuthenticationPrincipal Manager manager,
                                                            @PathVariable Long enterpriseId) {
        try {
            return driverService.getEnterpriseDriversForManager(manager.getId(), enterpriseId).stream()
                .map(driverMapper::activeDriverDTOFromModel)
                .filter(activeDriverDTO -> Objects.nonNull(activeDriverDTO.getVehicle()))
                .toList();
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @PostMapping(value = "/{enterpriseId}", produces = {"application/json"})
    @PreAuthorize("hasRole('MANAGER')")
    public EnterpriseBaseDTO getEnterprise(@PathVariable Long enterpriseId,
                                           @AuthenticationPrincipal Manager manager) {
        try {
            return enterpriseMapper.enterpriseBaseDTOFromModel(
                enterpriseService.getEnterpriseForManager(enterpriseId, manager.getId()));
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @GetMapping(produces = {"application/json"})
    @PreAuthorize("hasRole('MANAGER')")
    public List<EnterpriseBaseDTO> getEnterprises(@AuthenticationPrincipal Manager manager) {
        return enterpriseService.getEnterprisesForManager(manager.getId()).stream()
            .map(enterpriseMapper::enterpriseBaseDTOFromModel)
            .toList();
    }

    @PostMapping(produces = {"application/json"})
    @PreAuthorize("hasRole('MANAGER')")
    public EnterpriseBaseDTO createEnterprise(@RequestBody EnterpriseInfoDTO enterpriseDTO,
                                              @AuthenticationPrincipal Manager manager) {
        try {
            return enterpriseMapper.enterpriseBaseDTOFromModel(
                enterpriseService.createEnterprise(
                    enterpriseMapper.enterpriseModelFromInfoDTO(enterpriseDTO), manager.getId())
            );
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @PutMapping(value = "/{enterpriseId}", produces = {"application/json"})
    @PreAuthorize("hasRole('MANAGER')")
    public EnterpriseBaseDTO updateEnterprise(@PathVariable Long enterpriseId,
                                              @RequestBody EnterpriseInfoDTO enterpriseDTO,
                                              @AuthenticationPrincipal Manager manager) {
        try {
            return enterpriseMapper.enterpriseBaseDTOFromModel(
                enterpriseService.updateEnterprise(
                    enterpriseMapper.enterpriseModelFromInfoDTO(enterpriseDTO).withId(enterpriseId),
                    manager.getId())
            );
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @DeleteMapping(value = "/{enterpriseId}", produces = {"application/json"})
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEnterprise(@PathVariable Long enterpriseId,
                                 @AuthenticationPrincipal Manager manager) {
        try {
            enterpriseService.deleteEnterprise(enterpriseId, manager.getId());
        } catch (NoSuchException exception) {
            throw new NotFoundApiException(exception.getMessage());
        }
    }

    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken csrfToken) {
        return csrfToken;
    }

}
