package com.theusick.fleet.service.impl;

import com.theusick.core.service.exception.NoAccessException;
import com.theusick.core.service.exception.NoSuchException;
import com.theusick.fleet.repository.EnterpriseRepository;
import com.theusick.fleet.repository.VehicleBrandRepository;
import com.theusick.fleet.repository.VehicleRepository;
import com.theusick.fleet.repository.entity.EnterpriseEntity;
import com.theusick.fleet.repository.entity.VehicleBrandEntity;
import com.theusick.fleet.repository.entity.VehicleEntity;
import com.theusick.fleet.service.EnterpriseService;
import com.theusick.fleet.service.VehicleService;
import com.theusick.fleet.service.exception.NoSuchEnterpriseException;
import com.theusick.fleet.service.exception.NoSuchVehicleBrandException;
import com.theusick.fleet.service.exception.NoSuchVehicleException;
import com.theusick.fleet.service.mapper.VehicleMapper;
import com.theusick.fleet.service.model.VehicleModel;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    private final EnterpriseRepository enterpriseRepository;
    private final EnterpriseService enterpriseService;

    private final VehicleBrandRepository brandRepository;

    @Override
    public VehicleModel getVehicle(Long vehicleId) throws NoSuchVehicleException {
        return vehicleMapper.vehicleModelFromEntity(vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new NoSuchVehicleException(vehicleId)));
    }

    @Override
    public List<VehicleModel> getVehicles() {
        return vehicleRepository.findAll().stream()
            .map(vehicleMapper::vehicleModelFromEntity)
            .toList();
    }

    @Override
    @Transactional
    public List<VehicleModel> getEnterpriseVehicles(Long enterpriseId) throws NoSuchEnterpriseException {
        EnterpriseEntity enterpriseEntity = enterpriseRepository.findById(enterpriseId)
            .orElseThrow(() -> new NoSuchEnterpriseException(enterpriseId));
        return enterpriseEntity.getVehicles().stream()
            .map(vehicleMapper::vehicleModelFromEntity)
            .toList();
    }

    @Override
    public Page<VehicleModel> getEnterpriseVehiclesPageForManager(Long managerId,
                                                                  Long enterpriseId,
                                                                  Pageable pageable)
        throws NoAccessException {
        return enterpriseService.getVisiblePageEntitiesForManager(
            managerId,
            enterpriseId,
            vehicleRepository::findByEnterpriseId,
            vehicleMapper::vehicleModelsPageFromEntities,
            pageable
        );
    }

    @Override
    public List<VehicleModel> getEnterpriseVehiclesForManager(Long managerId,
                                                              Long enterpriseId) throws NoAccessException {
        return enterpriseService.getVisibleEntitiesForManager(
            managerId,
            enterpriseId,
            vehicleRepository::findByEnterpriseId,
            vehicleMapper::vehicleModelFromEntity
        );
    }

    @Override
    @Transactional
    public VehicleModel createVehicle(Long enterpriseId,
                                      Long brandId,
                                      VehicleModel vehicleModel)
        throws NoSuchEnterpriseException, NoSuchVehicleBrandException {
        EnterpriseEntity enterpriseEntity = enterpriseRepository.findById(enterpriseId)
            .orElseThrow(() -> new NoSuchEnterpriseException(enterpriseId));

        return createVehicle(brandId, vehicleModel, enterpriseEntity);
    }

    @Override
    @Transactional
    public VehicleModel createVehicleForManager(Long enterpriseId,
                                                Long brandId,
                                                Long managerId,
                                                VehicleModel vehicleModel) throws NoSuchException, NoAccessException {
        EnterpriseEntity enterpriseEntity =
            enterpriseService.verifyManagerAccess(enterpriseId, managerId);

        return createVehicle(brandId, vehicleModel, enterpriseEntity);
    }

    private VehicleModel createVehicle(Long brandId,
                                       VehicleModel vehicleModel,
                                       EnterpriseEntity enterpriseEntity) throws NoSuchVehicleBrandException {
        VehicleBrandEntity brandEntity = brandRepository.findById(brandId)
            .orElseThrow(() -> new NoSuchVehicleBrandException(brandId));

        VehicleEntity vehicleEntity = VehicleEntity.builder()
            .enterprise(enterpriseEntity)
            .brand(brandEntity)
            .build();

        vehicleMapper.updateVehicleEntityFromModel(vehicleEntity, vehicleModel);
        vehicleRepository.save(vehicleEntity);

        return vehicleMapper.vehicleModelFromEntity(vehicleEntity);
    }

    @Override
    @Transactional
    public void updateVehicle(VehicleModel vehicleModel) throws NoSuchException {
        VehicleEntity vehicleEntity = vehicleRepository.findById(vehicleModel.getId())
            .orElseThrow(() -> new NoSuchVehicleException(vehicleModel.getId()));

        final Long newEnterpriseId = vehicleModel.getEnterpriseId();
        if (!Objects.equals(newEnterpriseId, vehicleEntity.getEnterprise().getId())) {
            EnterpriseEntity enterpriseEntity = enterpriseRepository.findById(newEnterpriseId)
                .orElseThrow(() -> new NoSuchEnterpriseException(newEnterpriseId));

            vehicleEntity.setEnterprise(enterpriseEntity);
        }

        vehicleMapper.updateVehicleEntityFromModel(vehicleEntity, vehicleModel);
        vehicleRepository.save(vehicleEntity);
    }

    @Override
    @Transactional
    public VehicleModel updateVehicleForManager(Long currentEnterpriseId,
                                                Long newEnterpriseId,
                                                Long managerId,
                                                VehicleModel vehicleModel) throws NoSuchException, NoAccessException {
        enterpriseService.verifyManagerAccess(currentEnterpriseId, managerId);

        VehicleEntity vehicleEntity =
            findEnterpriseVehicleOrThrow(currentEnterpriseId, vehicleModel.getId());
        updateEnterpriseIfChanged(vehicleEntity, managerId, newEnterpriseId);
        updateBrandIfChanged(vehicleEntity, vehicleModel.getBrandId());

        vehicleMapper.updateVehicleEntityFromModel(vehicleEntity, vehicleModel);
        vehicleRepository.save(vehicleEntity);

        return vehicleMapper.vehicleModelFromEntity(vehicleEntity);
    }

    @Override
    public void deleteVehicle(Long vehicleId) throws NoSuchVehicleException {
        VehicleEntity vehicleEntity = vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new NoSuchVehicleException(vehicleId));
        vehicleRepository.delete(vehicleEntity);
    }

    @Override
    @Transactional
    public void deleteVehicleForManager(Long enterpriseId,
                                        Long managerId,
                                        Long vehicleId) throws NoSuchException, NoAccessException {
        enterpriseService.verifyManagerAccess(enterpriseId, managerId);

        VehicleEntity vehicleEntity = findEnterpriseVehicleOrThrow(enterpriseId, vehicleId);
        vehicleRepository.delete(vehicleEntity);
    }

    private VehicleEntity findEnterpriseVehicleOrThrow(Long enterpriseId,
                                                       Long vehicleId) throws NoSuchVehicleException {
        return vehicleRepository.findByIdAndEnterpriseId(vehicleId, enterpriseId)
            .orElseThrow(() -> new NoSuchVehicleException(vehicleId));
    }

    private void updateEnterpriseIfChanged(@NotNull VehicleEntity vehicleEntity,
                                           @NotNull Long managerId,
                                           Long newEnterpriseId) throws NoSuchEnterpriseException {
        if (!Objects.equals(vehicleEntity.getEnterprise().getId(), newEnterpriseId)) {
            EnterpriseEntity enterpriseEntity =
                enterpriseRepository.findByIdAndManagersId(newEnterpriseId, managerId)
                    .orElseThrow(() -> new NoSuchEnterpriseException(newEnterpriseId));
            vehicleEntity.setEnterprise(enterpriseEntity);
        }
    }

    private void updateBrandIfChanged(@NotNull VehicleEntity vehicleEntity,
                                      Long newBrandId) throws NoSuchVehicleBrandException {
        if (!Objects.equals(vehicleEntity.getBrand().getId(), newBrandId)) {
            VehicleBrandEntity brandEntity = brandRepository.findById(newBrandId)
                .orElseThrow(() -> new NoSuchVehicleBrandException(newBrandId));
            vehicleEntity.setBrand(brandEntity);
        }
    }

}
