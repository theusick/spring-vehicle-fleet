package com.theusick.fleet.service;

import com.theusick.core.service.exception.NoAccessException;
import com.theusick.core.service.exception.NoSuchException;
import com.theusick.fleet.service.exception.NoSuchEnterpriseException;
import com.theusick.fleet.service.exception.NoSuchVehicleBrandException;
import com.theusick.fleet.service.exception.NoSuchVehicleException;
import com.theusick.fleet.service.model.VehicleModel;

import java.util.List;

public interface VehicleService {

    VehicleModel getVehicle(Long vehicleId) throws NoSuchVehicleException;

    List<VehicleModel> getVehicles();

    List<VehicleModel> getEnterpriseVehicles(Long enterpriseId) throws NoSuchEnterpriseException;

    List<VehicleModel> getEnterpriseVehiclesForManager(Long managerId,
                                                       Long enterpriseId) throws NoAccessException;

    VehicleModel createVehicle(Long enterpriseId,
                               Long brandId,
                               VehicleModel vehicleModel)
        throws NoSuchEnterpriseException, NoSuchVehicleBrandException;

    VehicleModel createVehicleForManager(Long enterpriseId,
                                         Long brandId,
                                         Long managerId,
                                         VehicleModel vehicleModel) throws NoSuchException, NoAccessException;

    void updateVehicle(VehicleModel vehicleModel) throws NoSuchException;

    VehicleModel updateVehicleForManager(Long enterpriseId,
                                         Long brandId,
                                         Long managerId,
                                         VehicleModel vehicleModel) throws NoSuchException, NoAccessException;

    void deleteVehicle(Long vehicleId) throws NoSuchVehicleException;

    void deleteVehicleForManager(Long enterpriseId,
                                 Long vehicleId,
                                 Long managerId) throws NoSuchException, NoAccessException;

}
