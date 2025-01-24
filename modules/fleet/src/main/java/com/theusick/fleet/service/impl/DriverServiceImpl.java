package com.theusick.fleet.service.impl;

import com.theusick.fleet.repository.DriverRepository;
import com.theusick.fleet.repository.EnterpriseRepository;
import com.theusick.fleet.repository.entity.DriverEntity;
import com.theusick.fleet.repository.entity.EnterpriseEntity;
import com.theusick.fleet.service.DriverService;
import com.theusick.fleet.service.EnterpriseService;
import com.theusick.core.service.exception.NoAccessException;
import com.theusick.fleet.service.exception.NoSuchDriverException;
import com.theusick.fleet.service.exception.NoSuchEnterpriseException;
import com.theusick.fleet.service.mapper.DriverMapper;
import com.theusick.fleet.service.model.DriverModel;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    private final EnterpriseRepository enterpriseRepository;
    private final EnterpriseService enterpriseService;

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
    public DriverModel createDriver(DriverModel driverModel) {
        DriverEntity driverEntity = new DriverEntity();
        driverMapper.updateDriverEntityFromModel(driverEntity, driverModel);
        driverRepository.save(driverEntity);
        return driverMapper.driverModelFromEntity(driverEntity);
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
