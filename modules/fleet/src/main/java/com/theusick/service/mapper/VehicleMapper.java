package com.theusick.service.mapper;

import com.theusick.repository.entity.VehicleEntity;
import com.theusick.service.model.VehicleModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    VehicleModel vehicleModelFromEntity(VehicleEntity vehicleEntity);

}
