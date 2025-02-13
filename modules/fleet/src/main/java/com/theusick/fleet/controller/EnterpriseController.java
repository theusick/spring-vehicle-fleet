package com.theusick.fleet.controller;

import com.theusick.core.api.exception.ForbiddenApiException;
import com.theusick.core.api.exception.NotFoundApiException;
import com.theusick.core.security.repository.entity.User;
import com.theusick.core.service.exception.NoAccessException;
import com.theusick.core.service.exception.NoSuchException;
import com.theusick.fleet.controller.dto.enterprise.EnterpriseBaseDTO;
import com.theusick.fleet.controller.dto.enterprise.EnterpriseInfoDTO;
import com.theusick.fleet.service.EnterpriseService;
import com.theusick.fleet.service.mapper.EnterpriseMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

import java.util.List;

@RestController
@Tag(name = "Enterprise")
@RequestMapping("/api/v1/enterprises")
@RequiredArgsConstructor
public class EnterpriseController {

    private final EnterpriseService enterpriseService;
    private final EnterpriseMapper enterpriseMapper;

    @GetMapping(value = "/{enterpriseId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('MANAGER')")
    public EnterpriseInfoDTO getEnterprise(@AuthenticationPrincipal User manager,
                                           @PathVariable Long enterpriseId) {
        try {
            return enterpriseMapper.enterpriseInfoDTOFromModel(
                enterpriseService.getEnterpriseForManager(manager.getId(), enterpriseId));
        } catch (NoAccessException exception) {
            throw new ForbiddenApiException(exception.getMessage());
        }
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('MANAGER')")
    public List<EnterpriseBaseDTO> getEnterprises(@AuthenticationPrincipal User manager) {
        return enterpriseService.getEnterprisesForManager(manager.getId()).stream()
            .map(enterpriseMapper::enterpriseBaseDTOFromModel)
            .toList();
    }

    @PostMapping(
        produces = {MediaType.APPLICATION_JSON_VALUE},
        consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    public EnterpriseBaseDTO createEnterprise(@Valid @RequestBody EnterpriseInfoDTO enterpriseDTO,
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
        produces = {MediaType.APPLICATION_JSON_VALUE},
        consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("hasRole('MANAGER')")
    public EnterpriseBaseDTO updateEnterprise(@PathVariable Long enterpriseId,
                                              @Valid @RequestBody EnterpriseInfoDTO enterpriseDTO,
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

    @DeleteMapping(value = "/{enterpriseId}", produces = {MediaType.APPLICATION_JSON_VALUE})
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
