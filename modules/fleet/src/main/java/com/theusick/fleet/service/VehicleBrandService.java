package com.theusick.fleet.service;

import com.theusick.fleet.service.exception.NoSuchVehicleBrandException;
import com.theusick.fleet.service.model.VehicleBrandModel;

import java.util.List;

public interface VehicleBrandService {

    VehicleBrandModel getVehicleBrand(Long vehicleBrandId) throws NoSuchVehicleBrandException;

    List<VehicleBrandModel> getVehicleBrands();

    VehicleBrandModel createVehicleBrand(VehicleBrandModel vehicleBrandModel);

    void updateVehicleBrand(VehicleBrandModel vehicleBrandModel) throws NoSuchVehicleBrandException;

    void deleteVehicleBrand(Long vehicleBrandId) throws NoSuchVehicleBrandException;

}
