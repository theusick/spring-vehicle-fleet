package com.theusick.service.impl;

import com.theusick.repository.VehicleBrandRepository;
import com.theusick.repository.VehicleRepository;
import com.theusick.repository.entity.VehicleBrandEntity;
import com.theusick.repository.entity.VehicleEntity;
import com.theusick.service.VehicleService;
import com.theusick.service.exception.NoSuchVehicleBrandException;
import com.theusick.service.exception.NoSuchVehicleException;
import com.theusick.service.mapper.VehicleMapper;
import com.theusick.service.model.VehicleModel;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

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
    public VehicleModel createVehicle(Long brandId,
                                      VehicleModel vehicleModel) throws NoSuchVehicleBrandException {
        VehicleBrandEntity brandEntity = brandRepository.findById(brandId)
            .orElseThrow(() -> new NoSuchVehicleBrandException(brandId));

        VehicleEntity vehicleEntity = VehicleEntity.builder()
            .brand(brandEntity)
            .build();

        vehicleMapper.updateVehicleEntityFromModel(vehicleEntity, vehicleModel);
        vehicleRepository.save(vehicleEntity);

        return vehicleMapper.vehicleModelFromEntity(vehicleEntity);
    }

    @Override
    @Transactional
    public void updateVehicle(VehicleModel vehicleModel) throws NoSuchVehicleException,
        NoSuchVehicleBrandException {
        VehicleEntity vehicleEntity = vehicleRepository.findById(vehicleModel.getId())
            .orElseThrow(() -> new NoSuchVehicleException(vehicleModel.getId()));

        final Long newBrandId = vehicleModel.getBrand().getId();
        if (!Objects.equals(newBrandId, vehicleEntity.getBrand().getId())) {
            VehicleBrandEntity brandEntity = brandRepository.findById(newBrandId)
                .orElseThrow(() -> new NoSuchVehicleBrandException(newBrandId));

            vehicleEntity.setBrand(brandEntity);
        }
        vehicleMapper.updateVehicleEntityFromModel(vehicleEntity, vehicleModel);
        vehicleRepository.save(vehicleEntity);
    }

    @Override
    public void deleteVehicle(Long vehicleId) throws NoSuchVehicleException {
        VehicleEntity vehicleEntity = vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new NoSuchVehicleException(vehicleId));
        vehicleRepository.delete(vehicleEntity);
    }

}
