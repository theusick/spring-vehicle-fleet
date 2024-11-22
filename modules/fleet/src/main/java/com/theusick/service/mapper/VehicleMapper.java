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

    VehicleModel vehicleModelFromEntity(VehicleEntity vehicleEntity);

    @Mapping(target = "id", ignore = true)
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

}
