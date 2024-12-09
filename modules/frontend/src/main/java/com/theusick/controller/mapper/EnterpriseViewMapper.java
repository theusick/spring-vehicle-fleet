package com.theusick.controller.mapper;

import com.theusick.controller.dto.EnterpriseViewDTO;
import com.theusick.service.model.EnterpriseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnterpriseViewMapper {

    EnterpriseViewDTO enterpriseViewDTOFromModel(EnterpriseModel enterpriseModel);

}
