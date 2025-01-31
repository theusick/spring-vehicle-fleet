package com.theusick.datagenerator.service;

import com.theusick.datagenerator.service.exception.NoVehicleBrandsException;
import com.theusick.fleet.service.exception.NoSuchDriverException;
import com.theusick.fleet.service.exception.NoSuchEnterpriseException;
import com.theusick.fleet.service.exception.NoSuchVehicleBrandException;
import com.theusick.fleet.service.exception.NoSuchVehicleException;

import java.util.List;

public interface GeneratorService {

    List<Long> generateAndSaveVehicles(Long enterpriseId,
                                       int count)
        throws NoSuchEnterpriseException, NoVehicleBrandsException, NoSuchVehicleBrandException;

    List<Long> generateAndSaveDrivers(Long enterpriseId,
                                      int count,
                                      List<Long> availableVehicleIds)
        throws NoSuchEnterpriseException, NoSuchVehicleException, NoSuchDriverException;

}
