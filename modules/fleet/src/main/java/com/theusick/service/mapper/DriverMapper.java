package com.theusick.service.mapper;

import com.theusick.controller.dto.driver.DriverBaseDTO;
import com.theusick.repository.entity.DriverEntity;
import com.theusick.service.model.DriverModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {VehicleMapper.class, VehicleDriverMapper.class})
public interface DriverMapper {

    @Mapping(target = "vehicles", source = "vehicleDrivers")
    DriverBaseDTO driverBaseDTOFromModel(DriverModel driverModel);

    @Mapping(target = "enterpriseId", source = "enterprise.id")
    DriverModel driverModelFromEntity(DriverEntity driverEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enterprise", ignore = true)
    @Mapping(target = "vehicleDrivers", ignore = true)
    void updateDriverEntityFromModel(@MappingTarget DriverEntity driverEntity,
                                     DriverModel driverModel);

}
