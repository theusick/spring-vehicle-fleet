package com.theusick.fleet.service.mapper;

import com.theusick.fleet.controller.dto.driver.ActiveDriverDTO;
import com.theusick.fleet.controller.dto.driver.DriverBaseDTO;
import com.theusick.fleet.repository.entity.DriverEntity;
import com.theusick.fleet.repository.entity.VehicleDriverEntity;
import com.theusick.fleet.repository.entity.VehicleEntity;
import com.theusick.fleet.service.model.DriverModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
    componentModel = "spring",
    uses = {
        VehicleMapper.class, VehicleDriverMapper.class
    }
)
public interface DriverMapper {

    DriverBaseDTO driverBaseDTOFromModel(DriverModel driverModel);

    default Page<DriverBaseDTO> driverDTOPageFromModels(Page<DriverModel> models) {
        return models.map(this::driverBaseDTOFromModel);
    }

    ActiveDriverDTO activeDriverDTOFromModel(DriverModel driverModel);

    default Page<ActiveDriverDTO> activeDiverDTOPageFromModels(Page<DriverModel> models) {
        return models.map(this::activeDriverDTOFromModel);
    }

    @Mapping(target = "activeVehicleId", source = "activeVehicle.id")
    @Mapping(
        target = "vehicleIds",
        source = "vehicleDrivers",
        qualifiedByName = "mapVehicleIdsFromVehicleDrivers"
    )
    DriverModel driverModelFromEntity(DriverEntity driverEntity);

    @Named("mapVehicleIdsFromVehicleDrivers")
    default Set<Long> mapVehicleIdsFromVehicleDrivers(Set<VehicleDriverEntity> vehicleDrivers) {
        if (vehicleDrivers == null) {
            return Collections.emptySet();
        }
        return vehicleDrivers.stream()
            .map(vehicleDriver -> vehicleDriver.getPrimaryKey().getVehicle().getId())
            .collect(Collectors.toSet());
    }

    default Page<DriverModel> driverModelsPageFromEntities(Page<DriverEntity> entities) {
        return entities.map(this::driverModelFromEntity);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enterprise", ignore = true)
    @Mapping(
        target = "vehicleDrivers",
        expression = "java(appendVehicleDriversFromVehicleIds(driverModel.getVehicleIds(), driverEntity))"
    )
    void updateDriverEntityFromModel(@MappingTarget DriverEntity driverEntity,
                                     DriverModel driverModel);

    default Set<VehicleDriverEntity> appendVehicleDriversFromVehicleIds(Set<Long> vehicleIds,
                                                                        DriverEntity driverEntity) {
        if ((vehicleIds == null) || (driverEntity == null) || vehicleIds.isEmpty()) {
            return Collections.emptySet();
        }

        Set<VehicleDriverEntity> existedVehicleDrivers = driverEntity.getVehicleDrivers();
        if (existedVehicleDrivers == null) {
            existedVehicleDrivers = new HashSet<>();
        }

        existedVehicleDrivers.addAll(vehicleIds.stream()
            .map(vehicleId -> {
                VehicleEntity vehicleProxy = VehicleEntity.builder().id(vehicleId).build();

                VehicleDriverEntity.VehicleDriverId pk =
                    new VehicleDriverEntity.VehicleDriverId(vehicleProxy, driverEntity);

                return VehicleDriverEntity.builder()
                    .primaryKey(pk)
                    .build();
            })
            .collect(Collectors.toSet()));

        return driverEntity.getVehicleDrivers();
    }

}
