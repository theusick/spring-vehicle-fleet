package com.theusick.service;

import com.theusick.service.exception.NoSuchVehicleBrandException;
import com.theusick.service.model.VehicleBrandModel;

import java.util.List;

public interface VehicleBrandService {

    VehicleBrandModel getVehicleBrand(Long vehicleBrandId) throws NoSuchVehicleBrandException;

    List<VehicleBrandModel> getVehicleBrands();

    VehicleBrandModel createVehicleBrand(VehicleBrandModel vehicleBrandModel);

    void updateVehicleBrand(VehicleBrandModel vehicleBrandModel) throws NoSuchVehicleBrandException;

    void deleteVehicleBrand(Long vehicleBrandId) throws NoSuchVehicleBrandException;

}
