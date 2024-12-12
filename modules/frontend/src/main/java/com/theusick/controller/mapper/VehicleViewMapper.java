package com.theusick.controller.mapper;

import com.theusick.controller.dto.VehicleViewDTO;
import com.theusick.service.model.VehicleModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleViewMapper {

    @Mapping(target = "brandId", source = "brand.id")
    VehicleViewDTO vehicleViewDTOFromModel(VehicleModel vehicleModel);

    @Mapping(target = "brand.id", source = "brandId")
    @Mapping(target = "vehicleDrivers", ignore = true)
    VehicleModel vehicleModelFromDTO(VehicleViewDTO vehicleViewDTO);

}
