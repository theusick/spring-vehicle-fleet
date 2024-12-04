package com.theusick.service.mapper;

import com.theusick.repository.entity.EnterpriseEntity;
import com.theusick.service.model.EnterpriseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EnterpriseMapper {

    EnterpriseModel enterpriseModelFromEntity(EnterpriseEntity enterpriseEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vehicles", ignore = true)
    @Mapping(target = "drivers", ignore = true)
    void updateEnterpriseEntityFromModel(@MappingTarget EnterpriseEntity enterpriseEntity,
                                         EnterpriseModel enterpriseModel);

}
