package com.theusick.fleet.service.impl;

import com.theusick.fleet.repository.VehicleBrandRepository;
import com.theusick.fleet.repository.entity.VehicleBrandEntity;
import com.theusick.fleet.service.VehicleBrandService;
import com.theusick.fleet.service.exception.NoSuchVehicleBrandException;
import com.theusick.fleet.service.mapper.VehicleBrandMapper;
import com.theusick.fleet.service.model.VehicleBrandModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VehicleBrandServiceImpl implements VehicleBrandService {

    private final VehicleBrandRepository vehicleBrandRepository;
    private final VehicleBrandMapper vehicleBrandMapper;

    @Override
    public VehicleBrandModel getVehicleBrand(Long vehicleBrandId) throws NoSuchVehicleBrandException {
        return vehicleBrandMapper.vehicleBrandModelFromEntity(vehicleBrandRepository.findById(vehicleBrandId)
            .orElseThrow(() -> new NoSuchVehicleBrandException(vehicleBrandId)));
    }

    @Override
    public List<VehicleBrandModel> getVehicleBrands() {
        return vehicleBrandRepository.findAll().stream()
            .map(vehicleBrandMapper::vehicleBrandModelFromEntity)
            .toList();
    }

    @Override
    public VehicleBrandModel createVehicleBrand(VehicleBrandModel vehicleBrandModel) {
        VehicleBrandEntity brandEntity = new VehicleBrandEntity();
        vehicleBrandMapper.updateVehicleBrandEntityFromModel(brandEntity, vehicleBrandModel);
        vehicleBrandRepository.save(brandEntity);
        return vehicleBrandMapper.vehicleBrandModelFromEntity(brandEntity);
    }

    @Override
    public void updateVehicleBrand(VehicleBrandModel vehicleBrandModel) throws NoSuchVehicleBrandException {
        VehicleBrandEntity brandEntity = vehicleBrandRepository.findById(vehicleBrandModel.getId())
            .orElseThrow(() -> new NoSuchVehicleBrandException(vehicleBrandModel.getId()));
        vehicleBrandMapper.updateVehicleBrandEntityFromModel(brandEntity, vehicleBrandModel);
        vehicleBrandRepository.save(brandEntity);
    }

    @Override
    public void deleteVehicleBrand(Long vehicleBrandId) throws NoSuchVehicleBrandException {
        VehicleBrandEntity brandEntity = vehicleBrandRepository.findById(vehicleBrandId)
            .orElseThrow(() -> new NoSuchVehicleBrandException(vehicleBrandId));
        vehicleBrandRepository.delete(brandEntity);
    }

}
