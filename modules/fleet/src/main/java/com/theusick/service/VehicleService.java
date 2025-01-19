package com.theusick.service;

import com.theusick.service.exception.NoAccessException;
import com.theusick.service.exception.NoSuchEnterpriseException;
import com.theusick.service.exception.NoSuchException;
import com.theusick.service.exception.NoSuchVehicleException;
import com.theusick.service.model.VehicleModel;

import java.util.List;

public interface VehicleService {

    VehicleModel getVehicle(Long vehicleId) throws NoSuchVehicleException;

    List<VehicleModel> getVehicles();

    List<VehicleModel> getEnterpriseVehicles(Long enterpriseId) throws NoSuchEnterpriseException;

    List<VehicleModel> getEnterpriseVehiclesForManager(Long managerId,
                                                       Long enterpriseId) throws NoAccessException;

    void createVehicle(Long enterpriseId,
                       Long brandId,
                       VehicleModel vehicleModel) throws NoSuchException;

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
