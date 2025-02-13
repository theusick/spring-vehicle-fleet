package com.theusick.fleet.controller;

import com.theusick.core.api.exception.ForbiddenApiException;
import com.theusick.core.security.repository.entity.User;
import com.theusick.core.service.exception.NoAccessException;
import com.theusick.fleet.controller.dto.driver.ActiveDriverDTO;
import com.theusick.fleet.controller.dto.driver.DriverBaseDTO;
import com.theusick.fleet.service.DriverService;
import com.theusick.fleet.service.mapper.DriverMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Enterprise Drivers")
@RequestMapping("/api/v1/enterprises/{enterpriseId}/drivers")
@RequiredArgsConstructor
public class EnterpriseDriverController {

    private final DriverService driverService;
    private final DriverMapper driverMapper;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
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

    @GetMapping(value = "/active", produces = {MediaType.APPLICATION_JSON_VALUE})
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

}
