package com.theusick.service.mapper;

import com.theusick.controller.dto.driver.VehicleDriverForDriverDTO;
import com.theusick.controller.dto.vehicle.VehicleDriverForVehicleDTO;
import com.theusick.repository.entity.VehicleDriverEntity;
import com.theusick.service.model.VehicleDriverModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleDriverMapper {

    @Mapping(target = "vehicleId", source = "primaryKey.vehicle.id")
    @Mapping(target = "driverId", source = "primaryKey.driver.id")
    VehicleDriverModel vehicleDriverModelFromEntity(VehicleDriverEntity entity);

    @Mapping(target = "id", source = "vehicleId")
    VehicleDriverForDriverDTO dtoWithoutDriverIdFromModel(VehicleDriverModel model);

    @Mapping(target = "id", source = "driverId")
    VehicleDriverForVehicleDTO dtoWithoutVehicleIdFromModel(VehicleDriverModel model);

}
