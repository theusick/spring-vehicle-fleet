package com.theusick.service.mapper;

import com.theusick.repository.entity.VehicleBrandEntity;
import com.theusick.service.model.VehicleBrandModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VehicleBrandMapper {

    VehicleBrandModel vehicleBrandModelFromEntity(VehicleBrandEntity vehicleBrandEntity);

    @Mapping(target = "id", ignore = true)
    void updateVehicleBrandEntityFromModel(@MappingTarget VehicleBrandEntity vehicleBrandEntity,
                                           VehicleBrandModel vehicleBrandModel);

}
