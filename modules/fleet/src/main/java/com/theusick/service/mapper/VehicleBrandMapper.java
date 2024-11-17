package com.theusick.service.mapper;

import com.theusick.repository.entity.VehicleBrandEntity;
import com.theusick.service.model.VehicleBrandModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleBrandMapper {

    VehicleBrandModel vehicleBrandModelFromEntity(VehicleBrandEntity vehicleBrandEntity);

}
