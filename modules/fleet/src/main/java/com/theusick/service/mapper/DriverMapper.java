package com.theusick.service.mapper;

import com.theusick.controller.dto.driver.DriverBaseDTO;
import com.theusick.controller.dto.driver.EnterpriseDriverDTO;
import com.theusick.repository.entity.DriverEntity;
import com.theusick.service.model.DriverModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DriverMapper {

    DriverBaseDTO driverBaseDTOFromModel(DriverModel driverModel);

    @Mapping(target = "driver", source = "driverModel")
    EnterpriseDriverDTO enterpriseDriverDTOFromModel(DriverModel driverModel);

    @Mapping(target = "enterpriseId", source = "enterprise.id")
    @Mapping(target = "vehicleId", source = "vehicle.id")
    DriverModel driverModelFromEntity(DriverEntity driverEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enterprise", ignore = true)
    @Mapping(target = "vehicle", ignore = true)
    void updateDriverEntityFromModel(@MappingTarget DriverEntity driverEntity,
                                     DriverModel driverModel);

}
