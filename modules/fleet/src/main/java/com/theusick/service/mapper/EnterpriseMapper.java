package com.theusick.service.mapper;

import com.theusick.controller.dto.enterprise.EnterpriseBaseDTO;
import com.theusick.repository.entity.EnterpriseEntity;
import com.theusick.service.model.DriverModel;
import com.theusick.service.model.EnterpriseModel;
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
        VehicleMapper.class, DriverMapper.class
    }
)
public interface EnterpriseMapper {

    @Mapping(target = "vehicles", source = "vehicles", qualifiedByName = "mapVehicleIdsToList")
    @Mapping(target = "drivers", source = "drivers", qualifiedByName = "mapDriverIdsToList")
    EnterpriseBaseDTO enterpriseBaseDTOFromModel(EnterpriseModel enterpriseModel);

    EnterpriseModel enterpriseModelFromEntity(EnterpriseEntity enterpriseEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vehicles", ignore = true)
    @Mapping(target = "drivers", ignore = true)
    void updateEnterpriseEntityFromModel(@MappingTarget EnterpriseEntity enterpriseEntity,
                                         EnterpriseModel enterpriseModel);

    @Named("mapVehicleIdsToList")
    default List<Long> mapVehicleIdsToList(List<VehicleModel> vehicles) {
        if (vehicles == null) {
            return Collections.emptyList();
        }
        return vehicles.stream()
            .map(VehicleModel::getId)
            .collect(Collectors.toList());
    }

    @Named("mapDriverIdsToList")
    default List<Long> mapDriverIdsToList(List<DriverModel> drivers) {
        if (drivers == null) {
            return Collections.emptyList();
        }
        return drivers.stream()
            .map(DriverModel::getId)
            .collect(Collectors.toList());
    }

}
