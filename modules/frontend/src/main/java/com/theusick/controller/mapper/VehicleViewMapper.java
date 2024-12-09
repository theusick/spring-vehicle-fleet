package com.theusick.controller.mapper;

import com.theusick.controller.dto.VehicleViewDTO;
import com.theusick.service.model.VehicleModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleViewMapper {

    VehicleViewDTO vehicleViewDTOFromModel(VehicleModel vehicleModel);

    VehicleModel vehicleModelFromDTO(VehicleViewDTO vehicleViewDTO);

}
