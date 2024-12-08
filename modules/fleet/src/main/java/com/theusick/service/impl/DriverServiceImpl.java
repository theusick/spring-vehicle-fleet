package com.theusick.service.impl;

import com.theusick.repository.DriverRepository;
import com.theusick.repository.EnterpriseRepository;
import com.theusick.repository.entity.DriverEntity;
import com.theusick.repository.entity.EnterpriseEntity;
import com.theusick.service.DriverService;
import com.theusick.service.exception.NoSuchDriverException;
import com.theusick.service.exception.NoSuchEnterpriseException;
import com.theusick.service.mapper.DriverMapper;
import com.theusick.service.model.DriverModel;
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
    @Transactional
    public DriverModel createDriver(DriverModel driverModel) {
        if (driverModel.isActive() && hasVehicleActiveDriver(driverModel.getVehicleId())) {
            throw new IllegalStateException("Vehicle already has an active driver");
        }

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

        boolean toggleActiveFlag = !driverEntity.isActive() && driverModel.isActive();
        if (toggleActiveFlag && hasVehicleActiveDriver(driverModel.getVehicleId())) {
            throw new IllegalStateException("Vehicle already has an active driver");
        }

        driverMapper.updateDriverEntityFromModel(driverEntity, driverModel);
        driverRepository.save(driverEntity);
    }

    @Override
    public void deleteDriver(Long driverId) throws NoSuchDriverException {
        DriverEntity driverEntity = driverRepository.findById(driverId)
            .orElseThrow(() -> new NoSuchDriverException(driverId));
        driverRepository.delete(driverEntity);
    }

    protected boolean hasVehicleActiveDriver(Long vehicleId) {
        return driverRepository.existsByVehicleIdAndActive(vehicleId, true);
    }

}
