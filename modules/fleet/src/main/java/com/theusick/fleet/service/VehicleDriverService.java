package com.theusick.fleet.service;

import com.theusick.fleet.service.exception.NoSuchDriverException;
import com.theusick.fleet.service.exception.NoSuchVehicleException;
import com.theusick.fleet.service.model.VehicleDriverModel;

import java.util.List;

public interface VehicleDriverService {

    VehicleDriverModel createVehicleDriver(VehicleDriverModel vehicleDriverModel)
        throws NoSuchVehicleException, NoSuchDriverException;

    List<VehicleDriverModel> createVehicleDrivers(List<VehicleDriverModel> vehicleDriverModels);

}
