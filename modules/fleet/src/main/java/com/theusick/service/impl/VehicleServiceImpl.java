package com.theusick.service.impl;

import com.theusick.repository.repository.VehicleRepository;
import com.theusick.service.VehicleService;
import com.theusick.service.mapper.VehicleMapper;
import com.theusick.service.model.VehicleModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    public List<VehicleModel> getVehicles() {
        return vehicleRepository.findAll().stream()
            .map(vehicleMapper::vehicleModelFromEntity)
            .toList();
    }

}
