package com.theusick.fleet.service.impl;

import com.theusick.core.service.exception.NoAccessException;
import com.theusick.fleet.repository.DriverRepository;
import com.theusick.fleet.repository.EnterpriseRepository;
import com.theusick.fleet.repository.VehicleRepository;
import com.theusick.fleet.repository.entity.DriverEntity;
import com.theusick.fleet.repository.entity.EnterpriseEntity;
import com.theusick.fleet.repository.entity.VehicleEntity;
import com.theusick.fleet.service.DriverService;
import com.theusick.fleet.service.EnterpriseService;
import com.theusick.fleet.service.exception.NoSuchDriverException;
import com.theusick.fleet.service.exception.NoSuchEnterpriseException;
import com.theusick.fleet.service.exception.NoSuchVehicleException;
import com.theusick.fleet.service.mapper.DriverMapper;
import com.theusick.fleet.service.model.DriverModel;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    private final EnterpriseRepository enterpriseRepository;
    private final EnterpriseService enterpriseService;
    private final VehicleRepository vehicleRepository;

    @Override
    public DriverModel getDriver(Long driverId) throws NoSuchDriverException {
        return driverMapper.driverModelFromEntity(driverRepository.findById(driverId)
            .orElseThrow(() -> new NoSuchDriverException(driverId)));
    }

    @Override
    public List<DriverModel> getDrivers() {
        return driverRepository.findAll().stream()
            .map(driverMapper::driverModelFromEntity)
            .toList();
    }

    @Override
    @Transactional
    public List<DriverModel> getEnterpriseDrivers(Long enterpriseId) throws NoSuchEnterpriseException {
        EnterpriseEntity enterpriseEntity = enterpriseRepository.findById(enterpriseId)
            .orElseThrow(() -> new NoSuchEnterpriseException(enterpriseId));
        return enterpriseEntity.getDrivers().stream()
            .map(driverMapper::driverModelFromEntity)
            .toList();
    }

    @Override
    public Page<DriverModel> getEnterpriseDriversPageForManager(Long managerId,
                                                                Long enterpriseId,
                                                                Pageable pageable)
        throws NoAccessException {
        return enterpriseService.getVisiblePageEntitiesForManager(
            managerId,
            enterpriseId,
            driverRepository::findByEnterpriseId,
            driverMapper::driverModelsPageFromEntities,
            pageable
        );
    }

    @Override
    public List<DriverModel> getEnterpriseDriversForManager(Long managerId,
                                                            Long enterpriseId) throws NoAccessException {
        return enterpriseService.getVisibleEntitiesForManager(
            managerId,
            enterpriseId,
            driverRepository::findByEnterpriseId,
            driverMapper::driverModelFromEntity
        );
    }

    @Override
    public Page<DriverModel> getEnterpriseActiveDriversPageForManager(Long managerId,
                                                                      Long enterpriseId,
                                                                      Pageable pageable)
        throws NoAccessException {
        return enterpriseService.getVisiblePageEntitiesForManager(
            managerId,
            enterpriseId,
            driverRepository::findByActiveVehicleIsNotNullAndEnterpriseId,
            driverMapper::driverModelsPageFromEntities,
            pageable
        );
    }

    @Override
    @Transactional
    public DriverModel createDriver(Long enterpriseId,
                                    DriverModel driverModel)
        throws NoSuchEnterpriseException, NoSuchVehicleException {
        EnterpriseEntity enterpriseEntity = enterpriseRepository.findById(enterpriseId)
            .orElseThrow(() -> new NoSuchEnterpriseException(enterpriseId));

        DriverEntity driverEntity = DriverEntity.builder()
            .enterprise(enterpriseEntity)
            .build();

        if (hasAssignedActiveVehicle(driverModel)) {
            setDriverEntityActiveVehicle(driverEntity, driverModel);
        }

        driverMapper.updateDriverEntityFromModel(driverEntity, driverModel);
        driverRepository.save(driverEntity);

        return driverMapper.driverModelFromEntity(driverEntity);
    }

    private void setDriverEntityActiveVehicle(DriverEntity driverEntity,
                                              DriverModel driverModel) throws NoSuchVehicleException {
        final Long activeVehicleId = driverModel.getActiveVehicleId();

        VehicleEntity activeVehicleEntity =
            vehicleRepository.findById(activeVehicleId)
                .orElseThrow(() -> new NoSuchVehicleException(activeVehicleId));
        driverEntity.setActiveVehicle(activeVehicleEntity);

        driverModel.getVehicleIds().add(activeVehicleEntity.getId());
    }

    private boolean hasAssignedActiveVehicle(DriverModel driverModel) {
        return Objects.nonNull(driverModel.getActiveVehicleId());
    }

    @Override
    @Transactional
    public void updateDriver(DriverModel driverModel) throws NoSuchDriverException {
        DriverEntity driverEntity = driverRepository.findById(driverModel.getId())
            .orElseThrow(() -> new NoSuchDriverException(driverModel.getId()));

        driverMapper.updateDriverEntityFromModel(driverEntity, driverModel);
        driverRepository.save(driverEntity);
    }

    @Override
    public void deleteDriver(Long driverId) throws NoSuchDriverException {
        DriverEntity driverEntity = driverRepository.findById(driverId)
            .orElseThrow(() -> new NoSuchDriverException(driverId));
        driverRepository.delete(driverEntity);
    }

}
