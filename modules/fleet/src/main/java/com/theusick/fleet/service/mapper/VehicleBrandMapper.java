package com.theusick.fleet.service.mapper;

import com.theusick.fleet.controller.dto.brand.VehicleBrandBaseDTO;
import com.theusick.fleet.controller.dto.brand.VehicleBrandIdDTO;
import com.theusick.fleet.repository.entity.VehicleBrandEntity;
import com.theusick.fleet.service.model.VehicleBrandModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VehicleBrandMapper {

    VehicleBrandBaseDTO vehicleBrandBaseDTOFromModel(VehicleBrandModel model);

    VehicleBrandIdDTO vehicleBrandIdDTOFromModel(VehicleBrandModel model);

    VehicleBrandModel vehicleBrandModelFromEntity(VehicleBrandEntity vehicleBrandEntity);

    @Mapping(target = "id", ignore = true)
    void updateVehicleBrandEntityFromModel(@MappingTarget VehicleBrandEntity vehicleBrandEntity,
                                           VehicleBrandModel vehicleBrandModel);

}
