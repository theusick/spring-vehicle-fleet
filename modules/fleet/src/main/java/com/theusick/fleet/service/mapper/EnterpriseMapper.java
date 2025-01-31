package com.theusick.fleet.service.mapper;

import com.theusick.fleet.controller.dto.enterprise.EnterpriseBaseDTO;
import com.theusick.fleet.controller.dto.enterprise.EnterpriseInfoDTO;
import com.theusick.fleet.repository.entity.DriverEntity;
import com.theusick.fleet.repository.entity.EnterpriseEntity;
import com.theusick.fleet.repository.entity.VehicleEntity;
import com.theusick.fleet.service.model.EnterpriseModel;
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
        VehicleMapper.class, DriverMapper.class
    }
)
public interface EnterpriseMapper {

    EnterpriseBaseDTO enterpriseBaseDTOFromModel(EnterpriseModel enterpriseModel);

    @Mapping(target = "vehicleIds", source = "vehicles", qualifiedByName = "mapVehicleIdsToList")
    @Mapping(target = "driverIds", source = "drivers", qualifiedByName = "mapDriverIdsToList")
    EnterpriseModel enterpriseModelFromEntity(EnterpriseEntity enterpriseEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vehicleIds", ignore = true)
    @Mapping(target = "driverIds", ignore = true)
    EnterpriseModel enterpriseModelFromInfoDTO(EnterpriseInfoDTO enterpriseDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vehicles", ignore = true)
    @Mapping(target = "drivers", ignore = true)
    @Mapping(target = "managers", ignore = true)
    void updateEnterpriseEntityFromModel(@MappingTarget EnterpriseEntity enterpriseEntity,
                                         EnterpriseModel enterpriseModel);

    @Named("mapVehicleIdsToList")
    default List<Long> mapVehicleIdsToList(List<VehicleEntity> vehicles) {
        if (vehicles == null) {
            return Collections.emptyList();
        }
        return vehicles.stream()
            .map(VehicleEntity::getId)
            .collect(Collectors.toList());
    }

    @Named("mapDriverIdsToList")
    default List<Long> mapDriverIdsToList(List<DriverEntity> drivers) {
        if (drivers == null) {
            return Collections.emptyList();
        }
        return drivers.stream()
            .map(DriverEntity::getId)
            .collect(Collectors.toList());
    }

}
