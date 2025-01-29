package com.theusick.fleet.service.impl;

import com.theusick.fleet.repository.DriverRepository;
import com.theusick.fleet.repository.VehicleDriverRepository;
import com.theusick.fleet.repository.VehicleRepository;
import com.theusick.fleet.repository.entity.DriverEntity;
import com.theusick.fleet.repository.entity.VehicleDriverEntity;
import com.theusick.fleet.repository.entity.VehicleEntity;
import com.theusick.fleet.service.VehicleDriverService;
import com.theusick.fleet.service.exception.NoSuchDriverException;
import com.theusick.fleet.service.exception.NoSuchVehicleException;
import com.theusick.fleet.service.mapper.VehicleDriverMapper;
import com.theusick.fleet.service.model.VehicleDriverModel;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VehicleDriverServiceImpl implements VehicleDriverService {

    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;

    private final VehicleDriverMapper vehicleDriverMapper;
    private final VehicleDriverRepository vehicleDriverRepository;

    @Override
    @Transactional
    public VehicleDriverModel createVehicleDriver(VehicleDriverModel vehicleDriverModel)
        throws NoSuchVehicleException, NoSuchDriverException {
        DriverEntity driverEntity = driverRepository.findById(vehicleDriverModel.getDriverId())
            .orElseThrow(() -> new NoSuchDriverException(vehicleDriverModel.getDriverId()));

        VehicleEntity vehicleEntity = vehicleRepository.findById(vehicleDriverModel.getVehicleId())
            .orElseThrow(() -> new NoSuchVehicleException(vehicleDriverModel.getVehicleId()));

        VehicleDriverEntity vehicleDriverLink =
            createVehicleDriverEntity(vehicleEntity, driverEntity);

        vehicleDriverRepository.save(vehicleDriverLink);
        return vehicleDriverMapper.vehicleDriverModelFromEntity(vehicleDriverLink);
    }

    @Override
    @Transactional
    public List<VehicleDriverModel> createVehicleDrivers(List<VehicleDriverModel> vehicleDriverModels) {
        Set<Long> vehicleIds = vehicleDriverModels.stream()
            .map(VehicleDriverModel::getVehicleId)
            .collect(Collectors.toSet());

        Map<Long, VehicleEntity> vehicleMap = vehicleRepository.findAllById(vehicleIds).stream()
            .collect(Collectors.toMap(VehicleEntity::getId, Function.identity()));

        Set<Long> driverIds = vehicleDriverModels.stream()
            .map(VehicleDriverModel::getDriverId)
            .collect(Collectors.toSet());

        Map<Long, DriverEntity> driverMap = driverRepository.findAllById(driverIds).stream()
            .collect(Collectors.toMap(DriverEntity::getId, Function.identity()));

        List<VehicleDriverEntity> entities = vehicleDriverModels.stream()
            .map(model -> {
                VehicleEntity vehicle = vehicleMap.get(model.getVehicleId());
                DriverEntity driver = driverMap.get(model.getDriverId());

                if (Objects.nonNull(vehicle) && Objects.nonNull(driver)) {
                    return createVehicleDriverEntity(vehicle, driver);
                }
                return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        return vehicleDriverRepository.saveAll(entities).stream()
            .map(vehicleDriverMapper::vehicleDriverModelFromEntity)
            .toList();
    }

    private VehicleDriverEntity createVehicleDriverEntity(VehicleEntity vehicle,
                                                          DriverEntity driver) {
        VehicleDriverEntity.VehicleDriverId vehicleDriverLinkKey =
            new VehicleDriverEntity.VehicleDriverId();

        vehicleDriverLinkKey.setDriver(driver);
        vehicleDriverLinkKey.setVehicle(vehicle);

        return VehicleDriverEntity.builder()
            .primaryKey(vehicleDriverLinkKey)
            .build();
    }

}
