package com.theusick.service.mapper;

import com.theusick.controller.dto.enterprise.EnterpriseBaseDTO;
import com.theusick.repository.entity.EnterpriseEntity;
import com.theusick.service.model.EnterpriseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {VehicleMapper.class, DriverMapper.class})
public interface EnterpriseMapper {

    EnterpriseBaseDTO enterpriseBaseDTOFromModel(EnterpriseModel enterpriseModel);

    EnterpriseModel enterpriseModelFromEntity(EnterpriseEntity enterpriseEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vehicles", ignore = true)
    @Mapping(target = "drivers", ignore = true)
    void updateEnterpriseEntityFromModel(@MappingTarget EnterpriseEntity enterpriseEntity,
                                         EnterpriseModel enterpriseModel);

}
