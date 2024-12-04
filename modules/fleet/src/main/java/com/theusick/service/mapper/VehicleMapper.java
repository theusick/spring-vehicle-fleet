package com.theusick.service.mapper;

import com.theusick.controller.dto.VehicleDTO;
import com.theusick.repository.entity.VehicleEntity;
import com.theusick.service.model.VehicleModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    VehicleDTO vehicleDTOFromModel(VehicleModel vehicleModel);

    VehicleModel vehicleModelFromDTO(VehicleDTO vehicleDTO);

    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "enterpriseId", source = "enterprise.id")
    VehicleModel vehicleModelFromEntity(VehicleEntity vehicleEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enterprise", ignore = true)
    @Mapping(target = "drivers", ignore = true)
    @Mapping(target = "brand.id", source = "brandId")
    void updateVehicleEntityFromModel(@MappingTarget VehicleEntity vehicleEntity,
                                      VehicleModel vehicleModel);

}
