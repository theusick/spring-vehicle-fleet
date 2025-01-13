package com.theusick.service.mapper;

import com.theusick.controller.dto.brand.VehicleBrandIdDTO;
import com.theusick.controller.dto.vehicle.VehicleBaseDTO;
import com.theusick.controller.dto.vehicle.VehicleInfoDTO;
import com.theusick.repository.entity.VehicleBrandEntity;
import com.theusick.repository.entity.VehicleEntity;
import com.theusick.service.model.VehicleBrandModel;
import com.theusick.service.model.VehicleDriverModel;
import com.theusick.service.model.VehicleModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(
    componentModel = "spring",
    uses = {
        VehicleBrandMapper.class, VehicleDriverMapper.class
    }
)
public interface VehicleMapper {

    @Mapping(target = "drivers", source = "vehicleDrivers", qualifiedByName = "mapVehicleDriverIdsToList")
    @Mapping(target = "brand", source = "brand.id")
    VehicleBaseDTO vehicleBaseDTOFromModel(VehicleModel vehicleModel);

    @Mapping(target = "enterpriseId", source = "enterprise.id")
    VehicleModel vehicleModelFromEntity(VehicleEntity vehicleEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vehicleDrivers", ignore = true)
    @Mapping(target = "enterpriseId", ignore = true)
    @Mapping(target = "brand", source = "brand", qualifiedByName = "mapBrandIdToModel")
    VehicleModel vehicleModelFromInfoDTO(VehicleInfoDTO vehicleDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enterprise", ignore = true)
    @Mapping(target = "vehicleDrivers", ignore = true)
    @Mapping(target = "currentDriver", ignore = true)
    @Mapping(target = "brand", qualifiedByName = "updateBrandId")
    void updateVehicleEntityFromModel(@MappingTarget VehicleEntity vehicleEntity,
                                      VehicleModel vehicleModel);

    @Named("updateBrandId")
    default void updateBrandId(@MappingTarget VehicleBrandEntity brandEntity,
                               VehicleBrandModel brandModel) {
        if (brandEntity == null) {
            brandEntity = new VehicleBrandEntity();
        }
        brandEntity.setId(brandModel.getId());
    }

    @Named("mapVehicleDriverIdsToList")
    default List<Long> mapVehicleDriverIdsToList(List<VehicleDriverModel> vehicleDrivers) {
        if (vehicleDrivers == null) {
            return Collections.emptyList();
        }
        return vehicleDrivers.stream()
            .map(VehicleDriverModel::getDriverId)
            .collect(Collectors.toList());
    }

    @Named("mapBrandIdToModel")
    default VehicleBrandModel mapBrandIdToModel(VehicleBrandIdDTO brandIdDTO) {
        if (brandIdDTO == null) {
            return null;
        }
        
        return VehicleBrandModel.builder()
            .id(brandIdDTO.getId())
            .build();
    }

}
