package com.theusick.service;

import com.theusick.service.exception.NoSuchVehicleBrandException;
import com.theusick.service.exception.NoSuchVehicleException;
import com.theusick.service.model.VehicleModel;

import java.util.List;

public interface VehicleService {

    VehicleModel getVehicle(Long vehicleId) throws NoSuchVehicleException;

    List<VehicleModel> getVehicles();

    VehicleModel createVehicle(Long brandId,
                               VehicleModel vehicleModel) throws NoSuchVehicleBrandException;

    void updateVehicle(VehicleModel vehicleModel) throws NoSuchVehicleException,
        NoSuchVehicleBrandException;

    void deleteVehicle(Long vehicleId) throws NoSuchVehicleException;

}
