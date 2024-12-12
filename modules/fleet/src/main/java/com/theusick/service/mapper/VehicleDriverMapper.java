package com.theusick.service.mapper;

import com.theusick.controller.dto.driver.VehicleDriverWithoutDriverIdDTO;
import com.theusick.controller.dto.vehicle.VehicleDriverWithoutVehicleIdDTO;
import com.theusick.repository.entity.VehicleDriverEntity;
import com.theusick.service.model.VehicleDriverModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleDriverMapper {

    @Mapping(target = "vehicleId", source = "primaryKey.vehicle.id")
    @Mapping(target = "driverId", source = "primaryKey.driver.id")
    VehicleDriverModel vehicleDriverModelFromEntity(VehicleDriverEntity entity);

    VehicleDriverWithoutDriverIdDTO dtoWithoutDriverIdFromModel(VehicleDriverModel model);

    VehicleDriverWithoutVehicleIdDTO dtoWithoutVehicleIdFromModel(VehicleDriverModel model);

}
