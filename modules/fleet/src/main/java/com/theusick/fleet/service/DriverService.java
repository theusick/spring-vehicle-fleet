package com.theusick.fleet.service;

import com.theusick.core.service.exception.NoAccessException;
import com.theusick.fleet.service.exception.NoSuchDriverException;
import com.theusick.fleet.service.exception.NoSuchEnterpriseException;
import com.theusick.fleet.service.exception.NoSuchVehicleException;
import com.theusick.fleet.service.model.DriverModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DriverService {

    DriverModel getDriver(Long driverId) throws NoSuchDriverException;

    List<DriverModel> getDrivers();

    List<DriverModel> getEnterpriseDrivers(Long enterpriseId) throws NoSuchEnterpriseException;

    Page<DriverModel> getEnterpriseDriversPageForManager(Long managerId,
                                                         Long enterpriseId,
                                                         Pageable pageable) throws NoAccessException;

    List<DriverModel> getEnterpriseDriversForManager(Long managerId,
                                                     Long enterpriseId) throws NoAccessException;

    Page<DriverModel> getEnterpriseActiveDriversPageForManager(Long managerId,
                                                               Long enterpriseId,
                                                               Pageable pageable) throws NoAccessException;

    DriverModel createDriver(Long enterpriseId,
                             DriverModel driverModel)
        throws NoSuchEnterpriseException, NoSuchVehicleException, NoSuchDriverException;

    void updateDriver(DriverModel driverModel) throws NoSuchDriverException;

    void deleteDriver(Long driverId) throws NoSuchDriverException;

}
