package com.theusick.service.mapper;

import com.theusick.repository.entity.VehicleEntity;
import com.theusick.service.model.VehicleModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(source = "brand.id", target = "brandId")
    VehicleModel vehicleModelFromEntity(VehicleEntity vehicleEntity);

}
