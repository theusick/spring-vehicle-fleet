package com.theusick.datagenerator.service;

import com.theusick.datagenerator.service.exception.NoVehicleBrandsException;
import com.theusick.fleet.service.exception.NoSuchDriverException;
import com.theusick.fleet.service.exception.NoSuchEnterpriseException;
import com.theusick.fleet.service.exception.NoSuchVehicleBrandException;
import com.theusick.fleet.service.exception.NoSuchVehicleException;
import com.theusick.fleet.service.model.DriverModel;
import com.theusick.fleet.service.model.VehicleModel;

import java.util.List;

public interface GeneratorService {

    List<VehicleModel> generateAndSaveVehicles(Long enterpriseId,
                                               int count)
        throws NoSuchEnterpriseException, NoVehicleBrandsException, NoSuchVehicleBrandException;

    List<DriverModel> generateAndSaveDrivers(Long enterpriseId,
                                             int count,
                                             List<VehicleModel> availableVehicles)
        throws NoSuchEnterpriseException, NoSuchVehicleException, NoSuchDriverException;

}
