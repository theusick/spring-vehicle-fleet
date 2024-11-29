package com.theusick.service.mapper;

import com.theusick.repository.entity.VehicleBrandEntity;
import com.theusick.repository.entity.VehicleEntity;
import com.theusick.service.model.VehicleBrandModel;
import com.theusick.service.model.VehicleModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(source = "brand.id", target = "brandId")
    VehicleModel vehicleModelFromEntity(VehicleEntity vehicleEntity);

    @Mapping(target = "id", ignore = true)
    void updateVehicleEntityFromModel(@MappingTarget VehicleEntity vehicleEntity,
                                      VehicleModel vehicleModel);

}
