package com.theusick.service.mapper;

import com.theusick.controller.dto.brand.VehicleBrandBaseDTO;
import com.theusick.controller.dto.brand.VehicleBrandIdDTO;
import com.theusick.repository.entity.VehicleBrandEntity;
import com.theusick.service.model.VehicleBrandModel;
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
