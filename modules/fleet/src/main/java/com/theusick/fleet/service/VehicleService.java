package com.theusick.fleet.service;

import com.theusick.core.service.exception.NoAccessException;
import com.theusick.core.service.exception.NoSuchException;
import com.theusick.fleet.service.exception.NoSuchEnterpriseException;
import com.theusick.fleet.service.exception.NoSuchVehicleBrandException;
import com.theusick.fleet.service.exception.NoSuchVehicleException;
import com.theusick.fleet.service.model.VehicleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VehicleService {

    VehicleModel getVehicle(Long vehicleId) throws NoSuchVehicleException;

    List<VehicleModel> getVehicles();

    List<VehicleModel> getEnterpriseVehicles(Long enterpriseId) throws NoSuchEnterpriseException;

    List<VehicleModel> getEnterpriseVehiclesForManager(Long managerId,
                                                       Long enterpriseId) throws NoAccessException;

    Page<VehicleModel> getEnterpriseVehiclesPageForManager(Long managerId,
                                                           Long enterpriseId,
                                                           Pageable pageable) throws NoAccessException;

    VehicleModel createVehicle(Long enterpriseId,
                               Long brandId,
                               VehicleModel vehicleModel)
        throws NoSuchEnterpriseException, NoSuchVehicleBrandException;

    VehicleModel createVehicleForManager(Long enterpriseId,
                                         Long brandId,
                                         Long managerId,
                                         VehicleModel vehicleModel) throws NoSuchException, NoAccessException;

    void updateVehicle(VehicleModel vehicleModel) throws NoSuchException;

    VehicleModel updateVehicleForManager(Long currentEnterpriseId,
                                         Long newEnterpriseId,
                                         Long managerId,
                                         VehicleModel vehicleModel) throws NoSuchException, NoAccessException;

    void deleteVehicle(Long vehicleId) throws NoSuchVehicleException;

    void deleteVehicleForManager(Long enterpriseId,
                                 Long managerId,
                                 Long vehicleId) throws NoSuchException, NoAccessException;
}
