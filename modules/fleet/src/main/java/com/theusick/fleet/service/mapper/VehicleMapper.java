package com.theusick.fleet.service.mapper;

import com.theusick.fleet.controller.dto.vehicle.VehicleBaseDTO;
import com.theusick.fleet.controller.dto.vehicle.VehicleInfoDTO;
import com.theusick.fleet.repository.entity.VehicleDriverEntity;
import com.theusick.fleet.repository.entity.VehicleEntity;
import com.theusick.fleet.service.mapper.util.MappingTimeUtil;
import com.theusick.fleet.service.model.VehicleModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
    componentModel = "spring",
    uses = {
        VehicleBrandMapper.class,
        VehicleDriverMapper.class
    },
    imports = {
        MappingTimeUtil.class
    }
)
public interface VehicleMapper {

    @Mapping(target = "brand", source = "brandId")
    VehicleBaseDTO vehicleBaseDTOFromModel(VehicleModel vehicleModel);

    default Page<VehicleBaseDTO> vehicleDTOPageFromModels(Page<VehicleModel> models) {
        return models.map(this::vehicleBaseDTOFromModel);
    }

    @Mapping(target = "enterpriseId", source = "enterprise.id")
    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(
        target = "driverIds",
        source = "vehicleDrivers",
        qualifiedByName = "mapDriverIdsFromVehicleDrivers"
    )
    @Mapping(
        target = "purchaseDate",
        expression = "java(MappingTimeUtil.convertInstantToTimezone(vehicleEntity.getPurchaseDate(), " +
            "vehicleEntity.getEnterprise().getTimezone()))"
    )
    VehicleModel vehicleModelFromEntity(VehicleEntity vehicleEntity);

    @Named("mapDriverIdsFromVehicleDrivers")
    default Set<Long> mapDriverIdsFromVehicleDrivers(Set<VehicleDriverEntity> vehicleDrivers) {
        if (vehicleDrivers == null) {
            return Collections.emptySet();
        }
        return vehicleDrivers.stream()
            .map(vehicleDriver -> vehicleDriver.getPrimaryKey().getDriver().getId())
            .collect(Collectors.toSet());
    }

    default Page<VehicleModel> vehicleModelsPageFromEntities(Page<VehicleEntity> entities) {
        return entities.map(this::vehicleModelFromEntity);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "driverIds", ignore = true)
    @Mapping(target = "enterpriseId", ignore = true)
    @Mapping(target = "brandId", source = "brand.id")
    VehicleModel vehicleModelFromInfoDTO(VehicleInfoDTO vehicleDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enterprise", ignore = true)
    @Mapping(target = "vehicleDrivers", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(
        target = "purchaseDate",
        expression = "java(vehicleModel.getPurchaseDate().toInstant())"
    )
    void updateVehicleEntityFromModel(@MappingTarget VehicleEntity vehicleEntity,
                                      VehicleModel vehicleModel);

}
