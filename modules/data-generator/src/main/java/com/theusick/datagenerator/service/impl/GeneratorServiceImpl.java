package com.theusick.datagenerator.service.impl;

import com.theusick.datagenerator.service.GeneratorService;
import com.theusick.datagenerator.service.exception.NoVehicleBrandsException;
import com.theusick.datagenerator.service.model.DriverGenerator;
import com.theusick.datagenerator.service.model.VehicleGenerator;
import com.theusick.fleet.service.DriverService;
import com.theusick.fleet.service.VehicleBrandService;
import com.theusick.fleet.service.VehicleService;
import com.theusick.fleet.service.exception.NoSuchDriverException;
import com.theusick.fleet.service.exception.NoSuchEnterpriseException;
import com.theusick.fleet.service.exception.NoSuchVehicleBrandException;
import com.theusick.fleet.service.exception.NoSuchVehicleException;
import com.theusick.fleet.service.model.DriverModel;
import com.theusick.fleet.service.model.VehicleBrandModel;
import com.theusick.fleet.service.model.VehicleModel;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GeneratorServiceImpl implements GeneratorService {

    private final VehicleService vehicleService;
    private final VehicleBrandService vehicleBrandService;
    private final DriverService driverService;

    @Override
    public List<Long> generateAndSaveVehicles(Long enterpriseId,
                                              @Min(1) int count)
        throws NoSuchEnterpriseException, NoVehicleBrandsException, NoSuchVehicleBrandException {
        List<VehicleBrandModel> brands = vehicleBrandService.getVehicleBrands();
        if (brands.isEmpty()) {
            throw new NoVehicleBrandsException();
        }

        VehicleGenerator generator = VehicleGenerator.builder().build();

        List<Long> createdVehicleIds = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            VehicleModel generatedVehicleModel = generator.generateFields();

            createdVehicleIds.add(
                vehicleService.createVehicle(
                        enterpriseId,
                        VehicleGenerator.getRandomBrand(brands).getId(),
                        generatedVehicleModel)
                    .getId());
        }
        return createdVehicleIds;
    }

    @Override
    public List<Long> generateAndSaveDrivers(Long enterpriseId,
                                             @Min(1) int count,
                                             List<Long> availableVehicleIds)
        throws NoSuchEnterpriseException, NoSuchVehicleException, NoSuchDriverException {
        DriverGenerator generator = DriverGenerator.builder()
            .availableVehicleIds(new ArrayList<>(availableVehicleIds))
            .build();

        List<Long> createdDriverIds = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            DriverModel generatedDriverModel = generator.generateFields();

            createdDriverIds.add(
                driverService.createDriver(
                        enterpriseId,
                        generatedDriverModel)
                    .getId());
        }
        return createdDriverIds;
    }

}
