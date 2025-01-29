package com.theusick.datagenerator.service.impl;

import com.theusick.datagenerator.service.GeneratorService;
import com.theusick.datagenerator.service.exception.NoVehicleBrandsException;
import com.theusick.datagenerator.service.model.DriverGenerator;
import com.theusick.datagenerator.service.model.VehicleGenerator;
import com.theusick.fleet.service.DriverService;
import com.theusick.fleet.service.EnterpriseService;
import com.theusick.fleet.service.VehicleBrandService;
import com.theusick.fleet.service.VehicleService;
import com.theusick.fleet.service.exception.NoSuchDriverException;
import com.theusick.fleet.service.exception.NoSuchEnterpriseException;
import com.theusick.fleet.service.exception.NoSuchVehicleBrandException;
import com.theusick.fleet.service.exception.NoSuchVehicleException;
import com.theusick.fleet.service.model.DriverModel;
import com.theusick.fleet.service.model.EnterpriseModel;
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

    private final EnterpriseService enterpriseService;
    private final VehicleService vehicleService;
    private final VehicleBrandService vehicleBrandService;
    private final DriverService driverService;

    @Override
    public List<VehicleModel> generateAndSaveVehicles(Long enterpriseId,
                                                      @Min(1) int count)
        throws NoSuchEnterpriseException, NoVehicleBrandsException, NoSuchVehicleBrandException {
        EnterpriseModel enterprise = enterpriseService.getEnterprise(enterpriseId);

        List<VehicleBrandModel> brands = vehicleBrandService.getVehicleBrands();
        if (brands.isEmpty()) {
            throw new NoVehicleBrandsException();
        }

        VehicleGenerator generator = VehicleGenerator.builder()
            .enterpriseId(enterprise.getId())
            .build();

        List<VehicleModel> createdVehicles = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            VehicleModel generatedVehicleModel = generator.generateFields();

            createdVehicles.add(vehicleService.createVehicle(
                enterprise.getId(),
                VehicleGenerator.getRandomBrand(brands).getId(),
                generatedVehicleModel));
        }
        return createdVehicles;
    }

    @Override
    public List<DriverModel> generateAndSaveDrivers(Long enterpriseId,
                                                    @Min(1) int count,
                                                    List<VehicleModel> availableVehicles)
        throws NoSuchEnterpriseException, NoSuchVehicleException, NoSuchDriverException {
        EnterpriseModel enterprise = enterpriseService.getEnterprise(enterpriseId);

        DriverGenerator generator = DriverGenerator.builder()
            .enterpriseId(enterprise.getId())
            .availableVehicles(availableVehicles)
            .build();

        List<DriverModel> createdDrivers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            DriverModel generatedDriverModel = generator.generateFields();

            createdDrivers.add(driverService.createDriver(
                enterprise.getId(),
                generatedDriverModel));
        }
        return createdDrivers;
    }

}
