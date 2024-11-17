package com.theusick.service.impl;

import com.theusick.repository.repository.VehicleBrandRepository;
import com.theusick.service.VehicleBrandService;
import com.theusick.service.mapper.VehicleBrandMapper;
import com.theusick.service.model.VehicleBrandModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VehicleBrandServiceImpl implements VehicleBrandService {

    private final VehicleBrandRepository vehicleBrandRepository;
    private final VehicleBrandMapper vehicleBrandMapper;

    @Override
    public List<VehicleBrandModel> getVehicleBrands() {
        return vehicleBrandRepository.findAll().stream()
            .map(vehicleBrandMapper::vehicleBrandModelFromEntity)
            .toList();
    }
}
