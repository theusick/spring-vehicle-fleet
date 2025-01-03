package com.theusick.service;

import com.theusick.service.exception.NoSuchDriverException;
import com.theusick.service.exception.NoSuchEnterpriseException;
import com.theusick.service.exception.NoSuchException;
import com.theusick.service.model.DriverModel;

import java.util.List;

public interface DriverService {

    DriverModel getDriver(Long driverId) throws NoSuchDriverException;

    List<DriverModel> getDrivers();

    List<DriverModel> getEnterpriseDrivers(Long enterpriseId) throws NoSuchEnterpriseException;

    List<DriverModel> getEnterpriseDriversForManager(Long managerId, Long enterpriseId) throws NoSuchException;

    DriverModel createDriver(DriverModel driverModel);

    void updateDriver(DriverModel driverModel) throws NoSuchDriverException;

    void deleteDriver(Long driverId) throws NoSuchDriverException;

}
