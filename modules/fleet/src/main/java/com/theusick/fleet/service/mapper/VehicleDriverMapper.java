package com.theusick.fleet.service.mapper;

import com.theusick.fleet.repository.entity.VehicleDriverEntity;
import com.theusick.fleet.service.model.VehicleDriverModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleDriverMapper {

    @Mapping(target = "vehicleId", source = "primaryKey.vehicle.id")
    @Mapping(target = "driverId", source = "primaryKey.driver.id")
    VehicleDriverModel vehicleDriverModelFromEntity(VehicleDriverEntity entity);

}
