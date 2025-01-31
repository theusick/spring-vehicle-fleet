package com.theusick.frontend.controller.mapper;

import com.theusick.frontend.controller.dto.VehicleViewDTO;
import com.theusick.fleet.service.model.VehicleModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleViewMapper {

    VehicleViewDTO vehicleViewDTOFromModel(VehicleModel vehicleModel);

    @Mapping(target = "driverIds", ignore = true)
    VehicleModel vehicleModelFromDTO(VehicleViewDTO vehicleViewDTO);

}
