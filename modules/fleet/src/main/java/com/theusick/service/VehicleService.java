package com.theusick.service;

import com.theusick.service.exception.NoSuchEnterpriseException;
import com.theusick.service.exception.NoSuchException;
import com.theusick.service.exception.NoSuchVehicleException;
import com.theusick.service.model.VehicleModel;

import java.util.List;

public interface VehicleService {

    VehicleModel getVehicle(Long vehicleId) throws NoSuchVehicleException;

    List<VehicleModel> getVehicles();

    List<VehicleModel> getEnterpriseVehicles(Long enterpriseId) throws NoSuchEnterpriseException;

    List<VehicleModel> getEnterpriseVehiclesForManager(Long managerId, Long enterpriseId) throws NoSuchException;

    VehicleModel createVehicle(Long enterpriseId,
                               Long brandId,
                               VehicleModel vehicleModel) throws NoSuchException;

    void updateVehicle(VehicleModel vehicleModel) throws NoSuchException;

    void deleteVehicle(Long vehicleId) throws NoSuchVehicleException;

}
